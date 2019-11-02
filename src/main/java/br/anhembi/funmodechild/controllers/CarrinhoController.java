package br.anhembi.funmodechild.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.anhembi.funmodechild.models.Carrinho;
import br.anhembi.funmodechild.models.Produto;
import br.anhembi.funmodechild.models.ProdutoCarrinho;
import br.anhembi.funmodechild.repositories.RepositoryProduto;

@Controller
@Scope("session")
public class CarrinhoController {

	@Autowired
	RepositoryProduto repositoryProduto;

	// TODO: Encontrar uma maneira de limpar a mensagem
	List<String> messages = new ArrayList<>();
	
	@GetMapping("/carrinho")
	public ModelAndView carrinho(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		// Recupera os valores do request
        String sku = request.getParameter("sku");
        String del = request.getParameter("del");
		
	    // O carrinho contém apenas o SKU e sua quantidade.
	    Carrinho carrinho = (Carrinho) session.getAttribute("carrinhocompras");
	    if (carrinho == null) {
	        // Carrinho ainda não existe na session. Cria um novo.
	        carrinho = new Carrinho();
	    }	    	    

	    if (sku != null) {
	        // Recebeu um sku. Adiciona ou atualiza o carrinho.

	        // Alguém pode passar ?sku=a, maldosamente, por checamos
	        //  a exception NumberFormatException.
	        try {
	            if (del != null && del.equals("1")) {
	                carrinho.remove(Long.parseLong(sku));
	            } else {
	                carrinho.adiciona(Long.parseLong(sku), 1);
	            }
	            // Atualiza (ou cria, se não existir) o carrinho na session.
	            session.setAttribute("carrinhocompras", carrinho);
	        } catch (NumberFormatException nfe) {
	            //out.println("<p>Tentou zuar, né?</p>");
	        }
	    }
	    
        // Variáveis para cálculo e apresentação dos valores dos itens e total.
        Double totalCarrinho = 0.0;
        String totalCarrinhoFormatado;
        
        Iterator<Long> keySetIterator = carrinho.getLista().keySet().iterator();
        List<ProdutoCarrinho> listaProdutos = new ArrayList<>();
        while (keySetIterator.hasNext()) {
            Long skuItem = keySetIterator.next();
            ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
            Produto produto = repositoryProduto.getOne(skuItem);
            produtoCarrinho.setProduto(produto);
            produtoCarrinho.setQuantidade(carrinho.getLista().get(skuItem));
            produtoCarrinho.setPrecoTotal(produto.getPreco() * produtoCarrinho.getQuantidade());
            totalCarrinho += produtoCarrinho.getPrecoTotal();
            listaProdutos.add(produtoCarrinho);
        }
        totalCarrinhoFormatado = String.format("%1$,.2f", totalCarrinho);
	
		mv.setViewName("carrinho");
		mv.addObject("carrinho", carrinho);
		mv.addObject("listaProdutos", listaProdutos);
		mv.addObject("totalCarrinhoFormatado", totalCarrinhoFormatado);
		mv.addObject("messages", messages);
		return mv;
	}
	
	@PostMapping("/carrinho")
	public ModelAndView atualizarCarrinho(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
	    // O carrinho contém apenas o SKU e sua quantidade.
	    Carrinho carrinho = (Carrinho) session.getAttribute("carrinhocompras");
	    if (carrinho == null) {
	        // Carrinho ainda não existe na session. Cria um novo.
	        carrinho = new Carrinho();
	    }
		
		// Recupera os valores do request
		String alt = request.getParameter("alt");
		String[] skualt = request.getParameterValues("skualt");
        String[] qtde = request.getParameterValues("qtde");

	    if (alt != null) {
	        // Clicou no botão Alterar!
	        for (int i = 0; i < skualt.length; i++) {
	            Long isku = Long.parseLong(skualt[i]);
	            Integer iqtd = Integer.parseInt(qtde[i]);

	            if (iqtd < 1) {
	                // A quantidade foi atualizada para 0, 
	                // portanto removemos o produto do carrinho.
	                carrinho.remove(isku);
	            } else {
	                // Quantidade do produto foi alterada.
	                // Verifica se tem saldo em estoque.
	                Produto produto = repositoryProduto.getOne(isku);
	                if (produto.getQuantidade() < iqtd) {
	                	messages.add("Estoque insuficiente para o produto <strong>" + produto.getNome() + "</strong>!");
	                    // Atualiza o produto no carrinho com a quantidade que tem em estoque.
	                    carrinho.atualiza(isku, produto.getQuantidade());
	                } else {
	                    carrinho.atualiza(isku, iqtd);
	                }
	            }
	        }
	    }
	    
	    mv.setViewName("redirect:/carrinho");		
		return mv;
	}
}
