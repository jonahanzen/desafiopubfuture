package br.com.conta;

import br.com.conta.enums.TipoConta;
import lombok.Data;

@Data
public class ContaDTO {

	private Long id;
	private Double saldo;
	private TipoConta tipoConta;
	private String instituicaoFinanceira;
	
}
