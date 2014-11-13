package br.furb.json.ui.filefilter;

import java.io.File;
import java.io.FileFilter;

import br.furb.jsondb.store.metadata.DatabaseMetadataProvider;

public class DatabaseFileFilter implements FileFilter {

	public static final DatabaseFileFilter INSTANCE = new DatabaseFileFilter();

	@Override
	public boolean accept(File pathname) {
		return pathname.isFile() && pathname.getName().equalsIgnoreCase(DatabaseMetadataProvider.DATABASE_FILE_NAME);
	}
}