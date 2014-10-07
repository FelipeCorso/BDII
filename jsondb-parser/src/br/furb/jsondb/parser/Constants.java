package br.furb.jsondb.parser;

public interface Constants extends ScannerConstants, ParserConstants {

	int EPSILON = 0;
	int DOLLAR = 1;

	int t_id = 2;
	int t_numero = 3;
	int t_literal = 4;
	int t_data = 5;
	int t_TOKEN_6 = 6; //";"
	int t_TOKEN_7 = 7; //"("
	int t_TOKEN_8 = 8; //")"
	int t_TOKEN_9 = 9; //","
	int t_TOKEN_10 = 10; //"*"
	int t_TOKEN_11 = 11; //"."
	int t_TOKEN_12 = 12; //"="
	int t_TOKEN_13 = 13; //">"
	int t_TOKEN_14 = 14; //"<"
	int t_TOKEN_15 = 15; //">="
	int t_TOKEN_16 = 16; //"<="
	int t_TOKEN_17 = 17; //"<>"
	int t_CREATE = 18;
	int t_DATABASE = 19;
	int t_TABLE = 20;
	int t_NUMBER = 21;
	int t_VARCHAR = 22;
	int t_DATE = 23;
	int t_CHAR = 24;
	int t_CONSTRAINT = 25;
	int t_NULL = 26;
	int t_NOT = 27;
	int t_PRIMARY = 28;
	int t_KEY = 29;
	int t_REFERENCES = 30;
	int t_FOREIGN = 31;
	int t_INSERT = 32;
	int t_INTO = 33;
	int t_VALUES = 34;
	int t_SELECT = 35;
	int t_FROM = 36;
	int t_WHERE = 37;
	int t_AND = 38;
	int t_OR = 39;
	int t_DROP = 40;
	int t_DESCRIBE = 41;
	int t_SET = 42;
	int t_INDEX = 43;

}
