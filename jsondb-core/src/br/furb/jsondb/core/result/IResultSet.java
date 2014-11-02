package br.furb.jsondb.core.result;

import java.util.List;

import br.furb.jsondb.store.data.RowData;

public interface IResultSet extends IResult {

	List<RowData> getRows();

}
