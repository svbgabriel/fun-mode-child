package br.anhembi.funmodechild.controller;


import br.anhembi.funmodechild.model.Carrinho;
import br.anhembi.funmodechild.model.Pagamento;
import br.anhembi.funmodechild.model.Produto;
import br.anhembi.funmodechild.model.ProdutoCarrinho;
import br.anhembi.funmodechild.model.Usuario;
import br.anhembi.funmodechild.repository.RepositoryProduto;
import br.anhembi.funmodechild.repository.RepositoryUsuario;
import br.anhembi.funmodechild.service.ServicePagamento;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@Scope("session")
public class PagamentoController {

    private final RepositoryUsuario repositoryUsuario;
    private final RepositoryProduto repositoryProduto;
    private final ServicePagamento servicePagamento;

    public PagamentoController(RepositoryUsuario repositoryUsuario, RepositoryProduto repositoryProduto, ServicePagamento servicePagamento) {
        this.repositoryUsuario = repositoryUsuario;
        this.repositoryProduto = repositoryProduto;
        this.servicePagamento = servicePagamento;
    }

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
        double totalCarrinho = 0.0;
        String totalCarrinhoFormatado;

        Iterator<Long> keySetIterator = carrinho.getLista().keySet().iterator();
        List<ProdutoCarrinho> listaProdutos = new ArrayList<>();
        while (keySetIterator.hasNext()) {
            Long skuItem = keySetIterator.next();
            ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
            Produto produto = repositoryProduto.findBySku(skuItem).orElseThrow();
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
        mv.setViewName("pagamento");
        return mv;
    }

    @PostMapping("/pagamento")
    public String fazerPagamento(HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinhocompras");
        List<String> erroSalvar = new ArrayList<>();
        long idPedido = -1L;

        // Recupera os dados do usuário
        Principal user = request.getUserPrincipal();
        Usuario usuario = repositoryUsuario.findByEmail(user.getName());

        // Pedido
        if (request.getParameter("salvar") != null && carrinho != null) {
            // Clicou em Salvar Pedido.

            if (request.getParameter("numerocartao") == null) {
                erroSalvar.add("Informe o número do cartão");
            }
            if (request.getParameter("nomecartao") == null) {
                erroSalvar.add("Informe o nome que está no cartão");
            }
            if (request.getParameter("validademes") == null) {
                erroSalvar.add("Informe o mês de validade do cartão");
            }
            if (request.getParameter("validadeano") == null) {
                erroSalvar.add("Informe o ano de validade do cartão");
            }
            if (request.getParameter("codigo") == null) {
                erroSalvar.add("Informe o código de verificação do cartão");
            }
            if (request.getParameter("parcelas") == null) {
                erroSalvar.add("Informe o número de parcelas");
            }

            Pagamento pagamentoResposta = null;
            if (erroSalvar.isEmpty() && !carrinho.getLista().isEmpty()) {
                // Salva os dados de pagamento
                Pagamento pagamento = new Pagamento();
                pagamento.setNumeroCartao(request.getParameter("numerocartao"));
                pagamento.setNomeCartao(request.getParameter("nomecartao"));
                pagamento.setValidadeMes(Integer.parseInt(request.getParameter("validademes")));
                pagamento.setValidadeAno(Integer.parseInt(request.getParameter("validadeano")));
                pagamento.setCodigo(Integer.parseInt(request.getParameter("codigo")));
                pagamento.setParcelas(Integer.parseInt(request.getParameter("parcelas")));

                pagamentoResposta = servicePagamento.salvar(carrinho, usuario, pagamento);
                // Esvazia o carrinho.
                session.setAttribute("carrinhocompras", null);
            }

            idPedido = pagamentoResposta != null ? pagamentoResposta.getPedido().getId() : -1;
        }

        redirectAttributes.addFlashAttribute("erroSalvar", erroSalvar);
        redirectAttributes.addFlashAttribute("idPedido", idPedido);
        return "redirect:/pagamento";
    }
}
