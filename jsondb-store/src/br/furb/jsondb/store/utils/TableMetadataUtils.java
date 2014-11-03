package br.furb.jsondb.store.utils;

import java.util.ArrayList;
import java.util.List;

import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.store.metadata.ColumnMetadata;
import br.furb.jsondb.store.metadata.TableMetadata;

public class TableMetadataUtils {

	public static List<String> getNotNullFields(TableMetadata tableMetadata) {
		List<String> fields = new ArrayList<String>();

		for (ColumnMetadata columnMetadata : tableMetadata.getColumns().values()) {
			if (columnMetadata.getConstraint() == ConstraintKind.NOT_NULL) {
				fields.add(columnMetadata.getName());
			}
		}

		return fields;
	}
}
