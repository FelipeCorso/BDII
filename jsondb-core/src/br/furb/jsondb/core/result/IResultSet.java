package br.furb.jsondb.core.result;

import java.util.List;

public interface IResultSet extends IResult {

	List<ResultRow> getRows();

}