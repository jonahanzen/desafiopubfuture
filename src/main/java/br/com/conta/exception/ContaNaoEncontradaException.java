package br.com.conta.exception;

import br.com.exception.ApiException;

public class ContaNaoEncontradaException extends ApiException {

	public ContaNaoEncontradaException(Long contaId) {
		super("A conta: " + contaId + "Nao foi encontrada.");

	}

	private static final long serialVersionUID = -7639850447799051390L;

}
