package br.furb.jsondb.core.command;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.NumberValue;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.parser.StringValue;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.Value;
import br.furb.jsondb.parser.statement.InsertStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.data.RowData;
import br.furb.jsondb.store.data.TableData;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.TableMetadata;

public class InsertCommandTest extends BaseCommandTest {

	private static final String TABLE = "pessoa";
	private static final String DATABASE = "teste";

	@Test
	public void testInsert01() throws Exception {
		createDatabase(DATABASE);
		JsonDB.getInstance().setCurrentDatabase(DATABASE);
		createTable();

		InsertCommand insertCommand = createInsertCommand(123, "Jubileu");
		insertCommand.execute();

		TableMetadata tableMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(DATABASE).getTable(TABLE);
		TableData tableData = new TableData(tableMetadata, DATABASE);

		Map<Integer, RowData> rows = tableData.getRows();

		assertEquals(1, rows.size());

		RowData rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("nome").getValue());
		assertEquals(123.0, rowData.getColumn("codigo").getValue());

		// insere mais um registro:

		insertCommand = createInsertCommand(456, "Batatinha");
		insertCommand.execute();

		rows = tableData.getRows();

		assertEquals(2, rows.size());

		rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("nome").getValue());
		assertEquals(123.0, rowData.getColumn("codigo").getValue());

		rowData = rows.get(1);

		assertEquals("Batatinha", rowData.getColumn("nome").getValue());
		assertEquals(456.0, rowData.getColumn("codigo").getValue());

	}

	@Test
	public void testInsert02() throws Exception {
		createDatabase(DATABASE);
		JsonDB.getInstance().setCurrentDatabase(DATABASE);
		createTable();

		JsonDB.getInstance().executeSQL("insert into Pessoa(Codigo, Nome) values (123, \"Jubileu\");");

		TableMetadata tableMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(DATABASE).getTable(TABLE);
		TableData tableData = new TableData(tableMetadata, DATABASE);

		Map<Integer, RowData> rows = tableData.getRows();

		assertEquals(1, rows.size());

		RowData rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("nome").getValue());
		assertEquals(123.0, rowData.getColumn("codigo").getValue());

		// insere mais um registro:

		JsonDB.getInstance().executeSQL("insert into pessoa(codigo, nome) values (456, \"Batatinha\");");

		rows = tableData.getRows();

		assertEquals(2, rows.size());

		rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("nome").getValue());
		assertEquals(123.0, rowData.getColumn("codigo").getValue());

		rowData = rows.get(1);

		assertEquals("Batatinha", rowData.getColumn("nome").getValue());
		assertEquals(456.0, rowData.getColumn("codigo").getValue());

	}

	@Test
	public void testInsertDuplicatedKey() throws Exception {
		createDatabase(DATABASE);
		JsonDB.getInstance().setCurrentDatabase(DATABASE);
		createTable();

		JsonDB.getInstance().executeSQL("insert into Pessoa(Codigo, Nome) values (123, \"Jubileu\");");
		
		TableMetadata tableMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(DATABASE).getTable(TABLE);
		TableData tableData = new TableData(tableMetadata, DATABASE);

		Map<Integer, RowData> rows = tableData.getRows();

		assertEquals(1, rows.size());

		RowData rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("nome").getValue());
		assertEquals(123.0, rowData.getColumn("codigo").getValue());

		// insere um registro com a pk duplicada

		try {
			JsonDB.getInstance().executeSQL("insert into Pessoa(Codigo, Nome) values (123, \"Batatinha\");");
		} catch (SQLException e) {
			assertEquals("Primary key violation", e.getMessage());
		}

		rows = tableData.getRows();

		assertEquals(1, rows.size());

		rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("nome").getValue());
		assertEquals(123.0, rowData.getColumn("codigo").getValue());

	}

	private static InsertCommand createInsertCommand(int codigo, String nome) {
		Collection<ColumnIdentifier> columns = new ArrayList<ColumnIdentifier>();

		columns.add(new ColumnIdentifier("codigo"));
		columns.add(new ColumnIdentifier("nome"));

		Collection<Value<?>> values = new ArrayList<Value<?>>();

		values.add(new NumberValue((double) codigo));
		values.add(new StringValue(nome));

		InsertStatement insertStatement = new InsertStatement(new TableIdentifier(TABLE), columns, values);

		return new InsertCommand(insertStatement);
	}

	private static void createTable() throws SQLParserException, SQLException {
		JsonDB.getInstance().executeSQL("CREATE TABLE Pessoa(Codigo NUMBER(3) PRIMARY KEY, nome VARCHAR(45));");
	}

}
