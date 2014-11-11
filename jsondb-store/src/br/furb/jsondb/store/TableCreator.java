package br.furb.jsondb.store;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnType;
import br.furb.jsondb.parser.ConstraintDefinition;
import br.furb.jsondb.parser.TableDefinition;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.data.IndexData;
import br.furb.jsondb.store.data.LastRowId;
import br.furb.jsondb.store.metadata.ColumnMetadata;
import br.furb.jsondb.store.metadata.ConstraintMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.IndexMetadata;
import br.furb.jsondb.store.metadata.TableMetadata;
import br.furb.jsondb.store.utils.ConstraintNameGenerator;
import br.furb.jsondb.store.utils.JsonUtils;
import br.furb.jsondb.store.utils.LastRowIdUtils;

public class TableCreator {

	public static void createTable(String database, CreateStatement statement, List<String> pk) throws StoreException, SQLException {
		// cria uma pasta para a tabela
		File tableDir = createDiretory(database, statement);

		TableDefinition tableDefinition = (TableDefinition) statement.getStructure();

		// cria o arquivo de índice da pk da tabela
		IndexMetadata indexMetadata = createPrimaryKeyIndex(tableDir, pk);

		// cria o metadados da tabela

		TableMetadata tableMetadata = createTableMetadata(tableDefinition, pk, indexMetadata, database);

		LastRowIdUtils.createLastRowId(tableDir, new LastRowId());

		// adiciona o metadatos da tabela ao metadados do banco em mem�ria.
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);
		databaseMetadata.addTable(tableMetadata);

		// grava o metadados da nova tabela no metadados do banco em disco
		File metadataFile = DatabaseMetadataProvider.getInstance().getMetadataFile(database);
		try {
			JsonUtils.write(databaseMetadata, DatabaseMetadata.class, metadataFile);
		} catch (IOException e) {
			throw new StoreException(e.getMessage());
		}
	}

	private static TableMetadata createTableMetadata(TableDefinition tableDefinition, List<String> pk, IndexMetadata indexMetadata, String database) throws SQLException {
		TableMetadata tableMetadata = new TableMetadata();
		tableMetadata.setPrimaryKey(pk);
		tableMetadata.setName(tableDefinition.getIdentifier());

		for (ColumnDefinition column : tableDefinition.getColumns()) {
			ColumnType columnType = column.getColumnType();
			int precision = columnType.getPrecision().isPresent() ? columnType.getPrecision().get() : 0;
			int size = columnType.getSize().isPresent() ? columnType.getSize().get() : 0;
			ColumnMetadata columnMetadata = new ColumnMetadata(column.getName(), columnType.getDataType(), size, precision);

			Optional<ConstraintDefinition> constraint = column.getConstraint();
			if (constraint.isPresent()) {
				addColumnConstraint(tableDefinition, database, columnMetadata, constraint.get());
			}

			tableMetadata.addColumn(columnMetadata);
		}

		for (ConstraintDefinition constraintDefinition : tableDefinition.getFinalConstraints()) {
			addFinalConstraint(database, tableDefinition, constraintDefinition);
		}

		tableMetadata.addIndexMetadata(indexMetadata);
		return tableMetadata;
	}

	private static void addFinalConstraint(String database, TableDefinition tableDefinition, ConstraintDefinition constraint) throws SQLException {

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		String constraintName = constraint.getName().orElse(null);

		if (constraintName == null) {
			constraintName = ConstraintNameGenerator.generateConstraintName(
			/**/databaseMetadata,
			/**/tableDefinition.getIdentifier(),
			/**/constraint, null);
		}

		if (databaseMetadata.getConstraint(constraintName) != null) {
			throw new SQLException(String.format("Duplicated constraint '%s'", constraintName));
		}

		databaseMetadata.addConstraint(new ConstraintMetadata(constraintName, tableDefinition.getIdentifier(), constraint.getKind()));
	}

	private static void addColumnConstraint(TableDefinition tableDefinition, String database, ColumnMetadata columnMetadata, ConstraintDefinition constraint) throws SQLException {

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		String constraintName = constraint.getName().orElse(null);

		if (constraintName == null) {
			constraintName = ConstraintNameGenerator.generateConstraintName(
			/**/databaseMetadata,
			/**/tableDefinition.getIdentifier(),
			/**/constraint, columnMetadata.getName());
		}

		if (databaseMetadata.getConstraint(constraintName) != null) {
			throw new SQLException(String.format("Duplicated constraint '%s'", constraintName));
		}

		databaseMetadata.addConstraint(new ConstraintMetadata(constraintName, tableDefinition.getIdentifier(), constraint.getKind(), columnMetadata.getName()));

		columnMetadata.setConstraint(constraintName);
	}

	private static IndexMetadata createPrimaryKeyIndex(File tableDir, List<String> pk) throws StoreException {

		IndexData index = new IndexData();
		IndexMetadata metadata = new IndexMetadata();

		metadata.setName("PRIMARY");

		for (String column : pk) {
			index.addColumn(column);
			metadata.addColumn(column);
		}

		try {
			File indexFile = new File(tableDir, "PRIMARY.index");
			JsonUtils.write(metadata, IndexMetadata.class, indexFile);
		} catch (IOException e1) {
			throw new StoreException("Was not possible to create the primary key index file", e1);
		}

		return metadata;
	}

	private static File createDiretory(String database, CreateStatement statement) {
		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		String tableName = statement.getStructure().getIdentifier();

		File tableDir = new File(databaseDir, tableName);

		tableDir.mkdir();
		return tableDir;
	}

}
