package br.furb.json.ui.filefilter;

import java.io.File;
import java.io.FileFilter;

/**
 * Filtro para aprovar somente os diretórios que contenham um subdiretório
 * aprovado por {@link JsonDBFileFilter}.
 */
public class WorkDirFileFilter implements FileFilter {
	
	public static final WorkDirFileFilter INSTANCE = new WorkDirFileFilter();

	@Override
	public boolean accept(File pathname) {
		return pathname.isDirectory() && pathname.listFiles(JsonDBFileFilter.INSTANCE).length > 0;
	}

}
