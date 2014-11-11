package br.furb.jsondb.parser.conditions;

import java.util.Objects;

import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.Value;

/**
 * Condição relacional, onde dois valores são comparados.
 * 
 * @author William Leander Seefeld
 *
 * @param <BaseType>
 *            tipo dos valores sendo comparados.
 */
public class RelationalCondition implements ICondition<Value<?>, RelationalOperator> {

	private RelationalOperator operator;
	private ColumnIdentifier leftOperand;
	private Value<?> rightOperand;

	@Override
	public boolean isLogical() {
		return false;
	}

	public RelationalCondition(RelationalOperator operator, ColumnIdentifier leftOperand, Value<?> rightOperand) {
		this.operator = Objects.requireNonNull(operator, "operator cannot be null");
		this.leftOperand = Objects.requireNonNull(leftOperand, "left operand cannot be null");
		this.rightOperand = Objects.requireNonNull(rightOperand, "right operand cannot be null");
	}

	@Override
	public RelationalOperator getOperator() {
		return operator;
	}

	public ColumnIdentifier getLeftOperand() {
		return leftOperand;
	}

	@Override
	public Value<?> getRightOperand() {
		return rightOperand;
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
