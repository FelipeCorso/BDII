package br.furb.jsondb.core.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.store.JsonDBProperty;
import br.furb.jsondb.store.JsonDBStore;

public class CreateDatabaseCommandTest {

	private File tempDir;
	private String bkpProperty;

	@Before
	public void before() throws IOException {

		tempDir = File.createTempFile("jsondb", null);
		tempDir.delete();
		assertTrue(tempDir.getAbsolutePath(), tempDir.mkdir());

		bkpProperty = JsonDBProperty.JSON_DB_DIR.set(tempDir.getAbsolutePath());
	}

	@After
	public void after() throws IOException {
		JsonDBProperty.JSON_DB_DIR.set(bkpProperty);
		FileUtils.deleteDirectory(tempDir);
	}

	/**
	 * Testa a execuÁ„o do comando quando ainda n„o existe um banco com o mesmo
	 * nome
	 */
	@Test
	public void testCreateDatabase01() {

		String database = "database1";
		CreateStatement createStatement = new CreateStatement(
				new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(
				createStatement);

		IResult result = command.execute();

		assertEquals("Database database1 created with success", result
				.getMessages().get(0));
		assertFalse(result.hasError());

		File databaseDir = JsonDBStore.getInstance().getDatabaseDir(database);

		assertTrue(databaseDir.exists());
		assertTrue(databaseDir.isDirectory());
	}

	/**
	 * Testa a execu√ß√£o do comando quando o j√° existe um banco com o mesmo
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