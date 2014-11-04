package br.furb.jsondb.parser.core;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.furb.jsondb.parser.IStatement;

public class SemanticoTest {

	private static enum TestFiles {

		TEST_CREATE_DATABASE("testCreateDatabase"), //
		TEST_CREATE_INDEX("testCreateIndex"), //
		TEST_CREATE_INDEX_$QUOTED_ID("testCreateIndex_quotedId"), //
		TEST_CREATE_TABLE("testCreateTable"), //
		TEST_DESCRIBE_TABLE("testDescribeTable"), //
		TEST_DROP_INDEX("testDropIndex"), //
		TEST_DROP_INDEX_$QUOTED_ID("testDropIndex_quotedId"), //
		TEST_DROP_TABLE("testDropTable"), //
		TEST_DROP_TABLE_$QUOTED_ID("testDropTable_quotedId"), //
		TEST_INSERT_INTO_$NO_SOURCE_COLUMNS_$QUOTED_ID("testInsertInto_noSourceColumns_quotedId"), //
		TEST_SELECT_ALL_FROM_MANY_$NO_WHERE("testSelectAllFromMany_noWhere"), //
		TEST_SELECT_ALL_FROM_ONE_$ALL_WHERE_OPERATOS("testSelectAllFromOne_allWhereOperatos"), //
		TEST_SELECT_MANY_QUALIFIED_FROM_MANY("testSelectManyQualifiedFromMany"), //
		TEST_SET_DATABASE("testSetDatabase"), //
		TEST_SET_DATABASE_$QUOTED_ID("testSetDatabase_quotedId"); //

		private String fileName;

		TestFiles(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}

		public File getFile() {
			return new File(this.fileName);
		}

		public File getFile(File parentDir) {
			return new File(parentDir, this.fileName);
		}

		public String readFile() throws FileNotFoundException, IOException {
			return readFile(new File("."));
		}

		public String readFile(File parentDir) throws FileNotFoundException, IOException {
			StringBuilder content = new StringBuilder();
			try (FileReader fr = new FileReader(getFile(parentDir)); //
					BufferedReader bf = new BufferedReader(fr)) {
				String line = null;
				while ((line = bf.readLine()) != null) {
					content.append(line);
				}
			}
			return content.toString();
		}
	}

	private static final File TESTS_DIR = SemanticoTest.class.getClassLoader().;

	private Sintatico sintatico;
	private Lexico lexico;
	private Semantico semantico;

	@Before
	public void setUp() {
		sintatico = new Sintatico();
		semantico = new Semantico();
	}

	@Test
	public void testCreateDatabase() throws Exception {
		IStatement stm = parse(TestFiles.TEST_CREATE_DATABASE);

		fail("Not yet implemented");
	}

	public IStatement parse(TestFiles testFile) throws LexicalError, SyntaticError {
		try {
			this.lexico = new Lexico(testFile.readFile(TESTS_DIR));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		sintatico.parse(lexico, semantico);
		return semantico.getStatement();
	}

}
