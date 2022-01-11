package br.com.conta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.exception.ApiException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DadosContaIncorretosException extends ApiException {

	private static final long serialVersionUID = 743447040058014281L;

	public DadosContaIncorretosException(Long contaID) {
		super("A conta: " + contaID + " apresenta dados invalidos, favor tente novamente");
	}
}
