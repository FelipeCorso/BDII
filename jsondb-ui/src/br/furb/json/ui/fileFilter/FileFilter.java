package br.furb.json.ui.fileFilter;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileNameExtensionFilter;

public class FileFilter implements FilenameFilter {

	private static final String EXT_METADATA = ".METADATA";

	@Override
	public boolean accept(File dir, String name) {
		return name.toUpperCase().endsWith(EXT_METADATA);
	}

	public static FileNameExtensionFilter getFileFilter() {
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Pasta", "folder");
		return fileFilter;
	}

}
