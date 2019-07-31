package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Pedidos {

    private int pedido_id;
    private Date data_pedido;
    private String usuario;
    private double preco_total;
    private List<Detalhe_Pedidos> detalhes;

    public Pedidos() {
        // Inicializa a lista de itens do Pedido assim 
        // que a instância da classe for criada.
        this.detalhes = new ArrayList<Detalhe_Pedidos>();
    }
    
    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }

    public Date getData_pedido() {
        return data_pedido;
    }

    public void setData_pedido(Date data_pedido) {
        this.data_pedido = data_pedido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public double getPreco_total() {
        return preco_total;
    }

    public void setPreco_total(double preco_total) {
        this.preco_total = preco_total;
    }

    /**
     * @return the detalhes
     */
    public List<Detalhe_Pedidos> getDetalhes() {
        return detalhes;
    }

    /**
     * @param detalhes the detalhes to set
     */
    public void setDetalhes(List<Detalhe_Pedidos> detalhes) {
        this.detalhes = detalhes;
    }
}
