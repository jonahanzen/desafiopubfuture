package br.com.receita.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.exception.ApiException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ReceitaNaoEncontradaException extends ApiException {

	public ReceitaNaoEncontradaException() {
		super("A receita: " + " Nao foi encontrada");
	}

	private static final long serialVersionUID = -6649968128928095773L;

}
