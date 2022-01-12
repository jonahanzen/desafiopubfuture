package br.com.conta.dto;

import br.com.conta.enums.TipoConta;
import lombok.Data;

@Data
/**
 * Classe criada para conter apenas os dados necessarios para alterar uma conta
 * {@link Conta}
 * 
 *
 */
public class EditarContaDTO {

	private Long id;
	private Double saldo;
	private TipoConta tipoConta;
	private String instituicaoFinanceira;

}
