package br.anhembi.funmodechild.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "pagamentos")
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    @Column(name = "data_pagamento")
    private Date dataPagamento;
}
