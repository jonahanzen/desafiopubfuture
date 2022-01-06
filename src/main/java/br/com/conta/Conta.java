package br.com.conta;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.conta.enums.TipoConta;
import br.com.despesa.Despesa;
import br.com.receita.Receita;
import lombok.Data;

@Entity
@Data
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	List<Receita> receitas;

	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	List<Despesa> despesas;

	private Double saldo;
	private TipoConta tipoConta;
	private String instituicaoFinanceira;

}
