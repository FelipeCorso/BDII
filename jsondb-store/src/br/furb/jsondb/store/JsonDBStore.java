package br.furb.jsondb.store;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.InsertStatement;
import br.furb.jsondb.store.data.ColumnData;
import br.furb.jsondb.store.data.LastRowId;
import br.furb.jsondb.store.data.RowData;
import br.furb.jsondb.store.data.TableDataProvider;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.utils.JsonUtils;
import br.furb.jsondb.store.utils.LastRowIdUtils;

public class JsonDBStore {

	private static JsonDBStore instance = new JsonDBStore();

	private File jsonDBDir;

	private JsonDBStore() {
		// esconde o construtor
	}

	public static JsonDBStore getInstance() {
		return instance;
	}

	public File getJsonDBDir() {
		if (jsonDBDir == null) {
			jsonDBDir = new File(JsonDBProperty.JSON_DB_DIR.get(), ".jsondb");
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
	 * Cria uma nova tabela na base de dados. A cria��o da tabela envolve:
	 * 
	 * <ol>
	 * <li>cria��o do diret�rio da tabela
	 * <li>cria��o de um arquivo de �ndice para cada campo da chave da tabela
	 * <li>cria��o de um arquivo para os dados da tabela
	 * <li>cria��o do metadados da tabela e inser��o do mesmo no metadados do
	 * banco.
	 * </ol>
	 * 
	 * @param statement
	 * 
	 * @throws StoreException
	 */
	public void createTable(String database, CreateStatement statement)
			throws StoreException {

		TableCreator.createTable(database, statement);
	}

	public void dropTable(String database, String table) throws StoreException {
		// remove a tabela do metadados
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider
				.getInstance().getDatabaseMetadata(database);
		databaseMetadata.removeTable(table);

		// remove o diret�rio da tabela e todos os seus arquivos;
		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		File tableDir = new File(databaseDir, table);

		try {
			FileUtils.deleteDirectory(tableDir);
		} catch (IOException e) {
			throw new StoreException(e.getMessage(), e);
		}

		// FIXME atualizar o metadados em disco neste momento???
	}

	public int insertData(String database, InsertStatement statement) throws StoreException {
		RowData rowData = new RowData();

		List<ColumnIdentifier> columns = statement.getColumns();

		for (int i = 0; i < columns.size(); i++) {
			ColumnData columnData = new ColumnData();
			columnData.setName(columns.get(i).getColumnName());
			columnData.setValue(statement.getValues().get(i).getBaseValue());
			rowData.addColumn(columnData);
		}

		String tableName = statement.getTable().getIdentifier();

		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);
		File tableDir = new File(databaseDir, tableName);
		
		
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

		TableDataProvider.getInstance().getTableData(database, tableName).addRow(rowData);
		
		return rowId;
	}
}
