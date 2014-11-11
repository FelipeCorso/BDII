package br.furb.jsondb.store.utils;

import br.furb.jsondb.parser.ConstraintDefinition;
import br.furb.jsondb.store.metadata.DatabaseMetadata;

public class ConstraintNameGenerator {

	public static String generateConstraintName(DatabaseMetadata databaseMetadata, String table, ConstraintDefinition constraintDefinition) {
		return generateConstraintName(databaseMetadata, table, constraintDefinition, null);

	}

	public static String generateConstraintName(DatabaseMetadata databaseMetadata, String table, ConstraintDefinition constraintDefinition, String column) {

		StringBuilder sb = new StringBuilder();

		sb.append(constraintDefinition.getKind().getShortName())
		/**/.append('_').append(table).append('_');
		if (column != null) {
			sb.append(column);
			sb.append('_');
		}

		int id = 0;
		while (databaseMetadata.getConstraint(sb.toString() + id) != null) {
			id++;
		}

		sb.append(id);

		return sb.toString();

	}
}
