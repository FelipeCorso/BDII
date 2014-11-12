package br.furb.jsondb.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;

public class CreateIndexTest extends BaseJsonDBTest {

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

		executeSQL(
		/**/"CREATE TABLE livro("
		/**/+ "titulo VARCHAR(40),"
		/**/+ "autor VARCHAR(40));");
	}

	@Override
	public void after() throws IOException {
		try {
			executeSQL("DROP TABLE aluno;");
			executeSQL("DROP TABLE livro;");
		} catch (SQLParserException | SQLException e) {
			throw new RuntimeException(e);
		}
		super.after();
	}

	@Test
	public void testCreateIndex01() throws Exception {
		String sql = "CREATE INDEX idx_nome on aluno(nome);";

		executeSQL(sql);

	}

	@Test
	public void testCreateIndex02() throws Exception {
		String sql = "CREATE INDEX idx_nome on aluno(nome);";

		executeSQL(sql);

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Duplicate index name 'idx_nome'", e.getMessage());
		}

	}

	@Test
	public void testCreateIndex03() throws Exception {
		String sql = "CREATE INDEX idx_nome on aluno(x);";

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Column 'x' not found on table 'aluno'", e.getMessage());
		}

	}

	@Test
	public void testCreateIndex04() throws Exception {
		String sql = "CREATE INDEX idx_nome on y(x);";

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
