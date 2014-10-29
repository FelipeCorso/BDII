package br.furb.jsondb.core.util;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;

public class JsonDBUtils {

	public static IResult validateHasCurrentDatabase(){
		if(JsonDB.getInstance().getCurrentDatabase() == null){
			return new Result(true, "There is no current database");
		}
		return null;
	}
}
