package br.furb.jsondb.parser.conditions;



/**
 * Condição lógica, composta de um operador e dois operandos.
 */
public abstract class LogicalCondition implements IExpression {

	private LogicalOperator operator;

	@Override
	public boolean isLogical() {
		return true;
	}

	public abstract IExpression getLeftOperand();

	public abstract IExpression getRightOperand();

	public LogicalOperator getOperator() {
		return operator;
	}

}
