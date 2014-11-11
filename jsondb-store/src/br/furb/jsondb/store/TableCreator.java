package br.furb.jsondb.store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.ColumnType;
import br.furb.jsondb.parser.ConstraintDefinition;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.KeyDefinition;
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

		//  cria o metadados da tabela

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
			ColumnMetadata fieldMetadata = new ColumnMetadata(column.getName(), columnType.getDataType(), size, precision);

			Optional<ConstraintDefinition> constraint = column.getConstraint();
			if (constraint.isPresent()) {
				addConstraint(tableDefinition, database, column, fieldMetadata, constraint);
			}

			tableMetadata.addColumn(fieldMetadata);
		}

		tableMetadata.addIndexMetadata(indexMetadata);
		return tableMetadata;
	}

	private static void addConstraint(TableDefinition tableDefinition, String database, ColumnDefinition column, ColumnMetadata fieldMetadata,
			Optional<ConstraintDefinition> constraint) throws SQLException {
		Optional<String> name = constraint.get().getName();
		String cName = null;
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		if (!name.isPresent()) {
			cName = ConstraintNameGenerator.generateConstraintName(
			/**/databaseMetadata,
			/**/tableDefinition.getIdentifier(),
			/**/constraint.get(), column.getName());
		} else {
			cName = name.get();
		}

		if (databaseMetadata.getConstraint(cName) != null) {
			throw new SQLException(String.format("Duplicate constraint '%s'", cName));
		}

		databaseMetadata.addConstraint(new ConstraintMetadata(cName, tableDefinition.getIdentifier(), column.getName(), constraint.get().getKind()));

		fieldMetadata.setConstraint(cName);
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
