package br.furb.jsondb.store.metadata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import br.furb.jsondb.store.utils.JsonUtils;

public class DatabaseMetadataIO {

	public static DatabaseMetadata read(String path) throws IOException {
		FileInputStream in = new FileInputStream(new File(path));
		try {
			return read(in);
		} finally {
			in.close();
		}
	}

	public static DatabaseMetadata read(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return read(reader);
	}

	public static DatabaseMetadata read(Reader reader) throws IOException {
		DatabaseMetadata databaseMetadata;

		try {
			databaseMetadata = JsonUtils.parseJsonToObject(reader,
					DatabaseMetadata.class);
		} finally {
			reader.close();
		}
		return databaseMetadata;
	}

	public static void write(DatabaseMetadata metadata, String filePath)
			throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				filePath)));
		try {
			write(metadata, writer);
			writer.flush();
		} finally {

		}
	}

	public static void write(DatabaseMetadata metadata, Writer writer) {
		JsonUtils.parseObjectToJson(writer, metadata, DatabaseMetadata.class);
	}

	public static void appendTableMetadata(TableMetadata tableMetadata, File metadataFile)
			throws IOException {

		RandomAccessFile raf;
		raf = new RandomAccessFile(metadataFile, "rw");

		StringWriter stringWriter = new StringWriter();
		JsonUtils.parseObjectToJson(stringWriter, tableMetadata,
				TableMetadata.class);

		// Aponta para o final do mapa de tabelas
		raf.seek(metadataFile.length() - 2);
		// Coloca a tabela no final do mapa de tabelas 
		raf.writeBytes(",\"" + tableMetadata.getName() + "\":"
				+ stringWriter.toString() + "}}");
		raf.close();
	}
}
