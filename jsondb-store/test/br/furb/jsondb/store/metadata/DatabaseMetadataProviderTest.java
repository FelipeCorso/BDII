package br.furb.jsondb.store.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.furb.jsondb.parser.DataType;

public class DatabaseMetadataProviderTest {

	@Test
	public void testGetDatabaseMetadata() {

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata("teste");

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

}
