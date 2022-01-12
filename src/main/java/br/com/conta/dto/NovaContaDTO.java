package br.com.conta.dto;

import br.com.conta.enums.TipoConta;
import lombok.Data;

@Data
/**
 * Classe criada para conter apenas os dados necessarios para a inclusao de uma
 * conta {@link Conta}
 *
 */
public class NovaContaDTO {

	private Double saldo;
	private TipoConta tipoConta;
	private String instituicaoFinanceira;

}
