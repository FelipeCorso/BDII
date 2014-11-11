package br.furb.jsondb.parser;

import br.furb.jsondb.parser.conditions.ICondition;

public class WhereClause {

	private ICondition<?, ?> initialCondition;

	public WhereClause(ICondition<?, ?> initialCondition) {
		super();
		this.initialCondition = initialCondition;
	}

	public ICondition<?, ?> getInitialCondition() {
		return initialCondition;
	}

	@Override
	public String toString() {
		return "WHERE ".concat(String.valueOf(initialCondition));
	}

}
