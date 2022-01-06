package br.com.conta.exception;

import br.com.exception.ApiException;

public class ContaJaExisteException extends ApiException {

	private static final long serialVersionUID = 1400589900816547926L;

	public ContaJaExisteException(Long idConta) {
	super("A conta: " + idConta + " Ja existe");
	}
}
