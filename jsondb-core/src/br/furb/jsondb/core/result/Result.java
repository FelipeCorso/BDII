package br.furb.jsondb.core.result;

import java.util.Arrays;
import java.util.List;

public class Result implements IResult {

	private List<String> messages;
	private boolean hasError;

	public Result(boolean hasError, String... messages) {
		this.hasError = hasError;
		this.messages = Arrays.asList(messages);
	}

	@Override
	public List<String> getMessages() {
		return messages;
	}

	@Override
	public boolean hasError() {
		return hasError;
	}
}
