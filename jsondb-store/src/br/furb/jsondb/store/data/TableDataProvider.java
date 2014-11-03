package br.furb.jsondb.store.data;

import java.util.HashMap;
import java.util.Map;

import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.TableMetadata;

public class TableDataProvider {

	private static final TableDataProvider INSTANCE = new TableDataProvider();

	private Map<String, Map<String, TableData>> tablesData = new HashMap<String, Map<String, TableData>>();

	public static TableDataProvider getInstance() {
		return INSTANCE;
	}

	public TableData getTableData(String database, String table) throws StoreException {

		if (!tablesData.containsKey(database)) {
			tablesData.put(database, new HashMap<String, TableData>());
		}

		Map<String, TableData> map = tablesData.get(database);

		if (!map.containsKey(table)) {

			TableMetadata tableMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database).getTable(table);
			TableData tableData = new TableData(tableMetadata, database);

			map.put(table, tableData);
		}

		return map.get(table);
	}

}
