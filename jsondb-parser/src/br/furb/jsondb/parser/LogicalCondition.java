package br.furb.jsondb.parser;

public abstract class LogicalCondition implements ICondition {

	private LogicalOperator operator;

	@Override
	public boolean isLogical() {
		return true;
	}

	public abstract ICondition getLeftOperand();

	public abstract ICondition getRightOperand();

	public LogicalOperator getOperator() {
		return operator;
	}

}
