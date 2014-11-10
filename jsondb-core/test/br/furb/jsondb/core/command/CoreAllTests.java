package br.furb.jsondb.core.command;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CreateDatabaseCommandTest.class, //
		CreateTableCommandTest.class, //
		SetDatabaseCommandTest.class, // 
		InsertCommandTest.class//
})
public class CoreAllTests {

}
