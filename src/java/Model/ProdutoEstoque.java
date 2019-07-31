package Model;

import java.sql.Date;

public class ProdutoEstoque {

    private int sku;
    private String nome;
    private String descricao;
    private double preco;
    private boolean promovido;
    private int categoria_id;
    private String refe;
    private Date dt_cadastrado;
    private String refeBig;
    private int qtdEstoque;

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isPromovido() {
        return promovido;
    }

    public void setPromovido(boolean promovido) {
        this.promovido = promovido;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public Date getDt_cadastrado() {
        return dt_cadastrado;
    }

    public void setDt_cadastrado(Date dt_cadastrado) {
        this.dt_cadastrado = dt_cadastrado;
    }

    public String getRefe() {
        return refe;
    }

    public void setRefe(String refe) {
        this.refe = refe;
    }

    public String getRefeBig() {
        return refeBig;
    }

    public void setRefeBig(String refeBig) {
        this.refeBig = refeBig;
    }
    
        public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }
    
    /**
     * Retorna o preço formatado como 0.000,00.
     * 
     * @return  o preço formatado com duas casas decimais.
     */
    public String getPrecoFormatado() {
        return String.format("%1$,.2f", this.preco);
    }
}

