package br.furb.jsondb.core.command;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.ColumnType;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.DataType;
import br.furb.jsondb.parser.InsertStatement;
import br.furb.jsondb.parser.KeyDefinition;
import br.furb.jsondb.parser.NumberValue;
import br.furb.jsondb.parser.StringValue;
import br.furb.jsondb.parser.TableDefinition;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.Value;
import br.furb.jsondb.store.data.RowData;
import br.furb.jsondb.store.data.TableData;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.TableMetadata;

public class InsertCommandTest extends BaseCommandTest {

	private static final String TABLE = "PESSOA";
	private static final String DATABASE = "teste";

	@Test
	public void testInsert01() throws Exception {
		createDatabase(DATABASE);
		JsonDB.getInstance().setCurrentDatabase(DATABASE);
		createTable();

		InsertCommand insertCommand = createInsertCommand(123, "Jubileu");
		IResult result = insertCommand.execute();

		assertFalse(result.getMessages().toString(), result.hasError());

		TableMetadata tableMetadata = DatabaseMetadataProvider.getInstance()
				.getDatabaseMetadata(DATABASE).getTable(TABLE);
		TableData tableData = new TableData(tableMetadata, DATABASE);

		Map<Integer, RowData> rows = tableData.getRows();

		assertEquals(1, rows.size());

		RowData rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("Nome").getValue());
		assertEquals(123.0, rowData.getColumn("Codigo").getValue());

		// insere mais um registro:

		insertCommand = createInsertCommand(456, "Batatinha");
		result = insertCommand.execute();

		assertFalse(result.hasError());

		rows = tableData.getRows();

		assertEquals(2, rows.size());

		rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("Nome").getValue());
		assertEquals(123.0, rowData.getColumn("Codigo").getValue());

		rowData = rows.get(1);

		assertEquals("Batatinha", rowData.getColumn("Nome").getValue());
		assertEquals(456.0, rowData.getColumn("Codigo").getValue());

	}

	@Test
	public void testInsertDuplicatedKey() throws Exception {
		createDatabase(DATABASE);
		JsonDB.getInstance().setCurrentDatabase(DATABASE);
		createTable();

		InsertCommand insertCommand = createInsertCommand(123, "Jubileu");
		IResult result = insertCommand.execute();

		assertFalse(result.hasError());

		TableMetadata tableMetadata = DatabaseMetadataProvider.getInstance()
				.getDatabaseMetadata(DATABASE).getTable(TABLE);
		TableData tableData = new TableData(tableMetadata, DATABASE);

		Map<Integer, RowData> rows = tableData.getRows();

		assertEquals(1, rows.size());

		RowData rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("Nome").getValue());
		assertEquals(123.0, rowData.getColumn("Codigo").getValue());

		// insere um registro com a pk duplicada

		insertCommand = createInsertCommand(123, "Batatinha");
		result = insertCommand.execute();

		assertTrue(result.hasError());
		assertEquals("Unique key violation", result.getMessages().get(0));

		rows = tableData.getRows();

		assertEquals(1, rows.size());

		rowData = rows.get(0);

		assertEquals("Jubileu", rowData.getColumn("Nome").getValue());
		assertEquals(123.0, rowData.getColumn("Codigo").getValue());

	}

	private InsertCommand createInsertCommand(int codigo, String nome) {
		Collection<ColumnIdentifier> columns = new ArrayList<ColumnIdentifier>();

		columns.add(new ColumnIdentifier("Codigo"));
		columns.add(new ColumnIdentifier("Nome"));

		Collection<Value<?>> values = new ArrayList<Value<?>>();

		values.add(new NumberValue((long) codigo));
		values.add(new StringValue(nome));

		InsertStatement insertStatement = new InsertStatement(
				new TableIdentifier(TABLE), columns, values);

		return new InsertCommand(insertStatement);
	}

	private void createTable() {
		TableDefinition tableDefinition = new TableDefinition(
				new TableIdentifier(TABLE));

		ColumnDefinition columnCodigo = new ColumnDefinition("Codigo");
		columnCodigo.setColumnType(new ColumnType(DataType.NUMBER));

		ColumnDefinition columnNome = new ColumnDefinition("Nome");
		columnNome.setColumnType(new ColumnType(DataType.VARCHAR));

		tableDefinition.addColumnDefinition(columnCodigo);
		tableDefinition.addColumnDefinition(columnNome);
		tableDefinition.addFinalConstraint(new KeyDefinition("PRIMARY",
				ConstraintKind.PRIMARY_KEY, columnCodigo.getIdentifier()));

		CreateTableCommand command = new CreateTableCommand(
				new CreateStatement(tableDefinition));

		IResult result = command.execute();
		assertFalse(result.hasError());
	}

}
