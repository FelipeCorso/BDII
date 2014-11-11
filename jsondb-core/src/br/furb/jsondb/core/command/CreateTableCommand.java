package br.furb.jsondb.core.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import br.furb.jsondb.core.JsonDB;
import br.furb.jsondb.core.result.IResult;
import br.furb.jsondb.core.result.Result;
import br.furb.jsondb.core.util.JsonDBUtils;
import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.ConstraintDefinition;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.KeyDefinition;
import br.furb.jsondb.parser.TableDefinition;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.sql.SQLException;
import br.furb.jsondb.store.JsonDBStore;
import br.furb.jsondb.store.StoreException;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

public class CreateTableCommand implements ICommand {

	private CreateStatement statement;

	public CreateTableCommand(CreateStatement statement) {
		this.statement = statement;
	}

	@Override
	public IResult execute() throws SQLException {
		IResult result = null;

		String database = JsonDB.getInstance().getCurrentDatabase();

		JsonDBUtils.validateHasCurrentDatabase();

		DatabaseMetadata databaseMetadata = DatabaseMetadataProvider.getInstance().getDatabaseMetadata(database);

		String tableName = statement.getStructure().getIdentifier();

		// valida se a tabela j� existe
		if (databaseMetadata.getTable(tableName) != null) {
			throw new SQLException(String.format("Table %s already exists in database %s", tableName, database));
		}

		try {
			JsonDBStore.getInstance().createTable(database, statement, getPrimaryKeyFields((TableDefinition) statement.getStructure()));
			result = new Result(String.format("Table %s created with success", tableName));
		} catch (StoreException e) {
			throw new SQLException("Was not possible to create table.", e);
		}

		return result;
	}

	private static List<String> getPrimaryKeyFields(TableDefinition tableDefinition) throws StoreException, SQLException {
		List<String> pk = new ArrayList<String>();

		List<ColumnDefinition> columns = tableDefinition.getColumns();

		for (ColumnDefinition columnDefinition : columns) {

			Optional<ConstraintDefinition> constraint = columnDefinition.getConstraint();
			if (constraint.isPresent()) {
				if (constraint.get().getKind() == ConstraintKind.PRIMARY_KEY) {

					if (pk.size() > 0) {
						throw new SQLException("Multiple primary key defined");
					}

					pk.add(columnDefinition.getName());
				}
			}

		}

		for (ConstraintDefinition constraintDefinition : tableDefinition.getFinalConstraints()) {
			if (constraintDefinition.getKind() == ConstraintKind.PRIMARY_KEY) {

				if (pk.size() > 0) {
					throw new SQLException("Multiple primary key defined");
				}

				KeyDefinition keyDefinition = (KeyDefinition) constraintDefinition;

				for (ColumnIdentifier column : keyDefinition.getColumns()) {

					boolean foundColumn = CollectionUtils.exists(tableDefinition.getColumns(), new Predicate<ColumnDefinition>() {

						@Override
						public boolean evaluate(ColumnDefinition columnDefinition) {
							return columnDefinition.getName().equals(column.getColumnName());
						}
					});

					if (!foundColumn) {
						throw new SQLException(String.format("Key column '%s' doesn't exist in table", column.getColumnName()));
					}

					pk.add(column.getColumnName());
				}
			}
		}
		return pk;
	}

}
