package br.furb.jsondb.parser.core;

public class Token {

	private static String[] TOKEN_NAMES = { "<vazio>", "fim de programa", "id", "numero", "literal", "data", ";", "(", ")", ",", "*", ".", "=", ">", "<", ">=", "<=", "<>",
			"CREATE", "DATABASE", "TABLE", "NUMBER", "VARCHAR", "DATE", "CHAR", "CONSTRAINT", "NULL", "NOT", "PRIMARY", "KEY", "REFERENCES", "FOREIGN", "INSERT", "INTO", "VALUES",
			"SELECT", "FROM", "WHERE", "AND", "OR", "DROP", "DESCRIBE", "SET", "INDEX" };

	private int id;
	private String lexeme;
	private int position;
	private boolean fimProg;

	public Token(int id, String lexeme, int position) {
		this.id = id;
		this.lexeme = lexeme;
		this.position = position;
	}

	public Token(int id, String lexeme, int position, boolean fimProg) {
		this.id = id;
		this.lexeme = lexeme;
		this.position = position;
		this.fimProg = fimProg;
	}

	public final int getId() {
		return id;
	}

	public final String getLexeme() {
		return lexeme;
	}

	public final int getPosition() {
		return position;
	}

	public boolean isFimProg() {
		return fimProg;
	}

	@Override
	public String toString() {
		return id + " ( " + lexeme + " ) @ " + position;
	}

	public String getClassificacao() {
		if (this.id >= 0 && this.id < TOKEN_NAMES.length) {
			return TOKEN_NAMES[this.id];
		}
		return lexeme;
	}
}
