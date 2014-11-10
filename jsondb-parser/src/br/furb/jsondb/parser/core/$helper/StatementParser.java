package br.furb.jsondb.parser.core.$helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.ColumnType;
import br.furb.jsondb.parser.ConstraintDefinition;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.DataType;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.parser.ForeignKeyDefinition;
import br.furb.jsondb.parser.IStructure;
import br.furb.jsondb.parser.Index;
import br.furb.jsondb.parser.KeyDefinition;
import br.furb.jsondb.parser.NumberValue;
import br.furb.jsondb.parser.SQLParserException;
import br.furb.jsondb.parser.StringValue;
import br.furb.jsondb.parser.TableDefinition;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.Value;
import br.furb.jsondb.parser.core.Token;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.parser.statement.DropStatement;
import br.furb.jsondb.parser.statement.IStatement;
import br.furb.jsondb.parser.statement.InsertStatement;
import br.furb.jsondb.parser.statement.SelectStatement;
import br.furb.jsondb.parser.statement.SetDatabaseStatement;

public class StatementParser {

	private static final String CONST_ALL_FIELDS = "*";
	private final Matcher escapeCharMatcher = Pattern.compile("\\\\(\\\\|\\\")").matcher(""); // bytecode: \\(\\|\")

	private IStatement statement;
	private TableIdentifier lastTable;
	private ColumnDefinition lastColumn;
	private Deque<ColumnIdentifier> idStack = new LinkedList<>();
	private Deque<ColumnDefinition> columnDefStack;
	private Deque<ConstraintDefinition> constraintStack;
	private Deque<Value<?>> valuesStack;
	private ColumnType columnType;

	private String constraintName;
	private boolean doneRec;
	private boolean isFinal;

	public void executeAction(int action, Token token) throws SQLParserException {
		switch (action) {
		case 1:
			acaoSemantica01(token);
			break;
		case 2:
			acaoSemantica02(token);
			break;
		case 3:
			acaoSemantica03(token);
			break;
		case 4:
			acaoSemantica04(token);
			break;
		case 8:
			acaoSemantica08(token);
			break;
		case 9:
			acaoSemantica09(token);
			break;
		case 10:
			acaoSemantica10(token);
			break;
		case 11:
			acaoSemantica11(token);
			break;
		case 12:
			acaoSemantica12(token);
			break;
		case 13:
			acaoSemantica13(token);
			break;
		case 14:
			acaoSemantica14(token);
			break;
		case 15:
			acaoSemantica15(token);
			break;
		case 16:
			acaoSemantica16(token);
			break;
		case 17:
			acaoSemantica17(token);
			break;
		case 18:
			acaoSemantica18(token);
			break;
		case 19:
			acaoSemantica19(token);
			break;
		case 20:
			acaoSemantica20(token);
			break;
		case 21:
			acaoSemantica21(token);
			break;
		case 22:
			acaoSemantica22(token);
			break;
		case 23:
			acaoSemantica23(token);
			break;
		case 24:
			acaoSemantica24(token);
			break;
		case 26:
			acaoSemantica26();
			break;
		case 28:
			acaoSemantica28();
			break;
		case 29:
			acaoSemantica29();
			break;
		case 30:
			acaoSemantica30(token);
			break;
		case 31:
			acaoSemantica31(token);
			break;
		case 32:
			acaoSemantica32(token);
			break;
		case 33:
			acaoSemantica33(token);
			break;
		case 34:
			acaoSemantica34(token);
			break;
		case 38:
			acaoSemantica38(token);
			break;
		case 39:
			acaoSemantica39(token);
			break;
		case 51:
			acaoSemantica51(token);
			break;
		case 52:
			acaoSemantica52(token);
			break;
		case 53:
			acaoSemantica53(token);
			break;
		case 55:
			acaoSemantica55(token);
			break;
		case 61:
			acaoSemantica61(token);
			break;
		case 63:
			acaoSemantica63(token);
			break;
		case 65:
			acaoSemantica65(token);
			break;
		case 66:
			acaoSemantica66(token);
			break;
		case 67:
			acaoSemantica67(token);
			break;
		case 99:
			acaoSemantica99(token);
			break;
		default:
			throw new IllegalArgumentException("ação semântica não suportada: " + action);
		}
	}

	/** Declaração de NUMBER. **/
	private void acaoSemantica01(Token token) {
		this.columnType = new ColumnType(DataType.NUMBER);
	}

	/** Declaração de VARCHAR. **/
	private void acaoSemantica02(Token token) {
		this.columnType = new ColumnType(DataType.VARCHAR);
	}

	/** Declaração de DATE. **/
	private void acaoSemantica03(Token token) {
		this.columnType = new ColumnType(DataType.DATE);
	}

	/** Declaração de CHAR. **/
	private void acaoSemantica04(Token token) {
		this.columnType = new ColumnType(DataType.CHAR);
	}

	/** Precisão de tipo. **/
	private void acaoSemantica08(Token token) {
		this.columnType.setPrecision(Integer.parseInt(token.getLexeme()));
	}

	/** Tamanho de tipo. **/
	private void acaoSemantica09(Token token) {
		this.columnType.setSize(Integer.parseInt(token.getLexeme()));
	}

	/** Inicia reconhecimento de colunas no INSERT. **/
	private void acaoSemantica10(Token token) {
		this.idStack.clear();
	}

	/** Encerra reconhecimento de colunas no INSERT. **/
	private void acaoSemantica11(Token token) {
		ArrayList<ColumnIdentifier> columns = new ArrayList<ColumnIdentifier>(this.idStack);
		Collections.reverse(columns);
		this.statement = new InsertStatement(this.lastTable, columns, null);
		this.idStack.clear();
	}

	/**
	 * Nome de coluna. O token reconhecido aqui pode ser transformado em coluna
	 * se for seguido pela ação #14.
	 **/
	private void acaoSemantica12(Token token) {
		idStack.push(new ColumnIdentifier(cleanId(token.getLexeme())));
	}

	/** Nome de tabela. **/
	private void acaoSemantica13(Token token) {
		this.lastTable = tableFromId(token.getLexeme());
	}

	/**
	 * Reconhece nome de coluna, e faz com que o último nome de coluna (#12)
	 * seja considerado nome de tabela (#13).
	 **/
	private void acaoSemantica14(Token token) {
		ColumnIdentifier lastColumn = idStack.pop();
		this.lastTable = new TableIdentifier(lastColumn.getColumnName());

		String actualColumnLexeme = cleanId(token.getLexeme());
		ColumnIdentifier actualColumn = new ColumnIdentifier(this.lastTable, actualColumnLexeme);
		idStack.push(actualColumn);
	}

	/** Nome de restrição usada no CREATE. **/
	private void acaoSemantica15(Token token) {
		this.constraintName = cleanId(token.getLexeme());
	}

	/** Nome de restrição final usada no CREATE. **/
	private void acaoSemantica16(Token token) {
		this.constraintName = cleanId(token.getLexeme());
		this.isFinal = true;
	}

	/** Nome de campo/atributo usado no CREATE. **/
	private void acaoSemantica17(Token token) {
		String lexeme = token.getLexeme();
		this.lastColumn = new ColumnDefinition(cleanId(lexeme));
		this.columnDefStack.push(this.lastColumn);
		this.isFinal = false;
	}

	/** Encerra reconhecimento de lista de campos (SELECT «campos»). **/
	private void acaoSemantica18(Token token) {
		String lexeme = token.getLexeme();
		List<ColumnIdentifier> selectFields;
		if (CONST_ALL_FIELDS.equals(lexeme)) {
			selectFields = new ArrayList<>(1);
			selectFields.add(ColumnIdentifier.ALL);
		} else {
			selectFields = new ArrayList<>(this.idStack);
			Collections.reverse(selectFields);
		}
		this.idStack.clear();
		this.statement = new SelectStatement(selectFields);
	}

	/**
	 * Encerra reconhecimento de lista de tabelas (SELECT campos from
	 * «tabelas»).
	 **/
	private void acaoSemantica19(Token token) {
		List<TableIdentifier> tables = new ArrayList<>(idStack.size());
		idStack.forEach(column -> tables.add(new TableIdentifier(column.getColumnName())));
		Collections.reverse(tables);
		idStack.clear();
		((SelectStatement) statement).setTables(tables);
	}

	/** Restrição NULL. **/
	private void acaoSemantica20(Token token) {
		ConstraintDefinition constraint = new ConstraintDefinition(this.constraintName, ConstraintKind.NULL);
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Restrição NOT NULL. **/
	private void acaoSemantica21(Token token) {
		ConstraintDefinition constraint = new ConstraintDefinition(this.constraintName, ConstraintKind.NOT_NULL);
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Restrição PRIMARY KEY. **/
	private void acaoSemantica22(Token token) {
		ConstraintDefinition constraint = new KeyDefinition(this.constraintName, ConstraintKind.PRIMARY_KEY);
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Restrição [FOREIGN KEY] REFERENCES. **/
	// TODO: ao final, ver se pode ser substituída pela ação 24
	private void acaoSemantica23(Token token) {
		ForeignKeyDefinition constraint = new ForeignKeyDefinition(this.constraintName);
		constraint.addColumn(this.lastColumn.getIdentifier());
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Restrição FOREIGN KEY. **/
	private void acaoSemantica24(Token token) {
		ConstraintDefinition constraint = new ForeignKeyDefinition(this.constraintName);
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Encerra reconhecimento de IDs no {@code PRIMARY KEY ( <<ids>>)}. */
	private void acaoSemantica26() {
		KeyDefinition key = (KeyDefinition) this.constraintStack.peek();
		List<ColumnIdentifier> orderedCols = new ArrayList<>(this.idStack);
		Collections.reverse(orderedCols);

		orderedCols.forEach(column -> key.addColumn(column));
		this.idStack.clear();
	}

	/**
	 * Encerra reconhecimento de IDs no {@code FOREIGN KEY
	 * <table> (<<ids>>)}.
	 **/
	// TODO: ver se pode substituir pela ação 26
	private void acaoSemantica28() {
		ForeignKeyDefinition key = (ForeignKeyDefinition) this.constraintStack.peek();
		List<ColumnIdentifier> orderedCols = new ArrayList<>(this.idStack);
		Collections.reverse(orderedCols);

		orderedCols.forEach(column -> key.addColumn(column));
		this.idStack.clear();
	}

	/**
	 * Encerra reconhecimento de IDs no {@code REFERENCES
	 * <table> (<<ids>>)}.
	 **/
	private void acaoSemantica29() {
		ForeignKeyDefinition key = (ForeignKeyDefinition) this.constraintStack.peek();
		List<ColumnIdentifier> orderedCols = new ArrayList<>(this.idStack);
		Collections.reverse(orderedCols);

		orderedCols.forEach(column -> key.addTargetColumn(column));
		this.idStack.clear();
		key.setTargetTable(this.lastTable);
	}

	/**
	 * Reconhece operador relacional.
	 * 
	 * @throws SQLParserException
	 *             caso seja encontrado um operador não reconhecido
	 **/
	private void acaoSemantica30(Token token) throws SQLParserException {
		final String operator = token.getLexeme();
		switch (operator) {
		case "=":
			break;
		case ">":
			break;
		case "<":
			break;
		case ">=":
			break;
		case "<=":
			break;
		case "<>":
			break;
		default:
			throw new SQLParserException("unrecognizable operator: " + operator);
		}
	}

	/** Reconhece operador lógico. **/
	private void acaoSemantica31(Token token) {
	}

	/** Reconhece número. **/
	private void acaoSemantica32(Token token) {
		this.valuesStack.push(NumberValue.parseNumber(token.getLexeme()));
	}

	/** Reconhece literal. **/
	private void acaoSemantica33(Token token) {
		String rawLiteral = token.getLexeme();

		// remove caractere de escape
		escapeCharMatcher.reset(rawLiteral);
		String parsedLiteral = escapeCharMatcher.replaceAll("$1");

		// remove aspas de início e fim
		parsedLiteral = parsedLiteral.replaceAll("^\"|\"$", "");

		this.valuesStack.push(new StringValue(parsedLiteral));
	}

	/** Reconhece data. **/
	private void acaoSemantica34(Token token) {
	}

	/** Inicia reconhecimento de valores no INSERT. **/
	private void acaoSemantica38(Token token) {
		if (this.statement == null) {
			this.statement = new InsertStatement(this.lastTable, null, null);
		}
		this.valuesStack = new LinkedList<>();
	}

	/** Encerra reconhecimento de valores no INSERT. **/
	private void acaoSemantica39(Token token) {
		InsertStatement insert = (InsertStatement) this.statement;
		ArrayList<Value<?>> orderedValues = new ArrayList<>(this.valuesStack);
		Collections.reverse(orderedValues);
		insert.addValues(orderedValues);
		this.valuesStack.clear();
	}

	/** Nome de base de dados sendo criada. **/
	private void acaoSemantica51(Token token) {
		this.statement = new CreateStatement(new DatabaseIdentifier(cleanId(token.getLexeme())));
	}

	/** Nome de tabela sendo criada. **/
	private void acaoSemantica52(Token token) {
		this.statement = new CreateStatement(new TableDefinition(tableFromId(token.getLexeme())));
		this.columnDefStack = new LinkedList<>();
		this.constraintStack = new LinkedList<>();
	}

	/** Encerra reconhecimento do tipo. **/
	private void acaoSemantica53(Token token) {
		ColumnDefinition column = this.columnDefStack.pop();
		column.setColumnType(this.columnType);

		TableDefinition table = (TableDefinition) ((CreateStatement) this.statement).getStructure();
		table.addColumnDefinition(column);
	}

	/**
	 * Encerra reconhecimento da restrição. Atribui a restrição à última coluna
	 * reconhecida, caso faça parte da declaração da mesma. Do contrário,
	 * armazena na definição da tabela.
	 */
	private void acaoSemantica55(Token token) {
		TableDefinition table = (TableDefinition) ((CreateStatement) this.statement).getStructure();
		ConstraintDefinition constraint = this.constraintStack.pop();
		if (this.isFinal) {
			table.addFinalConstraint(constraint);
			((KeyDefinition) constraint).setFinal(true);
		} else {
			this.lastColumn.setConstraint(constraint);
			this.lastColumn = null;
		}
	}

	/** Nome de tabela a ser removida (DROP). **/
	private void acaoSemantica61(Token token) {
		statement = new DropStatement<IStructure>(tableFromId(token.getLexeme()));
	}

	/** Nome de tabela a ser descrita (DESCRIBE). **/
	private void acaoSemantica63(Token token) {
	}

	/** Nome de base a ser usada (SET DATABASE). **/
	private void acaoSemantica65(Token token) {
		DatabaseIdentifier database = new DatabaseIdentifier(cleanId(token.getLexeme()));
		statement = new SetDatabaseStatement(database);
	}

	/** CREATE INDEX. **/
	private void acaoSemantica66(Token token) {
	}

	/** DROP INDEX. **/
	private void acaoSemantica67(Token token) {
		ColumnIdentifier lastColumn = this.idStack.pop();
		Index index = new Index(new ColumnIdentifier(lastTable, lastColumn.getColumnName()));
		this.statement = new DropStatement<IStructure>(index);
	}

	/** Finaliza reconhecimento de sentenca. **/
	private void acaoSemantica99(Token token) {
		doneRec = true;
	}

	/**
	 * Retorna a sentença reconhecida.
	 * 
	 * @return sentença reconhecida.
	 */
	public IStatement getStatement() {
		if (!doneRec) {
			return null;
		}
		return statement;
	}

	// Métodos auxiliares

	private static TableIdentifier tableFromId(String lexeme) {
		String tableName = cleanId(lexeme);
		return new TableIdentifier(tableName);
	}

	private static String cleanId(String lexeme) {
		return fixCase(lexeme.replaceAll("\'", ""));
	}

	private static String fixCase(String id) {
		return id == null ? null : id.toLowerCase();
	}

}
