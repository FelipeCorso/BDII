package br.furb.jsondb.store.utils;

import java.io.File;
import java.io.FileInputStream;
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
	 * Tamanho padrão para identação de json
	 */
//	private static final String DEFAULT_INDENT = "    ";
	private static final String DEFAULT_INDENT = "";

	private JsonUtils() {
	}

	public static JsonElement parseElement(Reader reader)
			throws JsonIOException, JsonSyntaxException {
		JsonReader jsonReader = new JsonReader(reader);
		jsonReader.setLenient(true);
		JsonElement ret = new JsonParser().parse(jsonReader);
		return ret;
	}

	public static <T> T parseJsonToObject(Reader reader, Class<T> type) {
		JsonReader jsonReader = new JsonReader(reader);
		jsonReader.setLenient(true);
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonReader, type);
	}

	public static <T> void parseObjectToJson(Writer writer, T objectToJson,
			Class<T> type) {
		parseObjectToJson(writer, objectToJson, type, DEFAULT_INDENT);
	}

	public static <T> void parseObjectToJson(Writer writer, T objectToJson,
			Class<T> type, String indent) {
		JsonWriter jsonWriter = new JsonWriter(writer);
		jsonWriter.setIndent(indent);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		gson.toJson(objectToJson, type, jsonWriter);
	}

	public static JsonElement parseElement(String input)
			throws JsonIOException, JsonSyntaxException {
		return parseElement(new StringReader(input));
	}

	public static JsonObject parse(Reader reader) throws JsonIOException,
			JsonSyntaxException {
		JsonElement ret = parseElement(reader);
		if (!ret.isJsonObject()) {
			throw new JsonSyntaxException("Invalid json object");
		}
		return ret.getAsJsonObject();

	}

	public static JsonObject parse(InputStream stream) throws JsonIOException,
			JsonSyntaxException {
		return parse(new InputStreamReader(stream));
	}

	public static JsonObject parse(String json) throws JsonIOException,
			JsonSyntaxException {
		return parse(new StringReader(json));
	}

	public static JsonObject parse(File file) throws JsonIOException,
			JsonSyntaxException {
		try {
			FileInputStream stream = new FileInputStream(file);
			try {
				return parse(stream);
			} finally {
				stream.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
