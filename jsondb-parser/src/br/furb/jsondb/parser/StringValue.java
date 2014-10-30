package br.furb.jsondb.parser;


public class StringValue extends Value<String> {

	public StringValue(String value) {
		super(value);
	}

	@Override
	public String toString() {
		return "STRING(".concat(getBaseValue()).concat(")");
	}

}
