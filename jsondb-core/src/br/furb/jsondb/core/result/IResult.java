package br.furb.jsondb.core.result;

import java.util.List;

public interface IResult {

	List<String> getMessages();

	boolean hasError();
}
