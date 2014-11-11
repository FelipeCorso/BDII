package br.furb.jsondb.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.furb.jsondb.sql.SQLException;

public class CreateTableTest extends BaseJsonDBTest {

	@Test
	public void testCreateTable01() throws Exception {

		JsonDB.getInstance().executeSQL("CREATE TABLE 'tabela' (" +
		/**/"col_1 NUMBER(010, 10) PRIMARY KEY," +
		/**/"col_2 NUMBER(0020)," +
		/**/"col_3 VARCHAR (001) CONSTRAINT ccol_3_nn NOT NULL," +
		/**/"col_4 DATE constraint 'constraint' NULL" +
		/**/");");

	}

	@Test
	public void testCreateTable02() throws Exception {

		JsonDB.getInstance().executeSQL("CREATE TABLE 'tabela2' (" +
		/**/"col_1 NUMBER(5, 2)," +
		/**/"col_2 NUMBER(10)," +
		/**/"col_3 VARCHAR (50) CONSTRAINT ccol_4_nn NOT NULL," +
		/**/"col_4 DATE constraint 'constraint2' NULL," +
		/**/"col_5 CHAR (1) ," +
		/**/"constraint c2 PRIMARY KEY (col_2, col_5)" +
		/**/");");
	}

	@Test
	public void testCreateTable03() throws Exception {

		try {
			JsonDB.getInstance().executeSQL("CREATE TABLE 'tabela3' (" +
			/**/"col_1 NUMBER(010, 10)," +
			/**/"col_2 NUMBER(0020)," +
			/**/"constraint c2 PRIMARY KEY (col_1, col_3)" +
			/**/");");
		} catch (SQLException e) {
			assertEquals("Key column 'col_3' doesn't exist in table", e.getMessage());
		}
	}
	
	@Test
	public void testCreateTable04() throws Exception {

		try{
		JsonDB.getInstance().executeSQL("CREATE TABLE 'tabela4' (" +
		/**/"col_1 NUMBER(010, 10) PRIMARY KEY," +
		/**/"col_2 NUMBER(0020) PRIMARY KEY," +
		/**/"col_3 VARCHAR (001) CONSTRAINT ccol_5_nn NOT NULL," +
		/**/"col_4 DATE constraint 'constraint3' NULL" +
		/**/");");
		}catch(SQLException e){
			assertEquals("Multiple primary key defined", e.getMessage());
		}

	}
	
	@Test
	public void testCreateTable05() throws Exception {

		JsonDB.getInstance().executeSQL("CREATE TABLE 'tabela5' (" +
		/**/"col_1 NUMBER(5, 2)," +
		/**/"col_2 NUMBER(10)," +
		/**/"col_3 VARCHAR (50) NOT NULL," +
		/**/"col_5 CHAR (1) ," +
		/**/"constraint tabela5_pk PRIMARY KEY (col_2, col_5)" +
		/**/");");
	}


}
