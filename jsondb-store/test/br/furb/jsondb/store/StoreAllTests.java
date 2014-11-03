package br.furb.jsondb.store;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.furb.jsondb.store.metadata.DatabaseMetadataJsonParserTest;
import br.furb.jsondb.store.metadata.DatabaseMetadataProviderTest;

@RunWith(Suite.class)
@SuiteClasses({ TableDataReadTest.class, //
	DatabaseMetadataJsonParserTest.class, //
	DatabaseMetadataProviderTest.class, //
	})
public class StoreAllTests {

}
