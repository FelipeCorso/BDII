package br.furb.jsondb.core.command;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.furb.jsondb.core.CreateIndexTest;
import br.furb.jsondb.core.CreateTableTest;
import br.furb.jsondb.core.DropIndexTest;
import br.furb.jsondb.core.InsertTest;
import br.furb.jsondb.core.SelectTest;

@RunWith(Suite.class)
@SuiteClasses({ CreateDatabaseCommandTest.class, //
		CreateTableCommandTest.class, //
		SetDatabaseCommandTest.class, // 
		InsertCommandTest.class, //
		CreateTableTest.class,//
		InsertTest.class,//
		SelectTest.class,//
		CreateIndexTest.class,//
		DropIndexTest.class,//
})
public class CoreAllTests {

}
