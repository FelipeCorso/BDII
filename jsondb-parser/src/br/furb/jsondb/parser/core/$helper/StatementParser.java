package br.furb.jsondb.parser.core.$helper;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import br.furb.jsondb.parser.CreateStatement;
import br.furb.jsondb.parser.DatabaseIdentifier;
import br.furb.jsondb.parser.DropStatement;
import br.furb.jsondb.parser.IStatement;
import br.furb.jsondb.parser.IStructure;
import br.furb.jsondb.parser.Index;
import br.furb.jsondb.parser.SelectStatement;
import br.furb.jsondb.parser.SetDatabaseStatement;
import br.furb.jsondb.parser.TableColumn;
import br.furb.jsondb.parser.TableIdentifier;
import br.furb.jsondb.parser.core.Token;

public class StatementParser {

	private static final String CONST_ALL_FIELDS = "*";

	private IStatement statement;
	private boolean doneRec;
	private TableIdentifier lastTable;
	private Deque<TableColumn> columnStack = new LinkedList<>();

	public void executeAction(int action, Token token) {
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
	}

	/** Declaração de VARCHAR. **/
	private void acaoSemantica02(Token token) {
	}

	/** Declaração de DATE. **/
	private void acaoSemantica03(Token token) {
	}

	/** Declaração de CHAR. **/
	private void acaoSemantica04(Token token) {
	}

	/** Precisão de tipo. **/
	private void acaoSemantica08(Token token) {
	}

	/** Tamanho de tipo. **/
	private void acaoSemantica09(Token token) {
	}

	/** Inicia reconhecimento de colunas no INSERT. **/
	private void acaoSemantica10(Token token) {
	}

	/** Encerra reconhecimento de colunas no INSERT. **/
	private void acaoSemantica11(Token token) {
	}

	/**
	 * Nome de coluna. O token reconhecido aqui pode ser transformado em coluna
	 * se for seguido pela ação #14.
	 **/
	private void acaoSemantica12(Token token) {
		columnStack.push(new TableColumn(cleanId(token.getLexeme())));
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
		TableColumn lastColumn = columnStack.pop();
		this.lastTable = new TableIdentifier(lastColumn.getColumnName());

		String actualColumnLexeme = cleanId(token.getLexeme());
		TableColumn actualColumn = new TableColumn(this.lastTable, actualColumnLexeme);
		columnStack.push(actualColumn);
	}

	/** Nome de restrição usada no CREATE. **/
	private void acaoSemantica15(Token token) {
	}

	/** Finaliza reconhecimento de lista de IDs. **/
	private void acaoSemantica16(Token token) {
	}

	/** Nome de campo/atributo usado no CREATE. **/
	private void acaoSemantica17(Token token) {
	}

	/** Encerra reconhecimento de lista de campos (SELECT «campos»). **/
	private void acaoSemantica18(Token token) {
		String lexeme = token.getLexeme();
		List<TableColumn> selectFields;
		if (CONST_ALL_FIELDS.equals(lexeme)) {
			selectFields = new ArrayList<>(1);
			selectFields.add(TableColumn.ALL);
		} else {
			selectFields = new ArrayList<>(this.columnStack);
		}
		this.columnStack.clear();
		this.statement = new SelectStatement(selectFields);
	}

	/**
	 * Encerra reconhecimento de lista de tabelas (SELECT campos from
	 * «tabelas»).
	 **/
	private void acaoSemantica19(Token token) {
		List<TableIdentifier> tables = new ArrayList<>(columnStack.size());
		columnStack.forEach(column -> tables.add(new TableIdentifier(column.getColumnName())));
		columnStack.clear();
		((SelectStatement) statement).setTables(tables);
	}

	/** Restrição NULL. **/
	private void acaoSemantica20(Token token) {
	}

	/** Restrição NOT NULL. **/
	private void acaoSemantica21(Token token) {
	}

	/** Restrição PRIMARY KEY. **/
	private void acaoSemantica22(Token token) {
	}

	/** Restrição [FOREIGN KEY] REFERENCES. **/
	private void acaoSemantica23(Token token) {
	}

	/** Restrição FOREIGN KEY. **/
	private void acaoSemantica24(Token token) {
	}

	/** Reconhece operador relacional. **/
	private void acaoSemantica30(Token token) {
	}

	/** Reconhece operador lógico. **/
	private void acaoSemantica31(Token token) {
	}

	/** Reconhece número. **/
	private void acaoSemantica32(Token token) {
	}

	/** Reconhece literal. **/
	private void acaoSemantica33(Token token) {
	}

	/** Reconhece data. **/
	private void acaoSemantica34(Token token) {
	}

	/** Inicia reconhecimento de valores no INSERT. **/
	private void acaoSemantica38(Token token) {
	}

	/** Encerra reconhecimento de valores no INSERT. **/
	private void acaoSemantica39(Token token) {
	}

	/** Nome de base de dados. **/
	private void acaoSemantica51(Token token) {
		this.statement = new CreateStatement(new DatabaseIdentifier(cleanId(token.getLexeme())));
	}

	/** Encerra reconhecimento do tipo. **/
	private void acaoSemantica53(Token token) {
	}

	/** Encerra reconhecimento da restrição. **/
	private void acaoSemantica55(Token token) {
	}

	/** Nome de tabela e ser removida (DROP). **/
	private void acaoSemantica61(Token token) {
		statement = new DropStatement<IStructure>(tableFromId(token.getLexeme()));
	}

	/** Nome de tabela a ser descrita (DESCRIBE). **/
	private void acaoSemantica63(Token token) {
	}

	/** Nome de base a ser usada (SET DATABASE). **/
	private void acaoSemantica65(Token token) {
		statement = new SetDatabaseStatement(tableFromId(token.getLexeme()));
	}

	/** CREATE INDEX. **/
	private void acaoSemantica66(Token token) {
	}

	/** DROP INDEX. **/
	private void acaoSemantica67(Token token) {
		TableColumn lastColumn = this.columnStack.pop();
		Index index = new Index(new TableColumn(lastTable, lastColumn.getColumnName()));
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
		return lexeme.replaceAll("\'", "");
	}

}
