package br.com.conta.dto;

import br.com.conta.enums.TipoConta;
import lombok.Data;

@Data
/**
 * Classe que contem apenas as propriedades necessarias para alterar uma Conta
 * 
 *
 */
public class EditarContaDTO {

	private Double saldo;
	private TipoConta tipoConta;
	private String instituicaoFinanceira;

}
