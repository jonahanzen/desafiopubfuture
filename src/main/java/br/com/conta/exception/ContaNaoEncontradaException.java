package br.com.conta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.exception.ApiException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ContaNaoEncontradaException extends ApiException {

	private static final long serialVersionUID = -7639850447799051390L;

	public ContaNaoEncontradaException(Long contaId) {
		super("A conta: " + contaId + " Nao foi encontrada.");
	}
}
