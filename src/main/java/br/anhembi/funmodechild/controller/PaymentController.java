package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.Carrinho;
import br.anhembi.funmodechild.entity.Usuario;
import br.anhembi.funmodechild.repository.RepositoryUsuario;
import br.anhembi.funmodechild.service.CartService;
import br.anhembi.funmodechild.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Scope("session")
public class PaymentController {

    private final RepositoryUsuario repositoryUsuario;
    private final PaymentService paymentService;
    private final CartService cartService;

    public PaymentController(RepositoryUsuario repositoryUsuario,
                             PaymentService paymentService,
                             CartService cartService) {
        this.repositoryUsuario = repositoryUsuario;
        this.paymentService = paymentService;
        this.cartService = cartService;
    }

    @GetMapping("/pagamento")
    public ModelAndView pagamento(HttpSession session, HttpServletRequest request) {
        // Recupera os dados do usuário
        Principal user = request.getUserPrincipal();
        Usuario usuario = repositoryUsuario.findByEmail(user.getName());

        ModelAndView mv = new ModelAndView();

        // O carrinho contém apenas o SKU e sua quantidade.
        Carrinho carrinho = cartService.getCart(session);

        // Pega os valores do carrinho
        var cartInfo = cartService.getCartInfo(carrinho);

        mv.addObject("usuario", usuario);
        mv.addObject("carrinho", carrinho);
        mv.addObject("listaProdutos", cartInfo.products());
        mv.addObject("totalCarrinhoFormatado", cartInfo.formatTotalPrice());
        mv.setViewName("pagamento");
        return mv;
    }

    @PostMapping("/pagamento")
    public String fazerPagamento(HttpSession session,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        var carrinho = cartService.getCart(session);

        // Recupera os dados do usuário
        Principal user = request.getUserPrincipal();
        Usuario usuario = repositoryUsuario.findByEmail(user.getName());

        // Pedido
        var paymentResponse = paymentService.makePayment(session, request, usuario, carrinho);

        redirectAttributes.addFlashAttribute("erroSalvar", paymentResponse.errors());
        redirectAttributes.addFlashAttribute("idPedido", paymentResponse.paymentData().getId());
        return "redirect:/pagamento";
    }
}
