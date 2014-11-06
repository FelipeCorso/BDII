package br.furb.jsondb.parser.statement;

import java.util.Objects;

import br.furb.jsondb.parser.IStructure;

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

	@Override
	public String toString() {
		return "CREATE ".concat(getStructure().toString());
	}

}
