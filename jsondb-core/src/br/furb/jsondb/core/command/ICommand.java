package br.furb.jsondb.core.command;

import br.furb.jsondb.core.SQLException;
import br.furb.jsondb.core.result.IResult;

public interface ICommand {

	IResult execute() throws SQLException;

}
