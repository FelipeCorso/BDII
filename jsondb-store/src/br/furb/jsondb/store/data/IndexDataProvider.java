package br.furb.jsondb.store.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.TableMetadata;
import br.furb.jsondb.store.utils.JsonUtils;

public class IndexDataProvider {

	private static final Map<String, IndexDataProvider> INSTANCES = new HashMap<String, IndexDataProvider>();

	private Map<String, Map<String, IndexData>> indexData = new HashMap<String, Map<String, IndexData>>();

	private String database;

	private IndexDataProvider(String database) {
		this.database = database;
	}

	public static IndexDataProvider getInstance(String database) {

		if (!INSTANCES.containsKey(database)) {
			INSTANCES.put(database, new IndexDataProvider(database));
		}

		return INSTANCES.get(database);
	}

	public IndexData getIndexData(String table, String index)
			throws StoreException {

		if (!indexData.containsKey(table)) {
			indexData.put(table, new HashMap<String, IndexData>());
		}

		Map<String, IndexData> map = indexData.get(table);

		if (!map.containsKey(index)) {

			File databaseDir = JsonDBStore.getInstance().getDatabaseDir(
					database);
			File tableDir = new File(databaseDir, table);

			File indexDataFile = new File(tableDir, index + ".index");
			try {
				map.put(index, JsonUtils.parseJsonToObject(indexDataFile,
						IndexData.class));
			} catch (IOException e) {
				throw new StoreException(e);
			}
		}

		return map.get(index);
	}

	public static void reset() {
		INSTANCES.clear();
	}

}
