package br.furb.jsondb.parser.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SemanticoTest {

	private Sintatico sintatico;
	private Lexico lexico;
	private Semantico semantico;

	private static class TestFiles {

		static final String TEST_CREATE_DATABASE = "testCreateDatabase";
		static final String TEST_CREATE_INDEX = "testCreateIndex";
		static final String TEST_CREATE_INDEX$QUOTED_ID = "testCreateIndex_quotedId";
		static final String TEST_CREATE_TABLE = "testCreateTable";
		static final String TEST_DESCRIBE_TABLE = "testDescribeTable";
		static final String TEST_DROP_INDEX = "testDropIndex";
		static final String TEST_DROP_INDEX$QUOTED_ID = "testDropIndex_quotedId";
		static final String TEST_DROP_TABLE = "testDropTable";
		static final String TEST_DROP_TABLE$QUOTED_ID = "testDropTable_quotedId";
		static final String TEST_INSERT_INTO$NO_SOURCE_COLUMNS$QUOTED_ID = "testInsertInto_noSourceColumns_quotedId";
		static final String TEST_SELECT_ALL_FROM_MANY$NO_WHERE = "testSelectAllFromMany_noWhere";
		static final String TEST_SELECT_ALL_FROM_ONE$ALL_WHERE_OPERATOS = "testSelectAllFromOne_allWhereOperatos";
		static final String TEST_SELECT_MANY_QUALIFIED_FROM_MANY = "testSelectManyQualifiedFromMany";
		static final String TEST_SET_DATABASE = "testSetDatabase";
		static final String TEST_SET_DATABASE$QUOTED_ID = "testSetDatabase_quotedId";

	}

	@Before
	public void setUp() {
		sintatico = new Sintatico();
		semantico = new Semantico();
	}

	@Test
	public void testCreateDatabase() throws Exception {
		parse(TestFiles.TEST_CREATE_DATABASE);

		fail("Not yet implemented");
	}

	public void parse(String program) throws LexicalError, SyntaticError {
		this.lexico = new Lexico(program);
		sintatico.parse(lexico, semantico);
	}

}
