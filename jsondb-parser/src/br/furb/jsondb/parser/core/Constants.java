package br.furb.jsondb.parser.core;

public interface Constants extends ScannerConstants, ParserConstants {
	int EPSILON = 0;
	int DOLLAR = 1;

	int t_rawId = 2;
	int t_id = 3;
	int t_numero = 4;
	int t_literal = 5;
	int t_data = 6;
	int t_TOKEN_7 = 7; //";"
	int t_TOKEN_8 = 8; //"("
	int t_TOKEN_9 = 9; //")"
	int t_TOKEN_10 = 10; //","
	int t_TOKEN_11 = 11; //"*"
	int t_TOKEN_12 = 12; //"."
	int t_TOKEN_13 = 13; //"="
	int t_TOKEN_14 = 14; //">"
	int t_TOKEN_15 = 15; //"<"
	int t_TOKEN_16 = 16; //">="
	int t_TOKEN_17 = 17; //"<="
	int t_TOKEN_18 = 18; //"<>"
	int t_CREATE = 19;
	int t_DATABASE = 20;
	int t_TABLE = 21;
	int t_NUMBER = 22;
	int t_VARCHAR = 23;
	int t_DATE = 24;
	int t_CHAR = 25;
	int t_CONSTRAINT = 26;
	int t_NULL = 27;
	int t_NOT = 28;
	int t_PRIMARY = 29;
	int t_KEY = 30;
	int t_REFERENCES = 31;
	int t_FOREIGN = 32;
	int t_INSERT = 33;
	int t_INTO = 34;
	int t_VALUES = 35;
	int t_SELECT = 36;
	int t_FROM = 37;
	int t_WHERE = 38;
	int t_AND = 39;
	int t_OR = 40;
	int t_DROP = 41;
	int t_DESCRIBE = 42;
	int t_SET = 43;
	int t_INDEX = 44;

}
