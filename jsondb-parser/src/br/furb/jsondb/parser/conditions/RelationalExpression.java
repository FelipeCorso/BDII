package br.furb.jsondb.parser.conditions;

import br.furb.jsondb.parser.Value;

public class RelationalExpression<BaseType> implements IExpression {

	private RelationalOperator operator;
	private Value<BaseType> leftOperand;
	private Value<BaseType> rigthOperand;
	
	@Override
	public boolean isLogical() {
		return false;
	}

}
