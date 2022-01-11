package br.com.exception;

public class ApiException extends Exception {

	private static final long serialVersionUID = 9145792270703172371L;

	public ApiException(String mensagem) {
		super(mensagem);
	}

}
