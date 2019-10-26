package br.anhembi.funmodechild.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "produtos")
@Data public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "sku")
	private int sku;
	@Column(name = "nome")
	private String nome;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "preco")
	private double preco;
	@Column(name = "promovido")
	private boolean promovido;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	@Column(name = "refe")
	private String refe;
	@Column(name = "refebig")
	private String refeBig;
	@Column(name = "dt_cadastrado")
	private Date dt_cadastrado;
	@Column(name = "quantidade")
	private int quantidade;

	/**
	 * Retorna o preço formatado como 0.000,00.
	 * 
	 * @return o preço formatado com duas casas decimais.
	 */
	public String getPrecoFormatado() {
		return String.format("%1$,.2f", this.preco);
	}
}
