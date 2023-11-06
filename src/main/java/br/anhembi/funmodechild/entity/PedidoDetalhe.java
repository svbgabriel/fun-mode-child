package br.anhembi.funmodechild.entity;

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

@Entity
@Table(name = "detalhe_pedidos")
@Data
public class PedidoDetalhe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Product product;
    @Column(name = "preco_item")
    private double precoItem;
    @Column(name = "quantidade")
    private int quantidade;
}
