package br.furb.jsondb.core.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnType;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.DataType;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.parser.KeyDefinition;
import br.furb.jsondb.parser.TableDefinition;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.metadata.ColumnMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.TableMetadata;

public class CreateTableCommandTest extends BaseCommandTest {

	@Test
	public void testCreatTable01() {
		createDatabase("baseTeste");
		JsonDB.getInstance().setCurrentDatabase("baseTeste");

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

		IResult result = command.execute();

		//validações

		assertFalse(result.hasError());

		File databaseDir = JsonDBStore.getInstance().getDatabaseDir("baseTeste");

		File tableDir = new File(databaseDir, "Pessoa");

		assertTrue(tableDir.exists());

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata("baseTeste");
		TableMetadata tableMetadata = databaseMetadata.getTable("Pessoa");

		Map<String, ColumnMetadata> columns = tableMetadata.getColumns();

		assertEquals(3, columns.size());

	}



}
