package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.common.Cart;
import br.anhembi.funmodechild.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("session")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carrinho")
    public ModelAndView carrinho(HttpSession session, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        // Recupera os valores do request
        Long sku;
        if (request.getParameter("sku") != null) {
            try {
                sku = Long.parseLong(request.getParameter("sku"));
            } catch (NumberFormatException nfe) {
                sku = null;
            }
        } else {
            sku = null;
        }
        boolean del = request.getParameter("del") != null && request.getParameter("del").equals("1");

        // O carrinho contém apenas o SKU e sua quantidade.
        Cart cart = cartService.getCart(session);

        if (sku != null) {
            // Recebeu um sku. Adiciona ou atualiza o carrinho.
            if (del) {
                cart.remove(sku);
            } else {
                cart.add(sku, 1);
            }
        }

        var cartInfo = cartService.getCartInfo(cart);

        mv.setViewName("carrinho");
        mv.addObject("carrinho", cart);
        mv.addObject("listaProdutos", cartInfo.products());
        mv.addObject("totalCarrinhoFormatado", cartInfo.formatTotalPrice());
        return mv;
    }

    @PostMapping("/carrinho")
    public String atualizarCarrinho(HttpSession session,
                                    HttpServletRequest request,
                                    RedirectAttributes redirectAttributes) {
        // O carrinho contém apenas o SKU e sua quantidade.
        Cart cart = cartService.getCart(session);

        // Recupera os valores do request
        boolean alt = request.getParameter("alt") != null;

        List<String> errorMessages;
        if (alt) {
            // Clicou no botão Alterar!
            errorMessages = cartService.updateCart(cart, request);
        } else {
            errorMessages = new ArrayList<>();
        }

        redirectAttributes.addFlashAttribute("messages", errorMessages);
        return "redirect:/carrinho";
    }
}
