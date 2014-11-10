package br.furb.jsondb.core.result;

import java.util.Arrays;
import java.util.List;

public class Result implements IResult {

	private List<String> messages;

	public Result( String... messages) {
		this.messages = Arrays.asList(messages);
	}

	@Override
	public List<String> getMessages() {
		return messages;
	}

}
