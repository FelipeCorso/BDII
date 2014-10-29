package br.furb.jsondb.store;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import br.furb.jsondb.store.data.Index;
import br.furb.jsondb.store.data.TableData;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataIO;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.TableMetadata;
import br.furb.jsondb.store.utils.JsonUtils;

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
					throw new IllegalStateException(
							"Was not possible to create jsondb directory.");
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
			return metadataFile.createNewFile();
		} catch (IOException e) {
			throw new StoreException(e);
		}
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
	 * @throws StoreException
	 */
	public void createTable(/* TODO receber IStatement */)
			throws StoreException {
		// 1º cria uma pasta para a tabela
		String database = "";
		File databaseDir = getDatabaseDir(database);

		String tableName = "";

		File tableDir = new File(databaseDir, tableName);

		tableDir.mkdir();

		// 2º cria o arquivos de indices da tabela, um para cada campo da pk
		// TODO iterar pelos campos da pk
		String field = "";
		File indexFile = new File(tableDir, field + ".index");
		try {

			indexFile.createNewFile();
			Index index = new Index();
			index.setField(field);

			JsonUtils.parseObjectToJson(new FileWriter(indexFile), index,
					Index.class);

		} catch (IOException e1) {
			throw new StoreException(
					"Was not possible to create table index file", e1);
		}

		// 3º cria o arquivo de dados da tabela

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

		// 4º cria o metadados da tabela e adiciona ao metadados do banco

		TableMetadata tableMetadata = new TableMetadata();
		// TODO popular o metadados

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

	public void dropTable(String database, String table) throws StoreException {
		// remove a tabela do metadados
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider
				.getInstance().getDatabaseMetadata(database);
		databaseMetadata.removeTable(table);

		// remove o diretório da tabela e todos os seus arquivos;
		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		File tableDir = new File(databaseDir, table);

		try {
			FileUtils.deleteDirectory(tableDir);
		} catch (IOException e) {
			throw new StoreException(e.getMessage(), e);
		}

		// FIXME atualizar o metadados em disco neste momento???
	}
	
	public void insertData(/* TODO receber InsertStatement */){
		
	}
}
