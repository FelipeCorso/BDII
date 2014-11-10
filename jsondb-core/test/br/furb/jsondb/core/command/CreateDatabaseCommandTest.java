package br.furb.jsondb.core.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import br.furb.jsondb.core.SQLException;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.store.JsonDBStore;

public class CreateDatabaseCommandTest extends BaseCommandTest {

	/**
	 * Testa a execução do comando quando ainda não existe um banco com o mesmo
	 * nome
	 * @throws SQLException 
	 */
	@Test
	public void testCreateDatabase01() throws SQLException {

		String database = "database1";
		CreateStatement createStatement = new CreateStatement(new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(createStatement);

		IResult result = command.execute();

		assertEquals("Database database1 created with success", result.getMessages().get(0));

		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		assertTrue(databaseDir.exists());
		assertTrue(databaseDir.isDirectory());
	}

	/**
	 * Testa a execução do comando quando já existe um banco com o mesmo
	 * nome
	 * @throws SQLException 
	 */
	@Test
	public void testCreateDatabase02() throws SQLException {

		String database = "xxx";
		CreateStatement createStatement = new CreateStatement(
				new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(
				createStatement);
		command.execute();

		try{
			command.execute();
			fail();
		}catch(SQLException e){
			assertEquals("Database xxx already exists", e.getMessage());
		}


		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		assertTrue(databaseDir.exists());
		assertTrue(databaseDir.isDirectory());
	}
}