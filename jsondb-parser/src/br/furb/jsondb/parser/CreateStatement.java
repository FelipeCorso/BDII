package br.furb.jsondb.parser;

import java.util.Objects;

public class CreateStatement implements IStatement {

	private IStructure structure;

	public CreateStatement(IStructure structure) {
		setStructure(Objects.requireNonNull(structure));
	}

	public final IStructure getStructure() {
		return structure;
	}

	public final void setStructure(IStructure structure) {
		this.structure = Objects.requireNonNull(structure, "uma estrutura deve ser informada para criação");
	}

}
