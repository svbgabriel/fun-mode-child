package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Product;
import br.anhembi.funmodechild.model.Carrinho;
import br.anhembi.funmodechild.model.response.CartInfoRelation;
import br.anhembi.funmodechild.model.request.CartUpdateRequest;
import br.anhembi.funmodechild.model.response.CartInfoResponse;
import br.anhembi.funmodechild.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static br.anhembi.funmodechild.common.Constants.SHOPPING_CART;

@Service
public class CartService {

    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public CartInfoResponse getCartInfo(Carrinho cart) {
        // Variáveis para cálculo e apresentação dos valores dos itens e total.
        List<CartInfoRelation> productRelations = cart.getLista().keySet()
            .stream()
            .map(sku -> {
                Product product = productRepository.findBySku(sku).orElseThrow();
                return new CartInfoRelation(product, cart.getLista().get(sku));
            })
            .toList();

        double totalCartPrice = productRelations
            .stream()
            .mapToDouble(CartInfoRelation::calculateTotalPrice)
            .sum();

        return new CartInfoResponse(productRelations, totalCartPrice);
    }

    public Carrinho getCart(HttpSession session) {
        // O carrinho contém apenas o SKU e sua quantidade.
        if (session.getAttribute(SHOPPING_CART) != null) {
            return (Carrinho) session.getAttribute(SHOPPING_CART);
        } else {
            // Se não existir o carrinho na session, cria um novo
            var cart = new Carrinho();
            session.setAttribute(SHOPPING_CART, cart);
            return cart;
        }
    }

    public List<String> updateCart(Carrinho carrinho, HttpServletRequest request) {
        List<String> messages = new ArrayList<>();

        var productsToUpdate = getCartUpdateInfo(request);

        for (var productToUpdate : productsToUpdate) {
            if (productToUpdate.quantity() < 1) {
                // A quantidade foi atualizada para 0, portanto removemos o produto do carrinho.
                carrinho.remove(productToUpdate.sku());
            } else {
                // Quantidade do produto foi alterada. Verifica se tem saldo em estoque.
                Product product = productRepository.findBySku(productToUpdate.sku()).orElseThrow();
                if (product.getQuantidade() < productToUpdate.quantity()) {
                    messages.add("Estoque insuficiente para o produto <strong>" + product.getNome() + "</strong>!");
                    // Atualiza o produto no carrinho com a quantidade que tem em estoque.
                    carrinho.update(productToUpdate.sku(), product.getQuantidade());
                } else {
                    carrinho.update(productToUpdate.sku(), productToUpdate.quantity());
                }
            }
        }

        return messages;
    }

    private List<CartUpdateRequest> getCartUpdateInfo(HttpServletRequest request) {
        var skuList = request.getParameterValues("skualt");
        var quantityList = request.getParameterValues("qtde");

        var productListSize = skuList.length;

        var products = new ArrayList<CartUpdateRequest>();
        for (int i = 0; i < productListSize; i++) {
            var sku = Long.parseLong(skuList[i]);
            var quantity = Integer.parseInt(quantityList[i]);

            var product = new CartUpdateRequest(sku, quantity);
            products.add(product);
        }

        return products;
    }
}
