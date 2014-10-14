package br.furb.jsondb.store.metadata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.furb.jsondb.store.JsonDBStore;

public class DatabaseMetadataProvider {

	private static final DatabaseMetadataProvider INSTANCE = new DatabaseMetadataProvider();

	private Map<String, DatabaseMetadata> databaseMetadatas = new HashMap<String, DatabaseMetadata>();

	public static DatabaseMetadataProvider getInstance() {
		return INSTANCE;
	}

	public DatabaseMetadata getDatabaseMetadata(String database) {

		if (!databaseMetadatas.containsKey(database)) {

			synchronized (this) {
				if (!databaseMetadatas.containsKey(database)) {

					File metadataFile = getMetadataFile(database);

					try {
						databaseMetadatas.put(database, DatabaseMetadataIO
								.read(metadataFile.getAbsolutePath()));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}

				}
			}

		}

		return databaseMetadatas.get(database);
	}

	public File getMetadataFile(String database) {
		File jsonDbDir = JsonDBStore.getInstance().getJsonDBDir();
		
		File databaseDir = new File(jsonDbDir, database);

		if (databaseDir.exists()) {
			throw new IllegalStateException("Database not found: "
					+ database);
		}
		
		File metadataFile = new File(databaseDir, "database.metadata");
		return metadataFile;
	}
	
	public boolean containsDatabase(String database){
		if (databaseMetadatas.containsKey(database)) {
			return true;
		}
		
		File jsonDbDir = JsonDBStore.getInstance().getJsonDBDir();
		
		File databaseDir = new File(jsonDbDir, database);
		
		return databaseDir.exists();
	}
}
