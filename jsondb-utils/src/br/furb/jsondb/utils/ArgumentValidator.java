package br.furb.jsondb.utils;

public final class ArgumentValidator {

	/**
	 * Valida que a string informada não é nula e nem vazia, lançando uma
	 * {@code IllegalArgumentException} sem mensagem caso seja.
	 * 
	 * @param string
	 *            string a ser validada
	 * @return a string original
	 */
	public static String requireNonEmpty(String string) {
		return requireNonEmpty(string, null);
	}

	/**
	 * Valida que a string informada não é nula e nem vazia, lançando uma
	 * {@code IllegalArgumentException} com a mensagem informada caso seja.
	 * 
	 * @param string
	 *            string a ser validada
	 * @param failMessage
	 *            mensagem da exceção
	 * @return a string original
	 */
	public static String requireNonEmpty(String string, String failMessage) {
		if (string == null || "".equals(string.trim())) {
			throw new IllegalArgumentException(failMessage);
		}
		return string;
	}

}
