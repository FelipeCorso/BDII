package br.furb.jsondb.core.command;

import static org.junit.Assert.*;

import org.junit.Test;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.parser.SetDatabaseStatement;
import br.furb.jsondb.parser.TableIdentifier;

public class SetDatabaseCommandTest extends BaseCommandTest {

	@Test
	public void testSetDabase01() {

		SetDatabaseStatement statement = new SetDatabaseStatement(
				new TableIdentifier("banco1"));
		SetDatabaseCommand setDatabaseCommand = new SetDatabaseCommand(
				statement);
		IResult result = setDatabaseCommand.execute();

		assertTrue(result.hasError());

		assertEquals("Database banco1 not found", result.getMessages().get(0));
	}

	@Test
	public void testSetDabase02() {

		// primeiro cria a base:

		String database = "banco1";
		CreateStatement createStatement = new CreateStatement(
				new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(
				createStatement);

		command.execute();

		//

		SetDatabaseStatement statement = new SetDatabaseStatement(
				new TableIdentifier(database));
		SetDatabaseCommand setDatabaseCommand = new SetDatabaseCommand(
				statement);
		IResult result = setDatabaseCommand.execute();

		assertFalse(result.hasError());

		assertEquals("Current database is banco1", result.getMessages().get(0));

		assertEquals("banco1", JsonDB.getInstance().getCurrentDatabase());
	}
}
