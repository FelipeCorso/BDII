package br.furb.jsondb.parser.core;

public class Token {

	// TODO: atualizar
	// Extraído com regex da classe Constants
	private static String[] TOKEN_NAMES = { "<vazio>", "fim de programa", "identificador", "numero", "literal", "data", ";", "(", ")", ",", "*", ".", "=", ">", "<", ">=", "<=",
			"<>", "CREATE", "DATABASE", "TABLE", "NUMBER", "VARCHAR", "DATE", "CHAR", "CONSTRA= 25;", "NULL", "NOT", "PRIMARY", "KEY", "REFERENCES", "FOREIGN", "INSERT", "INTO",
			"VALUES", "SELECT", "FROM", "WHERE", "AND", "OR", "ON", "DROP", "DESCRIBE", "SET", "INDEX" };

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
		return getClassificacao() + " @ " + position;
	}

	public String getClassificacao() {
		if (this.id >= 0 && this.id < TOKEN_NAMES.length) {
			if (this.id > 1 && this.id < 6) {
				return TOKEN_NAMES[this.id].concat("(").concat(lexeme).concat(")");
			}
			return TOKEN_NAMES[this.id];
		}
		return lexeme;
	}
}
