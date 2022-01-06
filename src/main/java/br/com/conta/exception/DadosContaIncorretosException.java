package br.com.conta.exception;

import br.com.exception.ApiException;

public class DadosContaIncorretosException extends ApiException {

	private static final long serialVersionUID = 743447040058014281L;

	public DadosContaIncorretosException(Long contaID) {
		super("A conta: " + contaID + " apresenta dados invalidos, favor tente novamente");
	}
}
