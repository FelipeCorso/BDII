package br.furb.jsondb.core.command;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

import br.furb.jsondb.core.SQLException;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.store.JsonDBProperty;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.data.IndexDataProvider;
import br.furb.jsondb.store.data.TableDataProvider;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

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
		FileUtils.deleteDirectory(tempDir);
		DatabaseMetadataProvider.reset();
		TableDataProvider.reset();
		IndexDataProvider.reset();
	}

	protected void createDatabase(String database) throws SQLException {
		CreateStatement createStatement = new CreateStatement(new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(createStatement);

		System.out.println(JsonDBStore.getInstance().getDatabaseDir(database));
		command.execute();
	}
}
