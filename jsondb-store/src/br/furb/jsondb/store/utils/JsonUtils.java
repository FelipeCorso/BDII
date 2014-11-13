package br.furb.jsondb.store.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public final class JsonUtils {

	/**
	 * Tamanho padr�o para identa��o de json
	 */
	//	private static final String DEFAULT_INDENT = "    ";
	private static final String DEFAULT_INDENT = "";

	private JsonUtils() {
	}

	public static JsonElement parseElement(Reader reader) throws JsonIOException, JsonSyntaxException, IOException {
		try (JsonReader jsonReader = new JsonReader(reader)) {
			jsonReader.setLenient(true);
			JsonElement ret = new JsonParser().parse(jsonReader);
			return ret;
		}
	}

	public static <T> T parseJsonToObject(Reader reader, Class<T> type) throws IOException {
		try (JsonReader jsonReader = new JsonReader(reader)) {
			jsonReader.setLenient(true);
			Gson gson = new GsonBuilder().create();
			return gson.fromJson(jsonReader, type);
		}
	}

	public static <T> T parseJsonToObject(InputStream is, Class<T> type) throws IOException {
		return parseJsonToObject(new InputStreamReader(is), type);
	}

	public static <T> T parseJsonToObject(File file, Class<T> type) throws IOException {
		try (FileReader reader = new FileReader(file)) {
			T object = parseJsonToObject(reader, type);
			return object;
		}
	}

	public static <T> void parseObjectToJson(Writer writer, T objectToJson, Class<T> type) throws IOException {
		parseObjectToJson(writer, objectToJson, type, DEFAULT_INDENT);
	}

	public static <T> void parseObjectToJson(Writer writer, T objectToJson, Class<T> type, String indent) throws IOException {
		try (JsonWriter jsonWriter = new JsonWriter(writer)) {
			jsonWriter.setIndent(indent);
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.enableComplexMapKeySerialization();
			Gson gson = gsonBuilder.disableHtmlEscaping().create();
			gson.toJson(objectToJson, type, jsonWriter);
		}
	}

	public static JsonElement parseElement(String input) throws JsonIOException, JsonSyntaxException {
		try {
			return parseElement(new StringReader(input));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static JsonObject parse(Reader reader) throws JsonIOException, JsonSyntaxException, IOException {
		JsonElement ret = parseElement(reader);
		if (!ret.isJsonObject()) {
			throw new JsonSyntaxException("Invalid json object");
		}
		return ret.getAsJsonObject();

	}

	public static JsonObject parse(InputStream stream) throws JsonIOException, JsonSyntaxException, IOException {
		return parse(new InputStreamReader(stream));
	}

	public static JsonObject parse(String json) throws JsonIOException, JsonSyntaxException {
		try {
			return parse(new StringReader(json));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static JsonObject parse(File file) throws JsonIOException, JsonSyntaxException {
		try (FileInputStream stream = new FileInputStream(file)) {
			return parse(stream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> void write(T object, Class<T> type, File file) throws IOException {
		try (FileWriter fileWriter = new FileWriter(file)) {
			parseObjectToJson(fileWriter, object, type);
		}
	}

}
