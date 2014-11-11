package br.furb.jsondb.parser.conditions;

import java.util.Objects;

/**
 * Condição lógica, composta de um operador e dois operandos.
 */
public class LogicalCondition implements ICondition<ICondition<?, ?>, LogicalOperator> {

	private LogicalOperator operator;
	private ICondition<?, ?> leftOperand;
	private ICondition<?, ?> rightOperand;

	public LogicalCondition(LogicalOperator operator) {
		this.operator = Objects.requireNonNull(operator, "operator cannot be null");
	}

	@Override
	public boolean isLogical() {
		return true;
	}

	@Override
	public LogicalOperator getOperator() {
		return operator;
	}

	public ICondition<?, ?> getLeftOperand() {
		return leftOperand;
	}

	public void setLeftOperand(ICondition<?, ?> leftOperand) {
		this.leftOperand = leftOperand;
	}

	@Override
	public ICondition<?, ?> getRightOperand() {
		return rightOperand;
	}

	public void setRightOperand(ICondition<?, ?> rightOperand) {
		this.rightOperand = rightOperand;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getLeftOperand());
		sb.append(' ');
		sb.append(getOperator());
		sb.append(' ');
		sb.append(getRightOperand());

		return sb.toString();
	}

}
