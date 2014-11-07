package br.furb.jsondb.core.command;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;

import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.store.JsonDBProperty;

public abstract class BaseCommandTest {

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
//		FileUtils.deleteDirectory(tempDir);
	}
	
	protected void createDatabase(String database) {
		CreateStatement createStatement = new CreateStatement(new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(createStatement);

		assertFalse(command.execute().hasError());
	}
}
