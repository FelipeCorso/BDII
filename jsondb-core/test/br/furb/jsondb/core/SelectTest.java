package br.furb.jsondb.core;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.IResultSet;
import br.furb.jsondb.core.result.ResultRow;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.sql.SQLException;

public class SelectTest extends BaseJsonDBTest {

	@Before
	public void before() throws SQLParserException, SQLException {
		executeSQL(
		/**/"CREATE TABLE PRODUTO(" +
		/**/"CODIGO NUMBER(6)," +
		/**/"DESCRICAO VARCHAR(50)," +
		/**/"VALOR NUMBER(6,2)," +
		/**/"CONSTRAINT PK_PRODUTO PRIMARY KEY (CODIGO)" +
		/**/");");

		executeSQL("CREATE TABLE CLIENTE(" +
		/**/"CODIGO NUMBER(4) PRIMARY KEY," +
		/**/"NOME VARCHAR(100)," +
		/**/"SEXO CHAR(1)," +
		/**/"DATA_CADASTRO DATE," +
		/**/"TELEFONE VARCHAR(15)" +
		/**/");");

		executeSQL("CREATE TABLE PEDIDO(" +
		/**/"NUMERO_PEDIDO NUMBER(6) PRIMARY KEY," +
		/**/"DATA DATE," +
		/**/"VALOR_TOTAL NUMBER(10,2)," +
		/**/"CODIGO_CLIENTE NUMBER(4)" +
		/**/");");

		executeSQL("CREATE TABLE ITEM_PEDIDO(" +
		/**/"NUMERO_PEDIDO NUMBER(6)," +
		/**/"CODIGO_PRODUTO NUMBER(6)," +
		/**/"QUANTIDADE NUMBER(6)," +
		/**/"CONSTRAINT PK_ITEM_PEDIDO PRIMARY KEY (NUMERO_PEDIDO, CODIGO_PRODUTO)" +
		/**/");");

		executeSQL("CREATE INDEX IDX_NOME ON CLIENTE(NOME);");

		executeSQL("INSERT INTO PRODUTO VALUES(1, \"Teclado usb\", 31.90);");
		executeSQL("INSERT INTO PRODUTO VALUES(2, \"Mouse usb\", 41.90);");
		executeSQL("INSERT INTO PRODUTO VALUES(4, \"Teclado usb\", 35);");
		executeSQL("INSERT INTO PRODUTO VALUES(3, \"Notebook core i5 8GB HD 1T\", 1889);");

		executeSQL("INSERT INTO CLIENTE VALUES(100, \"Maria Santos\", \"F\", 01/12/2012, \"47-3344-0000\" );");
		executeSQL("INSERT INTO CLIENTE VALUES(101, \"Bruna da Silva\", \"F\", 01/06/2013, \"47-3344-0011\" );");
		executeSQL("INSERT INTO CLIENTE VALUES(102, \"Marcos\", \"M\", 02/03/2014, \"\" );");

		executeSQL("INSERT INTO PEDIDO VALUES(1000, 10/11/2014, 2500, 101);");

	}

	@Test
	public void testSelect01() throws Exception {
		String sql = "SELECT * FROM PRODUTO;";
		IResultSet rs = executeQuery(sql);

		List<ResultRow> rows = rs.getRows();

		assertEquals(4, rows.size());

		Map<String, Object> columnsValues = new LinkedHashMap<String, Object>();

		columnsValues.put("codigo", 1.0);
		columnsValues.put("descricao", "Teclado usb");
		columnsValues.put("valor", 31.9);

		assertEquals(columnsValues, rows.get(0).getColumns());

		columnsValues.put("codigo", 2.0);
		columnsValues.put("descricao", "Mouse usb");
		columnsValues.put("valor", 41.9);

		assertEquals(columnsValues, rows.get(1).getColumns());

		columnsValues.put("codigo", 4.0);
		columnsValues.put("descricao", "Teclado usb");
		columnsValues.put("valor", 35.0);

		assertEquals(columnsValues, rows.get(2).getColumns());

		columnsValues.put("codigo", 3.0);
		columnsValues.put("descricao", "Notebook core i5 8GB HD 1T");
		columnsValues.put("valor", 1889.0);

		assertEquals(columnsValues, rows.get(3).getColumns());
	}

	@Test
	public void testSelect02() throws Exception {
		String sql = "SELECT descricao, valor FROM PRODUTO;";
		IResultSet rs = executeQuery(sql);

		List<ResultRow> rows = rs.getRows();

		assertEquals(4, rows.size());

		Map<String, Object> columnsValues = new LinkedHashMap<String, Object>();

		columnsValues.put("descricao", "Teclado usb");
		columnsValues.put("valor", 31.9);

		assertEquals(columnsValues, rows.get(0).getColumns());

		columnsValues.put("descricao", "Mouse usb");
		columnsValues.put("valor", 41.9);

		assertEquals(columnsValues, rows.get(1).getColumns());

		columnsValues.put("descricao", "Teclado usb");
		columnsValues.put("valor", 35.0);

		assertEquals(columnsValues, rows.get(2).getColumns());

		columnsValues.put("descricao", "Notebook core i5 8GB HD 1T");
		columnsValues.put("valor", 1889.0);

		assertEquals(columnsValues, rows.get(3).getColumns());
	}

	@Ignore("join não suportado")
	@Test
	public void testSelect03() throws Exception {

		String sql = "SELECT pedido.numero_pedido, pedido.data, cliente.nome, pedido.valor_total FROM pedido, cliente;";
		IResultSet rs = executeQuery(sql);

		List<ResultRow> rows = rs.getRows();

		for (ResultRow resultRow : rows) {
			System.out.println(resultRow);
		}

		assertEquals(3, rows.size());

		//TODO

	}

	@Override
	public void after() throws IOException {
		try {
			executeSQL("DROP TABLE produto;");
			executeSQL("DROP TABLE cliente;");
			executeSQL("DROP TABLE pedido;");
			executeSQL("DROP TABLE item_pedido;");
		} catch (SQLParserException | SQLException e) {
			throw new RuntimeException(e);
		}
		super.after();
	}

	private IResult executeSQL(String sql) throws SQLParserException, SQLException {
		return JsonDB.getInstance().executeSQL(sql);
	}

	private IResultSet executeQuery(String sql) throws SQLParserException, SQLException {
		return (IResultSet) JsonDB.getInstance().executeSQL(sql);
	}

}
