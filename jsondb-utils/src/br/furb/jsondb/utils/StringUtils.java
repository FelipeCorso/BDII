package br.furb.jsondb.utils;

public class StringUtils {
	
	public static final String EMPTY_STR = "";

	public static boolean isEmpty(String string) {
		return string == null || string.trim().equals("");
	}

	public static boolean isAlphaNumeric(String codigo) {
		return !isEmpty(codigo) && codigo.matches("([A-Z]|\\d)+");
	}

}
