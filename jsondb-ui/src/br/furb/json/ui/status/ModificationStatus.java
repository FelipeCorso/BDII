package br.furb.json.ui.status;

/**
 * Indica o estado de modificação de um documento desde a última vez que foi
 * salvo ou desde que foi criado.
 */
public enum ModificationStatus {

	/**
	 * Indica que o documento está inalterado desde a última vez que foi salvo
	 * ou desde que foi criado.
	 */
	UNMODIFIED("N\u00E3o modificado"), //
	/**
	 * Indica que o documento foi modificado desde a última vez que foi salvo ou
	 * desde que foi criado.
	 */
	MODIFIED("Modificado");

	private String description;

	private ModificationStatus(String descricao) {
		this.description = descricao;
	}

	/**
	 * Retorna a representação textual humano-legível para o estado de
	 * modificação.
	 */
	@Override
	public String toString() {
		return this.description;
	}
}
