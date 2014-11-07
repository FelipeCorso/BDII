package br.furb.jsondb.store.utils;

import java.io.File;
import java.io.IOException;

import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.data.LastRowId;

public class LastRowIdUtils {

	public static final String LAST_ROW_ID_FILE_NAME = "lastRowId";

	public static LastRowId getLastRowId(File tableDir) throws StoreException {
		File rowIdFile = new File(tableDir, LAST_ROW_ID_FILE_NAME);

		LastRowId lastRowId;
		try {
			lastRowId = JsonUtils.parseJsonToObject(rowIdFile, LastRowId.class);
		} catch (IOException e) {
			throw new StoreException(e);
		}

		return lastRowId;

	}

	public static void saveLastRowId(File tableDir, LastRowId lastRowId)
			throws StoreException {
		try {
			JsonUtils.write(lastRowId, LastRowId.class, new File(tableDir,
					LAST_ROW_ID_FILE_NAME));
		} catch (IOException e) {
			throw new StoreException(e);
		}
	}

	public static void createLastRowId(File tableDir, LastRowId lastRowId)
			throws StoreException {
		try {
			new File(tableDir, LAST_ROW_ID_FILE_NAME).createNewFile();
		} catch (IOException e) {
			throw new StoreException(e);
		}

		saveLastRowId(tableDir, lastRowId);
	}

}
