package br.com.despesa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.exception.ApiException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DespesaNaoEncontradaException extends ApiException {

	public DespesaNaoEncontradaException(Long despesaId) {
		super("A despesa: " + despesaId + " Nao existe");
	}

	private static final long serialVersionUID = 7644641346631637898L;

}
