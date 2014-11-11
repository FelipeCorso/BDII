package br.furb.jsondb.core.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnType;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.DataType;
import br.furb.jsondb.parser.KeyDefinition;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.parser.TableDefinition;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.metadata.ColumnMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.TableMetadata;

public class CreateTableCommandTest extends BaseCommandTest {

	@Test
	public void testCreatTable01() throws SQLException {
		createDatabase("baseteste");
		JsonDB.getInstance().setCurrentDatabase("baseteste");

		TableDefinition tableDefinition = new TableDefinition(new TableIdentifier("Pessoa"));
		ColumnDefinition cpfCol = new ColumnDefinition("Cpf");
		cpfCol.setColumnType(new ColumnType(DataType.NUMBER));

		ColumnDefinition nameCol = new ColumnDefinition("Nome");
		nameCol.setColumnType(new ColumnType(DataType.VARCHAR));

		ColumnDefinition dataNascCol = new ColumnDefinition("DataNascimento");
		dataNascCol.setColumnType(new ColumnType(DataType.DATE));

		tableDefinition.addColumnDefinition(cpfCol);
		tableDefinition.addColumnDefinition(nameCol);
		tableDefinition.addColumnDefinition(dataNascCol);

		KeyDefinition constraint = new KeyDefinition("PESSOA_PK", ConstraintKind.PRIMARY_KEY);
		constraint.addColumn(cpfCol.getIdentifier());
		tableDefinition.addFinalConstraint(constraint);

		CreateTableCommand command = new CreateTableCommand(new CreateStatement(tableDefinition));

		command.execute();

		//validações
		File databaseDir = JsonDBStore.getInstance().getDatabaseDir("baseTeste");

		File tableDir = new File(databaseDir, "Pessoa");

		assertTrue(tableDir.exists());

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata("baseTeste");
		TableMetadata tableMetadata = databaseMetadata.getTable("Pessoa");

		Map<String, ColumnMetadata> columns = tableMetadata.getColumns();

		assertEquals(3, columns.size());

	}

	@Test
	public void testCreateTable02() throws SQLParserException, SQLException {
		createDatabase("baseteste");
		JsonDB.getInstance().executeSQL("SET DATABASE baseTeste;");
		JsonDB.getInstance().executeSQL("CREATE TABLE Pessoa(Codigo NUMBER(3) PRIMARY KEY, nome VARCHAR(45));");

		File databaseDir = JsonDBStore.getInstance().getDatabaseDir("baseTeste");

		File tableDir = new File(databaseDir, "pessoa");

		assertTrue(tableDir.exists());

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata("baseteste");
		TableMetadata tableMetadata = databaseMetadata.getTable("pessoa");

		Map<String, ColumnMetadata> columns = tableMetadata.getColumns();

		assertEquals(2, columns.size());
	}

}
