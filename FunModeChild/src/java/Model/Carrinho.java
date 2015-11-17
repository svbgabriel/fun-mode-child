package Model;

import java.util.AbstractMap;
import java.util.HashMap;

/**
 * Classe para manter uma lista de SKUs e suas quantidades.
 * 
 * Esta classe tem um HashMap com apenas o SKU do produto e a quantidade, 
 * pois deve ser ser mantida na session, portanto deve ser "leve".
 * 
 * HashMap = coleção de pares [chave, valor].
 * Em chave colocamos o SKU e, em valor, a quantidade.
 * 
 */
public class Carrinho {
    
    private final AbstractMap<Integer, Integer> lista;
    
    public Carrinho() {
        this.lista = new HashMap<Integer, Integer>();
    }
    
    public void adiciona(int sku, int quantidade) {
        if(lista.containsKey(sku)) {
            int q = lista.get(sku);
            lista.replace(sku, q + quantidade);
        } else {
            lista.put(sku, quantidade);
        }
    }
    
    public void remove(int sku) {
        if(lista.containsKey(sku)) {
            lista.remove(sku);
        }
    }
    
    public void atualiza(int sku, int quantidade) {
        lista.replace(sku, quantidade);
    }
    
    public AbstractMap<Integer, Integer> getLista() {
        return this.lista;
    }
}
