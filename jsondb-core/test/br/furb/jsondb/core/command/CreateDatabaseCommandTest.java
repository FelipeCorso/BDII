package br.furb.jsondb.core.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.store.JsonDBStore;

public class CreateDatabaseCommandTest extends BaseCommandTest {

	/**
	 * Testa a execu��o do comando quando ainda n�o existe um banco com o mesmo
	 * nome
	 */
	@Test
	public void testCreateDatabase01() {

		String database = "database1";
		CreateStatement createStatement = new CreateStatement(new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(createStatement);

		IResult result = command.execute();

		assertEquals("Database database1 created with success", result.getMessages().get(0));
		assertFalse(result.hasError());

		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		assertTrue(databaseDir.exists());
		assertTrue(databaseDir.isDirectory());
	}

	/**
	 * Testa a execu��o do comando quando j� existe um banco com o mesmo
	 * nome
	 */
	@Test
	public void testCreateDatabase02() {

		String database = "xxx";
		CreateStatement createStatement = new CreateStatement(
				new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(
				createStatement);
		IResult result = command.execute();
		assertFalse(result.hasError());

		result = command.execute();

		assertTrue(result.hasError());
		assertEquals("Database xxx already exists", result.getMessages().get(0));

		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		assertTrue(databaseDir.exists());
		assertTrue(databaseDir.isDirectory());
	}
}