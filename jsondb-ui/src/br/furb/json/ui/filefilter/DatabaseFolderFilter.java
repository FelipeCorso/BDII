package br.furb.json.ui.filefilter;

import java.io.File;
import java.io.FileFilter;

public class DatabaseFolderFilter implements FileFilter {

	public static final DatabaseFolderFilter INSTANCE = new DatabaseFolderFilter();

	@Override
	public boolean accept(File pathname) {
		if (pathname.isDirectory()) {
			return pathname.listFiles(DatabaseFileFilter.INSTANCE).length > 0;
		}
		return false;
	}
}
