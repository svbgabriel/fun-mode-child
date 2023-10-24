package br.anhembi.funmodechild.models;

import java.util.AbstractMap;
import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Classe para manter uma lista de SKUs e suas quantidades.
 * <p>
 * Esta classe tem um HashMap com apenas o SKU do produto e a quantidade,
 * pois deve ser mantida na session, portanto deve ser "leve".
 * <p>
 * HashMap = coleção de pares [chave, valor].
 * Em chave colocamos o SKU e, em valor, a quantidade.
 */

@Component
@Scope("session")
@Data
public class Carrinho {

    private final AbstractMap<Long, Integer> lista;

    public Carrinho() {
        this.lista = new HashMap<>();
    }

    public void adiciona(Long sku, Integer quantidade) {
        if (lista.containsKey(sku)) {
            Integer q = lista.get(sku);
            lista.replace(sku, q + quantidade);
        } else {
            lista.put(sku, quantidade);
        }
    }

    public void remove(Long sku) {
        lista.remove(sku);
    }

    public void atualiza(Long sku, Integer quantidade) {
        lista.replace(sku, quantidade);
    }
}
