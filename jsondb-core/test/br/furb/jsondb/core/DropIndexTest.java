package br.furb.jsondb.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;

public class DropIndexTest extends BaseJsonDBTest {

	@Before
	public void before() throws SQLParserException, SQLException {
		executeSQL(
		/**/"CREATE TABLE aluno("
		/**/+ "codigo NUMBER(4), "
		/**/+ "nome VARCHAR(30), "
		/**/+ "sexo CHAR(1) NOT NULL, "
		/**/+ "dataNasc DATE, "
		/**/+ "mediaNotas NUMBER(2,1),"
		/**/+ " constraint pk_aluno PRIMARY KEY (codigo) ) ;");

		String sql = "CREATE INDEX idx_nome on aluno(nome);";
		executeSQL(sql);
	}

	@Override
	public void after() throws IOException {
		try {
			executeSQL("DROP TABLE aluno;");
		} catch (SQLParserException | SQLException e) {
			throw new RuntimeException(e);
		}
		super.after();
	}

	@Test
	public void testDropIndex01() throws Exception {
		String sql = "DROP INDEX idx_nome on aluno;";

		executeSQL(sql);
	}

	@Test
	public void testDropIndex02() throws Exception {
		String sql = "DROP INDEX idx_x on aluno;";

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Index 'idx_x' not found on table 'aluno'", e.getMessage());
		}

	}

	@Test
	public void testDropIndex03() throws Exception {
		String sql = "DROP INDEX idx_nome on y;";

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Table 'y' not found", e.getMessage());
		}

	}

	private void executeSQL(String sql) throws SQLParserException, SQLException {
		JsonDB.getInstance().executeSQL(sql);
	}

}
