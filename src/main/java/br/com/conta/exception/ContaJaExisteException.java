package br.com.conta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.exception.ApiException;
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ContaJaExisteException extends ApiException {

	private static final long serialVersionUID = 1400589900816547926L;

	public ContaJaExisteException(Long idConta) {
	super("A conta: " + idConta + " Ja existe");
	}
}
