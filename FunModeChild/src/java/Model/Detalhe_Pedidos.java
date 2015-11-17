package Model;

public class Detalhe_Pedidos {

    private int id_DetalhePedido;
    private int pedido_id;
    private int sku;
    private double preco_item;
    private int quantidade;

    public int getId_DetalhePedido() {
        return id_DetalhePedido;
    }

    public void setId_DetalhePedido(int id_DetalhePedido) {
        this.id_DetalhePedido = id_DetalhePedido;
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public double getPreco_item() {
        return preco_item;
    }

    public void setPreco_item(double preco_item) {
        this.preco_item = preco_item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
