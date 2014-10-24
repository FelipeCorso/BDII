package br.furb.jsondb.store.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;
import br.furb.jsondb.store.metadata.FieldType;
import br.furb.jsondb.store.metadata.TableMetadata;

public class DatabaseMetadataProviderTest {

	@Test
	public void testGetDatabaseMetadata() {
		
		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata("teste");
	
		assertEquals(2,databaseMetadata.getTables().size());
		TableMetadata table1 = databaseMetadata.getTable("Table1");
		assertNotNull(table1);
		TableMetadata table2 = databaseMetadata.getTable("Table2");
		assertNotNull(table2);
	
		assertEquals(3,  table1.getFields().size());
		
		assertEquals(FieldType.VARCHAR, table1.getFields().get("Field1").getType());
		assertEquals(FieldType.CHAR, table1.getFields().get("Field2").getType());
		assertEquals(FieldType.NUMBER, table1.getFields().get("Field3").getType());
		
		assertEquals(2, table2.getFields().size());
		assertEquals(FieldType.VARCHAR, table2.getFields().get("Field1").getType());
		assertEquals(FieldType.NUMBER, table2.getFields().get("Field2").getType());
	}
	
}
