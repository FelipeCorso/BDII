package br.furb.jsondb.store.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

import br.furb.jsondb.parser.DataType;

public class DatabaseMetadataJsonParserTest {

	@Test
	public void testParseToJson() {

		DatabaseMetadata metadata = new DatabaseMetadata();
		
		TableMetadata table = new TableMetadata();

		table.setName("Table1");
		table.addField(new FieldMetadata("Field1", DataType.VARCHAR, 20,0));
		table.addField(new FieldMetadata("Field2", DataType.CHAR, 1));
		table.addField(new FieldMetadata("Field3", DataType.NUMBER, 2));

		metadata.addTable(table);

		table = new TableMetadata();

		table.setName("Table2");
		table.addField(new FieldMetadata("Field1", DataType.VARCHAR, 20));
		table.addField(new FieldMetadata("Field2", DataType.NUMBER, 2));

		metadata.addTable(table);

		StringWriter writer = new StringWriter();
		DatabaseMetadataIO.write(metadata, writer);

		// TODO validar
		System.out.print(writer.toString());
	}

	@Test
	public void testParseToObject() throws IOException {
		
		DatabaseMetadata databaseMetadata = DatabaseMetadataIO.read(this.getClass().getResourceAsStream("database_1.metadata"));
		
		assertEquals(2,databaseMetadata.getTables().size());
		TableMetadata table1 = databaseMetadata.getTable("Table1");
		assertNotNull(table1);
		TableMetadata table2 = databaseMetadata.getTable("Table2");
		assertNotNull(table2);
	
		assertEquals(3,  table1.getFields().size());
		
		assertEquals(DataType.VARCHAR, table1.getFields().get("Field1").getType());
		assertEquals(DataType.CHAR, table1.getFields().get("Field2").getType());
		assertEquals(DataType.NUMBER, table1.getFields().get("Field3").getType());
		
		assertEquals(2, table2.getFields().size());
		assertEquals(DataType.VARCHAR, table2.getFields().get("Field1").getType());
		assertEquals(DataType.NUMBER, table2.getFields().get("Field2").getType());
	}
	@Test
	public void testParseToObject02() throws IOException {
		
		DatabaseMetadata databaseMetadata = DatabaseMetadataIO.read(this.getClass().getResourceAsStream("Teste.metadata"));
		
		assertEquals(3,databaseMetadata.getTables().size());
	
	}
}
