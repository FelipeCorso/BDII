package br.furb.jsondb.core.command;

import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.sql.SQLException;

public interface ICommand {

	IResult execute() throws SQLException;

}
