package br.furb.jsondb.parser.core;

public class Token {

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

	public String toString() {
		return id + " ( " + lexeme + " ) @ " + position;
	}

	public String getClassificacao() {

		switch (this.id) {
		case 0:
			return "<vazio>";
		case 1:
			return "fim de programa";
		case 2:
			return "identificador";
		case 3:
			return "identificador";
		case 4:
			return "numero";
		case 5:
			return "literal";
		case 6:
			return "data";
		case 7:
			return ";";
		case 8:
			return "(";
		case 9:
			return ")";
		case 10:
			return ",";
		case 11:
			return "*";
		case 12:
			return ".";
		case 13:
			return "=";
		case 14:
			return ">";
		case 15:
			return "<";
		case 16:
			return ">=";
		case 17:
			return "<=";
		case 18:
			return "<>";
		case 19:
			return "CREATE";
		case 20:
			return "DATABASE";
		case 21:
			return "TABLE";
		case 22:
			return "NUMBER";
		case 23:
			return "VARCHAR";
		case 24:
			return "DATE";
		case 25:
			return "CHAR";
		case 26:
			return "CONSTRAINT";
		case 27:
			return "NULL";
		case 28:
			return "NOT";
		case 29:
			return "PRIMARY";
		case 30:
			return "KEY";
		case 31:
			return "REFERENCES";
		case 32:
			return "FOREIGN";
		case 33:
			return "INSERT";
		case 34:
			return "INTO";
		case 35:
			return "VALUES";
		case 36:
			return "SELECT";
		case 37:
			return "FROM";
		case 38:
			return "WHERE";
		case 39:
			return "AND";
		case 40:
			return "OR";
		case 41:
			return "DROP";
		case 42:
			return "DESCRIBE";
		case 43:
			return "SET";
		case 44:
			return "INDEX"; 
		default:
			return lexeme;
		}
		
	}
}
