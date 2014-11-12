package br.furb.jsondb.store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import br.furb.jsondb.parser.Index;
import br.furb.jsondb.parser.Value;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.data.ColumnData;
import br.furb.jsondb.store.data.IndexData;
import br.furb.jsondb.store.data.IndexDataProvider;
import br.furb.jsondb.store.data.LastRowId;
import br.furb.jsondb.store.data.RowData;
import br.furb.jsondb.store.data.TableDataProvider;
import br.furb.jsondb.store.metadata.ConstraintMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.IndexMetadata;
import br.furb.jsondb.store.metadata.TableMetadata;
import br.furb.jsondb.store.utils.JsonUtils;
import br.furb.jsondb.store.utils.LastRowIdUtils;

public class JsonDBStore {

	private static JsonDBStore instance;

	private File jsonDBDir;

	/**
	 * Nome da pasta do JsonDB, que deve estar dentro da pasta de trabalho do
	 * banco.
	 * <p>
	 * Atualmente é {@code ".jsondb"}
	 * </p>
	 */
	public static final String JSONDB_FOLDER_NAME = ".jsondb";

	private JsonDBStore() {
		// esconde o construtor
	}

	public static JsonDBStore getInstance() {
		if (instance == null) {
			instance = new JsonDBStore();
		}
		return instance;
	}

	public File getJsonDBDir() {
		if (jsonDBDir == null) {
			jsonDBDir = new File(JsonDBProperty.JSON_DB_DIR.get(), JSONDB_FOLDER_NAME);
			if (!jsonDBDir.exists()) {
				boolean mkdir = jsonDBDir.mkdir();
				if (!mkdir) {
					throw new IllegalStateException("Was not possible to create jsondb directory.");
				}
			}
		}
		return jsonDBDir;
	}

	public boolean createDatabase(String database) throws StoreException {
		File databaseDir = new File(getJsonDBDir(), database);
		if (!databaseDir.mkdirs()) {
			return false;
		}

		File metadataFile = new File(databaseDir, "database.metadata");

		try {
			if (metadataFile.createNewFile()) {

				DatabaseMetadata metadata = new DatabaseMetadata();
				metadata.setName(database);
				JsonUtils.write(metadata, DatabaseMetadata.class, metadataFile);
				return true;

			}
		} catch (IOException e) {
			throw new StoreException(e);
		}

		return false;
	}

	public File getDatabaseDir(String database) {
		File databaseDir = new File(getJsonDBDir(), database);
		return databaseDir;
	}

	/**
	 * Cria uma nova tabela na base de dados. A criação da tabela envolve:
	 * 
	 * <ol>
	 * <li>criação do diretório da tabela
	 * <li>criação de um arquivo de índice para cada campo da chave da tabela
	 * <li>criação de um arquivo para os dados da tabela
	 * <li>criação do metadados da tabela e inserção do mesmo no metadados do
	 * banco.
	 * </ol>
	 * 
	 * @param statement
	 * 
	 * @throws StoreException
	 * @throws SQLException
	 */
	public void createTable(String database, CreateStatement statement, List<String> pk) throws StoreException, SQLException {

		TableCreator.createTable(database, statement, pk);
	}

	public void dropTable(String database, String table) throws StoreException {
		// remove a tabela do metadados
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);
		databaseMetadata.removeTable(table);

		Map<String, ConstraintMetadata> constraints = databaseMetadata.getConstraints();

		List<String> list = new ArrayList<String>(constraints.keySet());
		for (String constraint : list) {
			if (constraints.get(constraint).getTable().equals(table)) {
				constraints.remove(constraint);
			}

		}

		// remove o diretório da tabela e todos os seus arquivos;
		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		File tableDir = new File(databaseDir, table);

		try {
			FileUtils.deleteDirectory(tableDir);
		} catch (IOException e) {

			//tenta de novo 

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
			}

			try {
				FileUtils.deleteDirectory(tableDir);
			} catch (IOException e2) {
				e.printStackTrace();
				throw new StoreException(e.getMessage(), e);
			}

		}

		saveDatabaseMetadata(databaseMetadata);
	}

	public int insertData(String database, String table, Map<String, Value<?>> mapValues) throws StoreException {
		RowData rowData = new RowData();

		for (Entry<String, Value<?>> entry : mapValues.entrySet()) {
			ColumnData columnData = new ColumnData();
			columnData.setName(entry.getKey());
			columnData.setValue(entry.getValue().getBaseValue());
			rowData.addColumn(columnData);
		}

		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);
		File tableDir = new File(databaseDir, table);

		LastRowId lastRowId = LastRowIdUtils.getLastRowId(tableDir);

		int rowId = lastRowId.getLastRowId() + 1;
		lastRowId.setLastRowId(rowId);

		LastRowIdUtils.saveLastRowId(tableDir, lastRowId);

		rowData.setRowId(rowId);

		try {
			JsonUtils.write(rowData, RowData.class, new File(tableDir, rowId + ".dat"));
		} catch (IOException e) {
			throw new StoreException(e);
		}

		TableDataProvider.getInstance().getTableData(database, table).addRow(rowData);

		return rowId;
	}

	public void saveDatabaseMetadata(DatabaseMetadata databaseMetadata) throws StoreException {
		try {
			JsonUtils.write(databaseMetadata, DatabaseMetadata.class, new File(JsonDBStore.getInstance().getDatabaseDir(databaseMetadata.getName()), "database.metadata"));
		} catch (IOException e) {
			throw new StoreException("Was not possible to save database metadata.", e);
		}
	}

	public static void reset() {
		instance = null;
	}

	public void createIndex(String database, Index index) throws StoreException {

		IndexData indexData = new IndexData();
		indexData.setName(index.getIdentifier());
		indexData.addColumn(index.getTableColumn().getColumnName());

		IndexMetadata indexMetadata = new IndexMetadata();
		indexMetadata.addColumn(index.getTableColumn().getColumnName());
		indexMetadata.setName(index.getIdentifier());

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		TableMetadata table = databaseMetadata.getTable(index.getTableColumn().getTable().get().getIdentifier());

		table.addIndexMetadata(indexMetadata);

		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		String tableName = table.getName();

		File tableDir = new File(databaseDir, tableName);

		File indexFile = new File(tableDir, index.getIdentifier() + ".index");

		try {
			indexFile.createNewFile();
			JsonUtils.write(indexData, IndexData.class, indexFile);
		} catch (IOException e) {
			throw new StoreException("Was not possible to create index file", e);
		}

		saveDatabaseMetadata(databaseMetadata);
	}

	public void dropIndex(String database, Index index) throws StoreException {
		// remove do metadata
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		TableMetadata table = databaseMetadata.getTable(index.getTable().getIdentifier());
		table.removeIndex(index.getIdentifier());

		saveDatabaseMetadata(databaseMetadata);

		//remove da memória
		IndexDataProvider.getInstance(database).removeIndexData(table.getName(), index.getIdentifier());

		// remove o arquivo do índice
		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		String tableName = table.getName();

		File tableDir = new File(databaseDir, tableName);

		File indexFile = new File(tableDir, index.getIdentifier() + ".index");
		boolean delete = indexFile.delete();

		if (!delete) {
			throw new StoreException("Was not possible to delete index file");
		}
	}
}
