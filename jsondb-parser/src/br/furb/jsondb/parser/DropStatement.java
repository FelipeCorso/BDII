package br.furb.jsondb.parser;

public class DropStatement<T extends IStructure> implements IStatement {

	private T structure;

	public DropStatement(T structure) {
		this.structure = structure;
	}

	public final T getStructure() {
		return structure;
	}

}
