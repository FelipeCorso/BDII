package br.furb.jsondb.core.command;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.SQLException;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.IResultSet;
import br.furb.jsondb.core.result.ResultRow;
import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.ColumnType;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.DataType;
import br.furb.jsondb.parser.KeyDefinition;
import br.furb.jsondb.parser.NumberValue;
import br.furb.jsondb.parser.StringValue;
import br.furb.jsondb.parser.TableDefinition;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.Value;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.parser.statement.InsertStatement;
import br.furb.jsondb.parser.statement.SelectStatement;

public class SelectCommandTest extends BaseCommandTest {

	private static final String TABLE = "PESSOA";
	private static final String DATABASE = "teste";

	/**
	 * Select com colunas e apenas uma tabela:
	 * 
	 * select * from PESSOA
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSelect01() throws Exception {
		insertData();

		//

		SelectStatement selectStatement = new SelectStatement();

		selectStatement.setColumns(Arrays.asList(new ColumnIdentifier("Codigo"), new ColumnIdentifier("Nome")));
		selectStatement.setTables(Arrays.asList(new TableIdentifier("PESSOA")));

		SelectCommand selectCommand = new SelectCommand(selectStatement);

		IResult result = selectCommand.execute();

		IResultSet resultSet = (IResultSet) result;

		List<ResultRow> rows = resultSet.getRows();
		assertEquals(2, rows.size());

		ResultRow rowData = rows.get(0);

		assertEquals(123L, rowData.getColumnValue("Codigo"));
		assertEquals("Jubileu", rowData.getColumnValue("Nome"));

	}

	/**
	 * Select sem colunas e apenas uma tabela:
	 * 
	 * select codigo, nome from pessoa
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSelect02() throws Exception {
		insertData();

		//

		SelectStatement selectStatement = new SelectStatement();

		selectStatement.setTables(Arrays.asList(new TableIdentifier("PESSOA")));

		SelectCommand selectCommand = new SelectCommand(selectStatement);

		IResult result = selectCommand.execute();

		IResultSet resultSet = (IResultSet) result;

		assertEquals(2, resultSet.getRows().size());
	}

	private void insertData() throws SQLException {
		createDatabase(DATABASE);
		JsonDB.getInstance().setCurrentDatabase(DATABASE);
		createTable();

		InsertCommand insertCommand = createInsertCommand(123, "Jubileu");
		IResult result = insertCommand.execute();

		insertCommand = createInsertCommand(456, "Batatinha");
		result = insertCommand.execute();

	}

	private InsertCommand createInsertCommand(int codigo, String nome) {
		Collection<ColumnIdentifier> columns = new ArrayList<ColumnIdentifier>();

		columns.add(new ColumnIdentifier("Codigo"));
		columns.add(new ColumnIdentifier("Nome"));

		Collection<Value<?>> values = new ArrayList<Value<?>>();

		values.add(new NumberValue((long) codigo));
		values.add(new StringValue(nome));

		InsertStatement insertStatement = new InsertStatement(new TableIdentifier(TABLE), columns, values);

		return new InsertCommand(insertStatement);
	}

	private void createTable() throws SQLException {
		TableDefinition tableDefinition = new TableDefinition(new TableIdentifier(TABLE));

		ColumnDefinition columnCodigo = new ColumnDefinition("Codigo");
		columnCodigo.setColumnType(new ColumnType(DataType.NUMBER));

		ColumnDefinition columnNome = new ColumnDefinition("Nome");
		columnNome.setColumnType(new ColumnType(DataType.VARCHAR));

		tableDefinition.addColumnDefinition(columnCodigo);
		tableDefinition.addColumnDefinition(columnNome);
		tableDefinition.addFinalConstraint(new KeyDefinition("PRIMARY", ConstraintKind.PRIMARY_KEY, columnCodigo.getIdentifier()));

		CreateTableCommand command = new CreateTableCommand(new CreateStatement(tableDefinition));

		IResult result = command.execute();
	}

}
