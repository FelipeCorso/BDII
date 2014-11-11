package br.furb.jsondb.store.utils;

import java.util.ArrayList;
import java.util.List;

import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.store.metadata.ColumnMetadata;
import br.furb.jsondb.store.metadata.ConstraintMetadata;
import br.furb.jsondb.store.metadata.DatabaseMetadata;
import br.furb.jsondb.store.metadata.TableMetadata;

public class TableMetadataUtils {

	public static List<String> getNotNullFields(TableMetadata tableMetadata, DatabaseMetadata databaseMetadata) {
		List<String> fields = new ArrayList<String>();

		for (ColumnMetadata columnMetadata : tableMetadata.getColumns().values()) {
			String constraint = columnMetadata.getConstraint();
			if (constraint != null) {

				ConstraintMetadata constraintMetadata = databaseMetadata.getConstraint(constraint);
				assert constraintMetadata != null : "Constraint not found: " + constraint;

				if (constraintMetadata.getKind() == ConstraintKind.NOT_NULL) {
					fields.add(columnMetadata.getName());
				}
			}
		}

		return fields;
	}
}
