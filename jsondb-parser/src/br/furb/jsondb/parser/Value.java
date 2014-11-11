package br.furb.jsondb.parser;

import java.util.Objects;

public abstract class Value<BaseType> {

	public static final Value<Object> NULL = new Value<Object>() {

		@Override
		public String toString() {
			return "NULL";
		}

	};

	private BaseType baseValue;

	public Value(BaseType baseValue) {
		this.baseValue = Objects.requireNonNull(baseValue, "value cannot be null. Use Value.NULL to represent null values");
	}

	private Value() {
		// reservado para Value.NULL
	}

	public BaseType getBaseValue() {
		return baseValue;
	}

	public String stringValue() {
		return baseValue.toString();
	}

	/**
	 * Retorna a representação do valor armazenado, no padrão:
	 * <p>
	 * &nbsp;&nbsp;&nbsp;&nbsp;{@code tipo(valor)}
	 * </p>
	 */
	@Override
	public abstract String toString();

}
