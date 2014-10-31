package br.furb.jsondb.store;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.ConstraintDefinition;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.KeyDefinition;
import br.furb.jsondb.parser.TableDefinition;
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

	public void insertData(/* TODO receber InsertStatement */) {

	}
}
