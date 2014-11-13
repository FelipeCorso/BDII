package br.furb.jsondb.store.metadata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.utils.JsonUtils;

public class DatabaseMetadataProvider {

	private static DatabaseMetadataProvider INSTANCE;

	private Map<String, DatabaseMetadata> databaseMetadatas = new HashMap<String, DatabaseMetadata>();

	public static DatabaseMetadataProvider getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new DatabaseMetadataProvider();
		}

		return INSTANCE;
	}

	public DatabaseMetadata getDatabaseMetadata(String database) {

		if (!databaseMetadatas.containsKey(database)) {

			synchronized (this) {
				if (!databaseMetadatas.containsKey(database)) {

					File metadataFile = getMetadataFile(database);

					try {
						databaseMetadatas.put(database, JsonUtils.parseJsonToObject(metadataFile, DatabaseMetadata.class));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}

				}
			}

		}

		return databaseMetadatas.get(database);
	}

	public static File getMetadataFile(String database) {
		File jsonDbDir = JsonDBStore.getInstance().getJsonDBDir();

		File databaseDir = new File(jsonDbDir, database);

		if (!databaseDir.exists()) {
			throw new IllegalStateException("Database not found: " + database);
		}

		File metadataFile = new File(databaseDir, "database.metadata");
		return metadataFile;
	}

	public boolean containsDatabase(String database) {
		if (databaseMetadatas.containsKey(database)) {
			return true;
		}

		File jsonDbDir = JsonDBStore.getInstance().getJsonDBDir();

		File databaseDir = new File(jsonDbDir, database);

		return databaseDir.exists();
	}

	public static void reset() {
		INSTANCE = null;
	}
}
