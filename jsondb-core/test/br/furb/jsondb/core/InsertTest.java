package br.furb.jsondb.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;

public class InsertTest extends BaseJsonDBTest {

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

		executeSQL("CREATE INDEX idx_nome on aluno(nome);");
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

	/**
	 * Testa o comando insert com colunas
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInsert01() throws Exception {

		String sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(1234, \"João\", \"M\", 8.5);";

		executeSQL(sql);
	}

	/**
	 * Testa o comando insert sem colunas
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInsert02() throws Exception {

		String sql = "INSERT INTO aluno VALUES(1234, \"João\", \"M\", 03/05/2005, 8.5);";

		executeSQL(sql);

		sql = "INSERT INTO aluno VALUES(3456, \"João\", \"M\", 03/05/2005, 8.5);";
		executeSQL(sql);
	}

	/**
	 * Testa o comando insert sem colunas faltando valores
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInsert03() throws Exception {

		String sql = "INSERT INTO aluno VALUES(1234, \"João\", \"M\", 03/05/2005);";

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Column count does not match", e.getMessage());
		}
	}

	/**
	 * Testa o comando insert com colunas faltando valores
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInsert04() throws Exception {

		String sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(1234, \"João\", \"M\");";

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Column count does not match", e.getMessage());
		}
	}

	/**
	 * Testa o comando insert em tabela sem pk
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInsert05() throws Exception {

		String sql = "INSERT INTO livro VALUES(\"abc\", \"fulano de tal\");";
		executeSQL(sql);

		sql = "INSERT INTO livro VALUES(\"def\", \"ciclano de tal\");";
		executeSQL(sql);

		sql = "INSERT INTO livro VALUES(\"abc\", \"fulano de tal\");";
		executeSQL(sql);

	}

	/**
	 * Testa o comando insert com coluna inexistente na tabela
	 * 
	 * @throws Exception
	 */
	@Test
	public void testColumnNotFound() throws Exception {

		String sql = "INSERT INTO aluno (codigo, nome, inexistente) VALUES(1234, \"João\", \"M\");";

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Column 'inexistente' not found on table aluno", e.getMessage());
		}
	}

	/**
	 * Testa o comando insert tabela inexistente
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTableNotFound() throws Exception {

		String sql = "INSERT INTO pessoa (codigo, nome, inexistente) VALUES(1234, \"João\", \"M\");";

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Table 'pessoa' not found", e.getMessage());
		}
	}

	@Test
	public void testNullValuePK() throws Exception {
		String sql = "INSERT INTO aluno ( nome, sexo) VALUES( \"João\", \"M\");";

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Column 'codigo' can't be null.", e.getMessage());
		}
	}

	@Test
	public void testNotNullValidation() throws Exception {
		String sql = "INSERT INTO aluno ( codigo, nome ) VALUES( 1234, \"João\");";

		try {
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Column 'sexo' can't be null.", e.getMessage());
		}
	}

	/**
	 * Testa insert com chave duplicada
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDuplcateKey() throws Exception {

		String sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(1234, \"João\", \"M\", 8.5);";

		executeSQL(sql);

		try {
			sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(1234, \"Maria\", \"F\", 9.0);";
			executeSQL(sql);
			fail();
		} catch (SQLException e) {
			assertEquals("Primary key violation", e.getMessage());
		}
	}

	@Test
	public void testPrecisionValidation() throws Exception {
		try {

			String sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(1234, \"João\", \"M\", 8.55);";
			executeSQL(sql);

			fail();

		} catch (SQLException e) {
			assertEquals("Invalid value length. Value: '8.55'. Column: 'medianotas'", e.getMessage());
		}

	}

	/**
	 * Testa a validação de tamanho de campo tipo number com precisão
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLengthValidation01() throws Exception {
		try {

			String sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(1234, \"João\", \"M\", 111.5);";
			executeSQL(sql);

			fail();

		} catch (SQLException e) {
			assertEquals("Invalid value length. Value: '111.5'. Column: 'medianotas'", e.getMessage());
		}

	}

	/**
	 * Testa a validação de tamanho de campo tipo number sem precisão
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLengthValidation02() throws Exception {
		try {

			String sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(12345, \"João\", \"M\", 8.5);";
			executeSQL(sql);

			fail();

		} catch (SQLException e) {
			//FIXME verificar se é um problema o valor ficar diferente do que foi declarado
			assertEquals("Invalid value length. Value: '12345.0'. Column: 'codigo'", e.getMessage());
		}

	}

	/**
	 * Testa a validação de tamanho de campo tipo VARCHAR
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLengthValidation03() throws Exception {

		// 31 caracteres
		try {

			String sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(1234, \"João xxxxxxxxxxxxxxxxxxxxxxxxxx\", \"M\", 8.5);";
			executeSQL(sql);

			fail();

		} catch (SQLException e) {
			assertEquals("Invalid value length. Value: 'João xxxxxxxxxxxxxxxxxxxxxxxxxx'. Column: 'nome'", e.getMessage());
		}

		// 30 caracteres

		String sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(1234, \"João xxxxxxxxxxxxxxxxxxxxxxxxx\", \"M\", 8.5);";
		executeSQL(sql);

	}

	/**
	 * Testa a validação de tamanho de campo tipo CHAR
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLengthValidation04() throws Exception {
		try {

			String sql = "INSERT INTO aluno (codigo, nome, sexo, mediaNotas) VALUES(1234, \"João\", \"MM\", 8.5);";
			executeSQL(sql);

			fail();

		} catch (SQLException e) {
			assertEquals("Invalid value length. Value: 'MM'. Column: 'sexo'", e.getMessage());
		}

	}

	/**
	 * Insere valor decimal em campo inteiro
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDataTypeValidation01() throws Exception {
		try {

			String sql = "INSERT INTO aluno (codigo) VALUES(12.4);";
			executeSQL(sql);

			fail();

		} catch (SQLException e) {
			assertEquals("Invalid value length. Value: '12.4'. Column: 'codigo'", e.getMessage());
		}

	}

	/**
	 * Insere valor string em campo inteiro
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDataTypeValidation02() throws Exception {
		try {

			String sql = "INSERT INTO aluno (codigo) VALUES(\"a\");";
			executeSQL(sql);

			fail();

		} catch (SQLException e) {
			assertEquals("Invalid value type. Value: 'a'. Column: 'codigo'", e.getMessage());
		}

	}

	/**
	 * Insere valor string em campo char
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDataTypeValidation03() throws Exception {
		try {

			String sql = "INSERT INTO aluno (codigo, sexo) VALUES(1234, \"masculino\");";
			executeSQL(sql);

			fail();

		} catch (SQLException e) {
			assertEquals("Invalid value length. Value: 'masculino'. Column: 'sexo'", e.getMessage());
		}

	}

	/**
	 * Insere valor string em campo date
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDataTypeValidation04() throws Exception {
		try {

			String sql = "INSERT INTO aluno (codigo, dataNasc) VALUES(1234, \"abril\");";
			executeSQL(sql);

			fail();

		} catch (SQLException e) {
			assertEquals("Invalid value type. Value: 'abril'. Column: 'datanasc'", e.getMessage());
		}

	}

	private void executeSQL(String sql) throws SQLParserException, SQLException {
		JsonDB.getInstance().executeSQL(sql);
	}

}
