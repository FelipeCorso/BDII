package br.furb.jsondb.core;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.furb.jsondb.core.command.CreateDatabaseCommand;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBProperty;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.data.IndexDataProvider;
import br.furb.jsondb.store.data.TableDataProvider;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

public class BaseJsonDBTest {
	protected static final String DATABASE = "teste";
	private static File tempDir;
	private static String bkpProperty;

	@BeforeClass
	public static void beforeClass() throws IOException, SQLException {
		tempDir = File.createTempFile("jsondb", null);
		tempDir.delete();
		assertTrue(tempDir.getAbsolutePath(), tempDir.mkdir());

		bkpProperty = JsonDBProperty.JSON_DB_DIR.set(tempDir.getAbsolutePath());
		
		createDatabase(DATABASE);
		JsonDB.getInstance().setCurrentDatabase(DATABASE);
	}

	@After
	public void after() throws IOException {
		DatabaseMetadataProvider.reset();
		TableDataProvider.reset();
		IndexDataProvider.reset();
		JsonDBStore.reset();
	}
	
	@AfterClass
	public static void afterClass() throws IOException {
		JsonDBProperty.JSON_DB_DIR.set(bkpProperty);
//		FileUtils.deleteDirectory(tempDir);
		DatabaseMetadataProvider.reset();
		TableDataProvider.reset();
		IndexDataProvider.reset();
		JsonDBStore.reset();
	}

	protected static void createDatabase(String database) throws SQLException {
		CreateStatement createStatement = new CreateStatement(new DatabaseIdentifier(database));
		CreateDatabaseCommand command = new CreateDatabaseCommand(createStatement);

		System.out.println(JsonDBStore.getInstance().getDatabaseDir(database));
		command.execute();

	}
}
