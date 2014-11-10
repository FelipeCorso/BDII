package br.furb.jsondb.core.util;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.SQLException;

public class JsonDBUtils {

	public static void validateHasCurrentDatabase() throws SQLException {
		if (JsonDB.getInstance().getCurrentDatabase() == null) {
			throw new SQLException("There is no current database");
		}
	}
}
