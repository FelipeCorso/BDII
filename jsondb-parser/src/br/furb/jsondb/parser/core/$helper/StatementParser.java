package br.furb.jsondb.parser.core.$helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.furb.jsondb.parser.ColumnDefinition;
import br.furb.jsondb.parser.ColumnIdentifier;
import br.furb.jsondb.parser.ColumnType;
import br.furb.jsondb.parser.ConstraintDefinition;
import br.furb.jsondb.parser.ConstraintKind;
import br.furb.jsondb.parser.DataType;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.parser.Date;
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
import br.furb.jsondb.parser.WhereClause;
import br.furb.jsondb.parser.conditions.ICondition;
import br.furb.jsondb.parser.conditions.LogicalCondition;
import br.furb.jsondb.parser.conditions.LogicalOperator;
import br.furb.jsondb.parser.conditions.RelationalCondition;
import br.furb.jsondb.parser.conditions.RelationalOperator;
import br.furb.jsondb.parser.core.Token;
import br.furb.jsondb.parser.statement.CreateStatement;
import br.furb.jsondb.parser.statement.DescribeStatement;
import br.furb.jsondb.parser.statement.DropStatement;
import br.furb.jsondb.parser.statement.IStatement;
import br.furb.jsondb.parser.statement.InsertStatement;
import br.furb.jsondb.parser.statement.SelectStatement;
import br.furb.jsondb.parser.statement.SetDatabaseStatement;

public class StatementParser {

	private static final String CONST_ALL_FIELDS = "*";
	private final Matcher escapeCharMatcher = Pattern.compile("\\\\(\\\\|\\\")").matcher(""); // bytecode: \\(\\|\")

	private IStatement statement;
	private final List<IStatement> statements = new LinkedList<>();
	private TableIdentifier lastTable;
	private ColumnDefinition lastColumn;
	private final Deque<ColumnIdentifier> idStack = new LinkedList<>();
	private Deque<ColumnDefinition> columnDefStack;
	private Deque<ConstraintDefinition> constraintStack;
	private final Deque<Value<?>> valuesStack = new LinkedList<>();
	private final List<ICondition<?, ?>> conditions = new LinkedList<>();
	private ColumnType columnType;
	private RelationalOperator operator;
	private String constraintName;

	private boolean doneRec;
	private boolean isFinal;

	public void executeAction(int action, Token token) throws SQLParserException {
		switch (action) {
		case 1:
			acaoSemantica01();
			break;
		case 2:
			acaoSemantica02();
			break;
		case 3:
			acaoSemantica03();
			break;
		case 4:
			acaoSemantica04();
			break;
		case 8:
			acaoSemantica08(token);
			break;
		case 9:
			acaoSemantica09(token);
			break;
		case 10:
			acaoSemantica10();
			break;
		case 11:
			acaoSemantica11();
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
			acaoSemantica19();
			break;
		case 20:
			acaoSemantica20();
			break;
		case 21:
			acaoSemantica21();
			break;
		case 22:
			acaoSemantica22();
			break;
		case 23:
			acaoSemantica23();
			break;
		case 24:
			acaoSemantica24();
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
		case 37:
			acaoSemantica37();
			break;
		case 38:
			acaoSemantica38();
			break;
		case 39:
			acaoSemantica39();
			break;
		case 51:
			acaoSemantica51(token);
			break;
		case 52:
			acaoSemantica52(token);
			break;
		case 53:
			acaoSemantica53();
			break;
		case 55:
			acaoSemantica55();
			break;
		case 56:
			acaoSemantica56();
			break;
		case 57:
			acaoSemantica57();
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
		case 67:
			acaoSemantica67(token);
			break;
		case 98:
			acaoSemantica98();
			break;
		case 99:
			acaoSemantica99();
			break;
		default:
			throw new IllegalArgumentException("unsupported semantic action: " + action);
		}
	}

	/** Declaração de NUMBER. **/
	private void acaoSemantica01() {
		this.columnType = new ColumnType(DataType.NUMBER);
	}

	/** Declaração de VARCHAR. **/
	private void acaoSemantica02() {
		this.columnType = new ColumnType(DataType.VARCHAR);
	}

	/** Declaração de DATE. **/
	private void acaoSemantica03() {
		this.columnType = new ColumnType(DataType.DATE);
	}

	/** Declaração de CHAR. **/
	private void acaoSemantica04() {
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
	private void acaoSemantica10() {
		this.idStack.clear();
	}

	/** Encerra reconhecimento de colunas no INSERT. **/
	private void acaoSemantica11() {
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
	private void acaoSemantica19() {
		List<TableIdentifier> tables = new ArrayList<>(idStack.size());
		idStack.forEach(column -> tables.add(new TableIdentifier(column.getColumnName())));
		Collections.reverse(tables);
		idStack.clear();
		((SelectStatement) statement).setTables(tables);
	}

	/** Restrição NULL. **/
	private void acaoSemantica20() {
		ConstraintDefinition constraint = new ConstraintDefinition(this.constraintName, ConstraintKind.NULL);
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Restrição NOT NULL. **/
	private void acaoSemantica21() {
		ConstraintDefinition constraint = new ConstraintDefinition(this.constraintName, ConstraintKind.NOT_NULL);
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Restrição PRIMARY KEY. **/
	private void acaoSemantica22() {
		ConstraintDefinition constraint = new KeyDefinition(this.constraintName, ConstraintKind.PRIMARY_KEY);
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Restrição [FOREIGN KEY] REFERENCES. **/
	private void acaoSemantica23() {
		ForeignKeyDefinition constraint = new ForeignKeyDefinition(this.constraintName);
		constraint.addColumn(this.lastColumn.getIdentifier());
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Restrição FOREIGN KEY. **/
	private void acaoSemantica24() {
		ConstraintDefinition constraint = new ForeignKeyDefinition(this.constraintName);
		this.constraintStack.push(constraint);
		this.constraintName = null;
	}

	/** Encerra reconhecimento de IDs no {@code PRIMARY KEY ( <<ids>>)}. */
	private void acaoSemantica26() {
		endKeysRecognition();
	}

	/**
	 * Encerra reconhecimento de IDs no {@code FOREIGN KEY
	 * <table> (<<ids>>)}.
	 **/
	private void acaoSemantica28() {
		endKeysRecognition();
	}

	private void endKeysRecognition() {
		KeyDefinition key = (KeyDefinition) this.constraintStack.peek();
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
		final String operatorLexeme = token.getLexeme();
		RelationalOperator operator;
		switch (operatorLexeme) {
		case "=":
			operator = RelationalOperator.EQUAL;
			break;
		case ">":
			operator = RelationalOperator.GREATER;
			break;
		case "<":
			operator = RelationalOperator.LESSER;
			break;
		case ">=":
			operator = RelationalOperator.GREATER_OR_EQUAL;
			break;
		case "<=":
			operator = RelationalOperator.LESSER_OR_EQUAL;
			break;
		case "<>":
			operator = RelationalOperator.NOT_EQUAL;
			break;
		default:
			throw new SQLParserException("unrecognized operator: " + operatorLexeme);
		}
		this.operator = operator;
	}

	/**
	 * Reconhece operador lógico.
	 * 
	 * @throws SQLParserException
	 *             caso seja encontrado um operador não reconhecido
	 **/
	private void acaoSemantica31(Token token) throws SQLParserException {
		String operatorLexeme = token.getLexeme().toUpperCase();
		LogicalOperator operator;
		switch (operatorLexeme) {
		case "AND":
			operator = LogicalOperator.AND;
			break;
		case "OR":
			operator = LogicalOperator.OR;
			break;
		default:
			throw new SQLParserException("unrecognized operator: " + operatorLexeme);
		}
		LogicalCondition condition = new LogicalCondition(operator);
		this.conditions.add(condition);
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
		Date date = new Date(token.getLexeme());
		this.valuesStack.push(date);
	}

	/** Reconhece condição relacional. */
	private void acaoSemantica37() {
		RelationalCondition condition = new RelationalCondition(operator, this.idStack.pop(), this.valuesStack.pop());
		this.conditions.add(condition);
	}

	/** Inicia reconhecimento de valores no INSERT. **/
	private void acaoSemantica38() {
		if (this.statement == null) {
			this.statement = new InsertStatement(this.lastTable, null, null);
		}
		this.valuesStack.clear();
	}

	/** Encerra reconhecimento de valores no INSERT. **/
	private void acaoSemantica39() {
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
	private void acaoSemantica53() {
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
	private void acaoSemantica55() {
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

	/** Encerra reconhecimento de condições WHERE. */
	private void acaoSemantica56() {
		// Junta operadores lógicos
		ICondition<?, ?> current;

		// Primeiro AND, depois OR
		for (LogicalOperator logicalOperator : Arrays.asList(LogicalOperator.AND, LogicalOperator.OR)) {
			ListIterator<ICondition<?, ?>> it = this.conditions.listIterator();
			while (it.hasNext()) {
				current = it.next();
				if (current.isLogical() && current.getOperator() == logicalOperator) {
					LogicalCondition logical = (LogicalCondition) current;
					// Define condição anterior como operando da esquerda
					it.previous();
					logical.setLeftOperand(it.previous());
					// Remove condição adicionada
					it.remove();
					it.next();
					// Define condição seguinte como operando da direita
					logical.setRightOperand(it.next());
					// Remove condição adicionada
					it.previous();
					it.remove();
				}
			}
		}
		if (this.conditions.size() > 1) {
			System.err.println("[StatementParser] more than one condition remained after defining precedence: " + this.conditions.toString());
		}
		WhereClause whereClause = new WhereClause(this.conditions.get(0));
		((SelectStatement) this.statement).setWhereClause(whereClause);
	}

	/** Encerra reconhecimento de CREATE INDEX. **/
	private void acaoSemantica57() {
		ColumnIdentifier column = new ColumnIdentifier(this.lastTable, this.idStack.pop().getColumnName());
		Index index = new Index(this.idStack.pop().getColumnName(), column);
		this.statement = new CreateStatement(index);
	}

	/** Nome de tabela a ser removida (DROP). **/
	private void acaoSemantica61(Token token) {
		statement = new DropStatement<IStructure>(tableFromId(token.getLexeme()));
	}

	/** Nome de tabela a ser descrita (DESCRIBE). **/
	private void acaoSemantica63(Token token) {
		this.statement = new DescribeStatement(tableFromId(token.getLexeme()));
	}

	/** Nome de base a ser usada (SET DATABASE). **/
	private void acaoSemantica65(Token token) {
		DatabaseIdentifier database = new DatabaseIdentifier(cleanId(token.getLexeme()));
		statement = new SetDatabaseStatement(database);
	}

	/** Nome de índice a ser removido (DROP INDEX). **/
	private void acaoSemantica67(Token token) {
		Index index = new Index(cleanId(token.getLexeme()), null);
		this.statement = new DropStatement<IStructure>(index);
	}

	/** Finaliza reconhecimento de sentenca. **/
	@SuppressWarnings("unchecked")
	private void acaoSemantica98() {
		if (statement instanceof DropStatement && ((DropStatement<?>) statement).getStructure() instanceof Index) {
			((DropStatement<Index>) statement).getStructure().setTable(lastTable);
		}
		this.statements.add(statement);
		// limpar campos
		statement = null;
		lastTable = null;
		lastColumn = null;
		idStack.clear();
		columnDefStack = null;
		constraintStack = null;
		valuesStack.clear();
		conditions.clear();
		columnType = null;
		operator = null;
		constraintName = null;
		isFinal = false;

	}

	/** Encerra reconhecimento de documento. */
	private void acaoSemantica99() {
		doneRec = true;
	}

	/**
	 * Retorna a sentença reconhecida.
	 * 
	 * @return sentença reconhecida.
	 */
	public List<IStatement> getStatements() {
		if (!doneRec) {
			return null;
		}
		return statements;
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
