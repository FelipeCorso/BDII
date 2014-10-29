package br.furb.jsondb.core.command;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;

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
		FileUtils.deleteDirectory(tempDir);
	}
}
