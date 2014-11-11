package br.furb.jsondb.store;

public enum JsonDBProperty {

	JSON_DB_DIR("br.furb.jsondb.dir", System.getProperty("user.home"));

	private String property;
	private String defaultValue;

	JsonDBProperty(String property, String defaultValue) {
		this.property = property;
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getProperty() {
		return property;
	}

	public String get() {
		String value = System.getProperty(property);

		if (value != null) {
			return value;
		}

		return defaultValue;
	}

	public String set(String value) {
		if (value == null) {
			System.clearProperty(property);
			return null;
		}
		return System.setProperty(property, value);
	}
}
