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
@Table(name = "pagamentos")
@Data public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;
	@Column(name = "numero_cartao")
	private String numeroCartao;
	@Column(name = "nome_cartao")
	private String nomeCartao;
	@Column(name = "validade_mes")
	private int validadeMes;
	@Column(name = "validade_ano")
	private int validadeAno;
	@Column(name = "codigo")
	private int codigo;
	@Column(name = "parcelas")
	private int parcelas;
	@Column(name = "ativo")
	private boolean ativo;
	@Column(name = "data_pagamento")
	private Date dataPagamento;

}
