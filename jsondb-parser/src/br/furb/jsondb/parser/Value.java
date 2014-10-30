package br.furb.jsondb.parser;

public abstract class Value<BaseType> {

	public static final Value<Object> NULL = new Value<Object>(null) {

		public String toString() {
			return "NULL";
		}

	};

	private BaseType baseValue;

	public Value(BaseType baseValue) {
		this.baseValue = baseValue;
	}

	public BaseType getBaseValue() {
		return baseValue;
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
