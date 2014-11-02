package br.furb.jsondb.store.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

import br.furb.jsondb.parser.DataType;
import br.furb.jsondb.store.utils.JsonUtils;

public class DatabaseMetadataJsonParserTest {

	@Test
	public void testParseToJson() {

		DatabaseMetadata metadata = new DatabaseMetadata();

		TableMetadata table = new TableMetadata();

		table.setName("Table1");
		table.addColumn(new ColumnMetadata("Field1", DataType.VARCHAR, 20, 0));
		table.addColumn(new ColumnMetadata("Field2", DataType.CHAR, 1));
		table.addColumn(new ColumnMetadata("Field3", DataType.NUMBER, 2));

		metadata.addTable(table);

		table = new TableMetadata();

		table.setName("Table2");
		table.addColumn(new ColumnMetadata("Field1", DataType.VARCHAR, 20));
		table.addColumn(new ColumnMetadata("Field2", DataType.NUMBER, 2));

		metadata.addTable(table);

		// TODO validar
	}

	@Test
	public void testParseToObject() throws IOException {

		DatabaseMetadata databaseMetadata = JsonUtils.parseJsonToObject(this.getClass().getResourceAsStream("database_1.metadata"), DatabaseMetadata.class);

		assertEquals(2, databaseMetadata.getTables().size());
		TableMetadata table1 = databaseMetadata.getTable("Table1");
		assertNotNull(table1);
		TableMetadata table2 = databaseMetadata.getTable("Table2");
		assertNotNull(table2);

		assertEquals(3, table1.getColumns().size());

		assertEquals(DataType.VARCHAR, table1.getColumns().get("Field1").getType());
		assertEquals(DataType.CHAR, table1.getColumns().get("Field2").getType());
		assertEquals(DataType.NUMBER, table1.getColumns().get("Field3").getType());

		assertEquals(2, table2.getColumns().size());
		assertEquals(DataType.VARCHAR, table2.getColumns().get("Field1").getType());
		assertEquals(DataType.NUMBER, table2.getColumns().get("Field2").getType());
	}

	@Test
	public void testParseToObject02() throws IOException {

		DatabaseMetadata databaseMetadata = JsonUtils.parseJsonToObject(this.getClass().getResourceAsStream("Teste.metadata"), DatabaseMetadata.class);

		assertEquals(3, databaseMetadata.getTables().size());

	}
}
