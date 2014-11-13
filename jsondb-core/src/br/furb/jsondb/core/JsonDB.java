package br.furb.jsondb.core;

import java.io.File;

import br.furb.jsondb.core.command.CreateDatabaseCommand;
import br.furb.jsondb.core.command.CreateIndexCommand;
import br.furb.jsondb.core.command.CreateTableCommand;
import br.furb.jsondb.core.command.DropIndexCommand;
import br.furb.jsondb.core.command.DropTableCommand;
import br.furb.jsondb.core.command.InsertCommand;
import br.furb.jsondb.core.command.SelectCommand;
import br.furb.jsondb.core.command.SetDatabaseCommand;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.parser.Index;
import br.furb.jsondb.parser.SQLParser;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.parser.TableDefinition;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.parser.statement.DropStatement;
import br.furb.jsondb.parser.statement.IStatement;
import br.furb.jsondb.parser.statement.InsertStatement;
import br.furb.jsondb.parser.statement.SelectStatement;
import br.furb.jsondb.parser.statement.SetDatabaseStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBProperty;
import br.furb.jsondb.store.JsonDBStore;

public class JsonDB {

	private static final JsonDB INSTANCE = new JsonDB();

	/**
	 * Nome da pasta do JsonDB, que deve estar dentro da pasta de trabalho do
	 * banco.
	 * <p>
	 * Atualmente é {@code ".jsondb"}
	 * </p>
	 */
	public static final String JSONDB_FOLDER_NAME = JsonDBStore.JSONDB_FOLDER_NAME;

	private String currentDatabase;

	private JsonDB() {
	}

	public static JsonDB getInstance() {
		return INSTANCE;
	}

	public static void setWorkingDir(File workingDir) {
		JsonDBProperty.JSON_DB_DIR.set(workingDir.getAbsolutePath());
		JsonDBStore.reset();
		JsonDBStore.getInstance().getJsonDBDir();
	}

	public String getCurrentDatabase() {
		return currentDatabase;
	}

	public void setCurrentDatabase(String currentDatabase) {
		this.currentDatabase = currentDatabase;
	}

	@SuppressWarnings("unchecked")
	public static IResult executeSQL(String sql) throws SQLParserException, SQLException {
		//		Collection<RawStatement> rawStatements = SQLParser.extractCommands(sql);
		IStatement statement = SQLParser.parse(sql);

		if (statement instanceof CreateStatement) {

			CreateStatement createStatement = (CreateStatement) statement;

			/* CREATE TABLE */
			if (createStatement.getStructure() instanceof TableDefinition) {
				return createTable(createStatement);
			}

			/* CREATE DATABASE */
			if (createStatement.getStructure() instanceof DatabaseIdentifier) {
				return createDatabase(createStatement);
			}

			/* CREATE INDEX */
			if (createStatement.getStructure() instanceof Index) {
				return createIndex(createStatement);
			}

			assert false;
		}

		/* INSERT */
		if (statement instanceof InsertStatement) {
			return insert((InsertStatement) statement);
		}

		/* SET DATABASE*/
		if (statement instanceof SetDatabaseStatement) {
			return setDatabase((SetDatabaseStatement) statement);
		}

		/**/
		if (statement instanceof DropStatement) {
			DropStatement<?> dropStatement = (DropStatement<?>) statement;

			/* DROP TABLE */
			if (dropStatement.getStructure() instanceof TableIdentifier) {
				return dropTable((DropStatement<TableIdentifier>) statement);
			}
			/* DROP INDEX */
			return dropIndex((DropStatement<Index>) statement);
		}

		/* SELECT */
		if (statement instanceof SelectStatement) {
			return select((SelectStatement) statement);
		}

		// TODO

		return null;
	}

	private static IResult select(SelectStatement statement) throws SQLException {
		return new SelectCommand(statement).execute();
	}

	private static IResult dropIndex(DropStatement<Index> statement) throws SQLException {
		return new DropIndexCommand(statement).execute();
	}

	private static IResult createIndex(CreateStatement createStatement) throws SQLException {
		return new CreateIndexCommand(createStatement).execute();
	}

	private static IResult createDatabase(CreateStatement statement) throws SQLException {
		return new CreateDatabaseCommand(statement).execute();
	}

	private static IResult setDatabase(SetDatabaseStatement statement) throws SQLException {
		return new SetDatabaseCommand(statement).execute();
	}

	private static IResult createTable(CreateStatement statement) throws SQLException {
		return new CreateTableCommand(statement).execute();
	}

	private static IResult dropTable(DropStatement<TableIdentifier> statement) throws SQLException {
		return new DropTableCommand(statement).execute();
	}

	private static IResult insert(InsertStatement statement) throws SQLException {
		return new InsertCommand(statement).execute();
	}

}
