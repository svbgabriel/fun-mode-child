package br.anhembi.funmodechild.models;

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
@Table(name = "produtos")
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    private Date dtCadastrado;
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
