package br.furb.jsondb.store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Test;

public class TableDataReadTest {

	@SuppressWarnings("resource")
	@Test
	public void testGetRowData() throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("")));

		bufferedReader.skip(1);

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("")));

		RandomAccessFile randomAccessFile = new RandomAccessFile(new File(""), "rw");
		randomAccessFile.length();
	}

}
