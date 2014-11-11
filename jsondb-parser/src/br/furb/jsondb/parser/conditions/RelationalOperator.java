package br.furb.jsondb.parser.conditions;

public enum RelationalOperator {

	EQUAL("="), GREATER(">"), LESSER("<"), GREATER_OR_EQUAL(">="), LESSER_OR_EQUAL("<="), NOT_EQUAL("<>");
	
	private String representation;

	private RelationalOperator(String representation) {
		this.representation = representation;
	}
	
	@Override
	public String toString() {
		return representation;
	}

}
