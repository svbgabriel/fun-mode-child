package br.anhembi.funmodechild.controllers;

import java.security.Principal;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.anhembi.funmodechild.models.Carrinho;
import br.anhembi.funmodechild.models.Pagamento;
import br.anhembi.funmodechild.models.Produto;
import br.anhembi.funmodechild.models.ProdutoCarrinho;
import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryProduto;
import br.anhembi.funmodechild.repositories.RepositoryUsuario;
import br.anhembi.funmodechild.services.ServicePagamento;

@Controller
@Scope("session")
public class PagamentoController {

	@Autowired
	RepositoryUsuario repositoryUsuario;

	@Autowired
	RepositoryProduto repositoryProduto;

	@Autowired
	ServicePagamento servicePagamento;

	@GetMapping("/pagamento")
	public ModelAndView pagamento(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		// Recupera os dados do usuário
		Principal user = request.getUserPrincipal();
		Usuario usuario = repositoryUsuario.findByEmail(user.getName());

	    // O carrinho contém apenas o SKU e sua quantidade.
	    Carrinho carrinho = (Carrinho) session.getAttribute("carrinhocompras");
	    if (carrinho == null) {
	        // Carrinho ainda não existe na session. Cria um novo.
	        carrinho = new Carrinho();
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

		mv.addObject("usuario", usuario);
		mv.addObject("carrinho", carrinho);
		mv.addObject("listaProdutos", listaProdutos);
		mv.addObject("totalCarrinhoFormatado", totalCarrinhoFormatado);
		mv.addObject("erroSalvar", "");
		mv.addObject("idPedido", 0);
		mv.setViewName("pagamento");
		return mv;
	}
	
	@PostMapping("/pagamento")
	public String fazerPagamento(HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Carrinho carrinho = (Carrinho) session.getAttribute("carrinhocompras");
	    String erroSalvar = "";
	    Long idPedido = -1L;
	    
	    // Recupera os dados do usuário
 		Principal user = request.getUserPrincipal();
 		Usuario usuario = repositoryUsuario.findByEmail(user.getName());
	    
	    // Pedido
	    if(request.getParameter("salvar") != null && carrinho != null) {
	        // Clicou em Salvar Pedido.
	        
	        if(request.getParameter("numerocartao") == null) {
	            erroSalvar += "<li>Informe o número do cartão</li>";
	        }
	        if(request.getParameter("nomecartao") == null) {
	            erroSalvar += "<li>Informe o nome que está no cartão</li>";
	        }
	        if(request.getParameter("validademes") == null) {
	            erroSalvar += "<li>Informe o mês de validade do cartão</li>";
	        } 
	        if(request.getParameter("validadeano") == null) {
	            erroSalvar += "<li>Informe o ano de validade do cartão</li>";
	        } 
	        if(request.getParameter("codigo") == null) {
	            erroSalvar += "<li>Informe o código de verificação do cartão</li>";
	        } 
	        if(request.getParameter("parcelas") == null) {
	            erroSalvar += "<li>Informe o número de parcelas</li>";
	        }
	        
	        Pagamento pagamentoResposta = null;
	        if(erroSalvar.isEmpty()) {
	            if(!carrinho.getLista().isEmpty()) {
	    			// Salva os dados de pagamento
	    			Pagamento pagamento = new Pagamento();
	    			pagamento.setNumeroCartao(request.getParameter("numerocartao"));
	    			pagamento.setNomeCartao(request.getParameter("nomecartao"));
	    			pagamento.setValidadeMes(Integer.parseInt(request.getParameter("validademes")));
	    			pagamento.setValidadeAno(Integer.parseInt(request.getParameter("validadeano")));
	    			pagamento.setCodigo(Integer.parseInt(request.getParameter("codigo")));
	    			pagamento.setParcelas(Integer.parseInt(request.getParameter("parcelas")));
	    			pagamento.setAtivo(true);
	    			
	    			pagamentoResposta = servicePagamento.salvar(carrinho, usuario, pagamento);
	                // Esvazia o carrinho.
	                session.setAttribute("carrinhocompras", null);
	            }
	        }
	        
	        idPedido = pagamentoResposta != null ? pagamentoResposta.getPedido().getId() : -1;
	    }

	    // TODO: Corrigir o feedback do pedido do usuário
		redirectAttributes.addFlashAttribute("erroSalvar", erroSalvar);
		redirectAttributes.addFlashAttribute("idPedido", idPedido);
		return "redirect:/pagamento";
	}
}
