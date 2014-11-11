package br.furb.jsondb.parser.conditions;

/**
 * Condição da cláusula {@code WHERE} que compara duas
 * 
 * @author William Leander Seefeld
 *
 * @param <OperandType>
 *            tipo operador da direita.
 * @param <OperatorType>
 *            tipo do operador de comparação, que varia entre condição lógica e
 *            condição relacional.
 */
public interface ICondition<OperandType, OperatorType> {

	/**
	 * Testa se esta condição é lógica. Caso não, então ela é relacional.
	 * 
	 * @return {@code true} se esta condição é lógica. {@code false} se ela é
	 *         relacional.
	 */
	boolean isLogical();

	public OperandType getRightOperand();

	public OperatorType getOperator();

}
