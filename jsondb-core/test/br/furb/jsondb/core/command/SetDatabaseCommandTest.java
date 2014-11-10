package br.furb.jsondb.core.command;

import static org.junit.Assert.*;

import org.junit.Test;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.SQLException;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.parser.statement.SetDatabaseStatement;

public class SetDatabaseCommandTest extends BaseCommandTest {

	@Test
	public void testSetDabase01() {

		SetDatabaseStatement statement = new SetDatabaseStatement(new DatabaseIdentifier("banco1"));
		SetDatabaseCommand setDatabaseCommand = new SetDatabaseCommand(statement);
		try {
			setDatabaseCommand.execute();
			fail();
		} catch (SQLException e) {
			assertEquals("Database banco1 not found", e.getMessage());

		}

	}

	@Test
	public void testSetDabase02() throws SQLException {

		// primeiro cria a base:

		String database = "banco1";
		CreateStatement createStatement = new CreateStatement(new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(createStatement);

		command.execute();

		//

		SetDatabaseStatement statement = new SetDatabaseStatement(new DatabaseIdentifier(database));
		SetDatabaseCommand setDatabaseCommand = new SetDatabaseCommand(statement);
		IResult result = setDatabaseCommand.execute();

		assertEquals("Current database is banco1", result.getMessages().get(0));

		assertEquals("banco1", JsonDB.getInstance().getCurrentDatabase());
	}
}
