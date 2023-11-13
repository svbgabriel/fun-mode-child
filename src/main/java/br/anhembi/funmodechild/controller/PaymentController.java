package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.common.Cart;
import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final CartService cartService;

    public PaymentController(UserRepository userRepository,
                             PaymentService paymentService,
                             CartService cartService) {
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.cartService = cartService;
    }

    @GetMapping("/payment")
    public ModelAndView pagamento(HttpSession session, HttpServletRequest request) {
        // Recupera os dados do usuário
        Principal user = request.getUserPrincipal();
        Customer customer = userRepository.findByEmail(user.getName());

        ModelAndView mv = new ModelAndView();

        // O carrinho contém apenas o SKU e sua quantidade.
        Cart cart = cartService.getCart(session);

        // Pega os valores do carrinho
        var cartInfo = cartService.getCartInfo(cart);

        mv.addObject("customer", customer);
        mv.addObject("cart", cart);
        mv.addObject("products", cartInfo.products());
        mv.addObject("totalCartPrice", cartInfo.formatTotalPrice());
        mv.setViewName("payment");
        return mv;
    }

    @PostMapping("/payment")
    public String fazerPagamento(HttpSession session,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        var carrinho = cartService.getCart(session);

        // Recupera os dados do usuário
        Principal user = request.getUserPrincipal();
        Customer customer = userRepository.findByEmail(user.getName());

        // Pedido
        var paymentResponse = paymentService.makePayment(session, request, customer, carrinho);

        redirectAttributes
            .addFlashAttribute("errors", paymentResponse.errors())
            .addFlashAttribute("orderId", paymentResponse.paymentData().getId());
        return "redirect:/payment";
    }
}
