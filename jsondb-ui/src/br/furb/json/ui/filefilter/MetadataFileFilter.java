package br.furb.json.ui.filefilter;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileNameExtensionFilter;

public class MetadataFileFilter implements FilenameFilter {

	public static final FileNameExtensionFilter FILE_FILTER = new FileNameExtensionFilter("Pasta", "folder");

	private static final String EXT_METADATA = ".METADATA";

	@Override
	public boolean accept(File dir, String name) {
		return dir.isDirectory() && name.toUpperCase().endsWith(EXT_METADATA);
	}

}
