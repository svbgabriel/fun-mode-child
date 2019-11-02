package br.anhembi.funmodechild.models;

import lombok.Data;

@Data public class ProdutoCarrinho {

	private Produto produto;
	private int quantidade;
	private double precoTotal;
	
	public String getPrecoTotalFormatado() {
		return String.format("%1$,.2f", this.precoTotal);
	}
}
