package br.furb.jsondb.parser;

public class NumberValue extends Value<Double> {

	public NumberValue(Double number) {
		super(number);
	}

	public static NumberValue parseNumber(String str) {
		return new NumberValue(Double.parseDouble(str));
	}

	@Override
	public String toString() {
		return "NUMBER(".concat(String.valueOf(getBaseValue())).concat(")");
	}

}
