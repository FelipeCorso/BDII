package br.furb.jsondb.parser.core.$helper;

import br.furb.jsondb.parser.core.Token;

public class StatementParser {

	public void executeAction(int action, Token token) {
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
			acaoSemantica08();
			break;
		case 9:
			acaoSemantica09();
			break;
		case 10:
			acaoSemantica10();
			break;
		case 11:
			acaoSemantica11();
			break;
		case 12:
			acaoSemantica12();
			break;
		case 13:
			acaoSemantica13();
			break;
		case 14:
			acaoSemantica14();
			break;
		case 15:
			acaoSemantica15();
			break;
		case 16:
			acaoSemantica16();
			break;
		case 17:
			acaoSemantica17();
			break;
		case 18:
			acaoSemantica18();
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
		case 30:
			acaoSemantica30();
			break;
		case 31:
			acaoSemantica31();
			break;
		case 32:
			acaoSemantica32();
			break;
		case 33:
			acaoSemantica33();
			break;
		case 34:
			acaoSemantica34();
			break;
		case 38:
			acaoSemantica38();
			break;
		case 39:
			acaoSemantica39();
			break;
		case 51:
			acaoSemantica51();
			break;
		case 53:
			acaoSemantica53();
			break;
		case 55:
			acaoSemantica55();
			break;
		case 61:
			acaoSemantica61();
			break;
		case 63:
			acaoSemantica63();
			break;
		case 65:
			acaoSemantica65();
			break;
		case 66:
			acaoSemantica66();
			break;
		case 67:
			acaoSemantica67();
			break;
		case 99:
			acaoSemantica99();
			break;
		default:
			throw new IllegalArgumentException("ação semântica não suportada: " + action);
		}
	}

	/** Declaração de NUMBER. **/
	private void acaoSemantica01() {
	}

	/** Declaração de VARCHAR. **/
	private void acaoSemantica02() {
	}

	/** Declaração de DATE. **/
	private void acaoSemantica03() {
	}

	/** Declaração de CHAR. **/
	private void acaoSemantica04() {
	}

	/** Precisão de tipo. **/
	private void acaoSemantica08() {
	}

	/** Tamanho de tipo. **/
	private void acaoSemantica09() {
	}

	/** Inicia reconhecimento de colunas no INSERT. **/
	private void acaoSemantica10() {
	}

	/** Encerra reconhecimento de colunas no INSERT. **/
	private void acaoSemantica11() {
	}

	/** Nome de coluna. **/
	private void acaoSemantica12() {
	}

	/** Nome de tabela. **/
	private void acaoSemantica13() {
	}

	/**
	 * Reconhece nome de coluna, e faz com que o ultimo nome de coluna (#12)
	 * seja considerado nome de tabela (#13).
	 **/
	private void acaoSemantica14() {
	}

	/** Nome de restrição usada no CREATE. **/
	private void acaoSemantica15() {
	}

	/** Finaliza reconhecimento de lista de IDs. **/
	private void acaoSemantica16() {
	}

	/** Nome de campo/atributo usado no CREATE. **/
	private void acaoSemantica17() {
	}

	/** Encerra reconhecimento de lista de campos (SELECT «campos»). **/
	private void acaoSemantica18() {
	}

	/**
	 * Encerra reconhecimento de lista de tabelas (SELECT campos from
	 * «tabelas»).
	 **/
	private void acaoSemantica19() {
	}

	/** Restrição NULL. **/
	private void acaoSemantica20() {
	}

	/** Restrição NOT NULL. **/
	private void acaoSemantica21() {
	}

	/** Restrição PRIMARY KEY. **/
	private void acaoSemantica22() {
	}

	/** Restrição [FOREIGN KEY] REFERENCES. **/
	private void acaoSemantica23() {
	}

	/** Restrição FOREIGN KEY. **/
	private void acaoSemantica24() {
	}

	/** Reconhece operador relacional. **/
	private void acaoSemantica30() {
	}

	/** Reconhece operador lógico. **/
	private void acaoSemantica31() {
	}

	/** Reconhece número. **/
	private void acaoSemantica32() {
	}

	/** Reconhece literal. **/
	private void acaoSemantica33() {
	}

	/** Reconhece data. **/
	private void acaoSemantica34() {
	}

	/** Inicia reconhecimento de valores no INSERT. **/
	private void acaoSemantica38() {
	}

	/** Encerra reconhecimento de valores no INSERT. **/
	private void acaoSemantica39() {
	}

	/** Nome de base de dados. **/
	private void acaoSemantica51() {
	}

	/** Encerra reconhecimento do tipo. **/
	private void acaoSemantica53() {
	}

	/** Encerra reconhecimento da restrição. **/
	private void acaoSemantica55() {
	}

	/** Nome de tabela e ser removida (DROP). **/
	private void acaoSemantica61() {
	}

	/** Nome de tabela a ser descrita (DESCRIBE). **/
	private void acaoSemantica63() {
	}

	/** Nome de base a ser usada (SET DATABASE). **/
	private void acaoSemantica65() {
	}

	/** CREATE INDEX. **/
	private void acaoSemantica66() {
	}

	/** DROP INDEX. **/
	private void acaoSemantica67() {
	}

	/** Finaliza reconhecimento de sentenca. **/
	private void acaoSemantica99() {
	}

}
