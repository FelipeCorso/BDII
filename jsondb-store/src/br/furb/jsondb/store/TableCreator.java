package br.furb.jsondb.store;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.ColumnType;
import br.furb.jsondb.parser.ConstraintDefinition;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.KeyDefinition;
import br.furb.jsondb.parser.TableDefinition;
import br.furb.jsondb.store.data.Index;
import br.furb.jsondb.store.data.TableData;
import br.furb.jsondb.store.metadata.DatabaseMetadataIO;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.FieldMetadata;
import br.furb.jsondb.store.metadata.IndexMetadata;
import br.furb.jsondb.store.metadata.TableMetadata;
import br.furb.jsondb.store.utils.JsonUtils;

public class TableCreator {

	public static void createTable(String database, CreateStatement statement)
			throws StoreException {
		// 1º cria uma pasta para a tabela
		File tableDir = createDiretory(database, statement);

		TableDefinition tableDefinition = (TableDefinition) statement
				.getStructure();

		Set<String> pk = getPrimaryKeyFields(tableDefinition);

		// 2º cria o arquivo de índice da pk da tabela
		IndexMetadata indexMetadata = createPrimaryKeyIndex(tableDir, pk);

		// 3º cria o arquivo de dados da tabela

		createDataFile(tableDir);

		// 4º cria o metadados da tabela

		TableMetadata tableMetadata = createTableMetadata(tableDefinition, pk,
				indexMetadata);
		
		
		// adiciona o metadatos da tabela ao metadados do banco em memória.
		DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database)
				.addTable(tableMetadata);

		// grava o metadados da nova tabela no metadados do banco em disco
		File metadataFile = DatabaseMetadataProvider.getInstance()
				.getMetadataFile(database);
		try {
			DatabaseMetadataIO.appendTableMetadata(tableMetadata, metadataFile);
		} catch (IOException e) {
			throw new StoreException(e.getMessage());
		}
	}

	private static TableMetadata createTableMetadata(TableDefinition tableDefinition,
			Set<String> pk, IndexMetadata indexMetadata) {
		TableMetadata tableMetadata = new TableMetadata();
		tableMetadata.setPrimaryKey(pk);
		tableMetadata.setName(tableDefinition.getIdentifier());
		
		for (ColumnDefinition column : tableDefinition.getColumns()) {
			ColumnType columnType = column.getColumnType();
			int precision = columnType.getPrecision().isPresent() ? columnType.getPrecision().get() : 0;
			FieldMetadata fieldMetadata = new FieldMetadata(column.getName(), columnType.getDataType(), columnType.getSize().orElse(0), precision);
			
			if(column.getConstraint()!= null){
				ConstraintKind constraintKind = column.getConstraint().get().getKind();
				fieldMetadata.setConstraint(constraintKind);
			}
			
			tableMetadata.addField(fieldMetadata);
		}
		
		tableMetadata.setIndexMetadata(indexMetadata);
		return tableMetadata;
	}

	private static Set<String> getPrimaryKeyFields(
			TableDefinition tableDefinition) {
		Set<String> pk = new LinkedHashSet<String>();

		for (ConstraintDefinition constraintDefinition : tableDefinition
				.getFinalConstraints()) {
			if (constraintDefinition.getKind() == ConstraintKind.PRIMARY_KEY) {

				KeyDefinition keyDefinition = (KeyDefinition) constraintDefinition;

				for (ColumnIdentifier column : keyDefinition.getColumns()) {
					pk.add(column.getColumnName());
				}
			}
		}
		return pk;
	}

	private static void createDataFile(File tableDir) throws StoreException {
		File tableDataFile = new File(tableDir, "table.data");
		try {
			tableDataFile.createNewFile();

			TableData tableData = new TableData();

			JsonUtils.parseObjectToJson(new FileWriter(tableDataFile),
					tableData, TableData.class);

		} catch (IOException e1) {
			throw new StoreException(
					"Was not possible to create table data file", e1);
		}
	}

	private static IndexMetadata createPrimaryKeyIndex(File tableDir, Set<String> pk)
			throws StoreException {

		Index index = new Index();
		IndexMetadata metadata = new IndexMetadata();
		
		for (String column : pk) {
			index.addColumn(column);
			metadata.addColumn(column);
		}

		try {

			File indexFile = new File(tableDir, "PRIMARY.index");
			indexFile.createNewFile();
			JsonUtils.parseObjectToJson(new FileWriter(indexFile), index,
					Index.class);
		} catch (IOException e1) {
			throw new StoreException(
					"Was not possible to create table index file", e1);
		}
		
		return metadata;
	}

	private static File createDiretory(String database,
			CreateStatement statement) {
		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		String tableName = statement.getStructure().getIdentifier();

		File tableDir = new File(databaseDir, tableName);

		tableDir.mkdir();
		return tableDir;
	}

}
