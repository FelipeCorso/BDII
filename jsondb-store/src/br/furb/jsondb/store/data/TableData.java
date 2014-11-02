package br.furb.jsondb.store.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.metadata.TableMetadata;
import br.furb.jsondb.store.utils.JsonUtils;

public class TableData {

	private Map<Integer, RowData> rows = new HashMap<Integer, RowData>();
	private TableMetadata tableMetadata;
	private String database;

	public TableData(TableMetadata tableMetadata, String database) {
		this.tableMetadata = tableMetadata;
		this.database = database;
	}

	public RowData getRow(int id) throws StoreException {
		if (!rows.containsKey(id)) {
			// Lê o registro e joga para a memória.

			File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);
			File tableDir = new File(databaseDir, tableMetadata.getName());

			File rowDataFile = new File(tableDir, id + ".dat");
			RowData rowData;
			try {
				rowData = JsonUtils.parseJsonToObject(rowDataFile, RowData.class);
			} catch (IOException e) {
				throw new StoreException(e);
			}
			addRow(rowData);
		}

		return rows.get(id);
	}

	public void addRow(RowData row) {
		this.rows.put(row.getRowId(), row);
	}

	public Map<Integer, RowData> getRows() throws StoreException {
		//Lê cada registro que ainda não está na memória

		for (int i = 0; i < tableMetadata.getLastRowId(); i++) {

			if (!rows.containsKey(i)) {

				File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);
				File tableDir = new File(databaseDir, tableMetadata.getName());

				File rowDataFile = new File(tableDir, i + ".dat");
				RowData rowData;
				try {
					rowData = JsonUtils.parseJsonToObject(rowDataFile, RowData.class);
				} catch (IOException e) {
					throw new StoreException(e);
				}
				addRow(rowData);
			}
		}

		return rows;
	}

}
