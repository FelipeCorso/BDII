package br.furb.json.ui.filefilter;

import java.io.File;
import java.io.FileFilter;

import br.furb.jsondb.store.JsonDBStore;

/**
 * Filtro para exibir somente a pasta que tenha o nome
 * {@link JsonDBStore#JSONDB_FOLDER_NAME JSONDB_FOLDER_NAME} ({@code ".jsondb"}).
 * 
 */
public class JsonDBFileFilter implements FileFilter {

	public static final JsonDBFileFilter INSTANCE = new JsonDBFileFilter();

	@Override
	public boolean accept(File pathname) {
		return pathname.isDirectory() && pathname.getName().equals(JsonDBStore.JSONDB_FOLDER_NAME);
	}
}