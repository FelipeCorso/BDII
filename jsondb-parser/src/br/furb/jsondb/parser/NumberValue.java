package br.furb.jsondb.parser;

public class NumberValue extends Value<Long> {

	public NumberValue(Long number) {
		super(number);
	}

	public static NumberValue parseNumber(String str) {
		return new NumberValue(Long.parseLong(str));
	}

	@Override
	public String toString() {
		return "NUMBER(".concat(String.valueOf(getBaseValue())).concat(")");
	}

}
