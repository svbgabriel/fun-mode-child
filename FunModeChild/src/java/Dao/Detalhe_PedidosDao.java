package Dao;

import Model.Detalhe_Pedidos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Detalhe_PedidosDao {

    private final Connection con;

    public Detalhe_PedidosDao() {
        con = new DataSource("funchildmode").getCon();
    }

    public ArrayList consultar(String chave) {
        ArrayList res = new ArrayList();
        String sql = "SELECT * FROM detalhe_pedidos WHERE pedido_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, chave);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Detalhe_Pedidos obj = new Detalhe_Pedidos();
                obj.setId_DetalhePedido(rs.getInt(1));
                obj.setPedido_id(rs.getInt(2));
                obj.setSku(rs.getInt(3));
                obj.setPreco_item(rs.getDouble(4));
                obj.setQuantidade(rs.getInt(5));
                res.add(obj);
            }
        } catch (SQLException e) {
            System.err.println("Detalhe do pedido nao encontrado");
            System.err.println(e);
            System.exit(1);
        }
        return res;
    }

    public void inserir(Detalhe_Pedidos obj) {
        String sql = "INSERT INTO detalhe_pedidos (id_DetalhePedido, pedido_id, "
                + "sku, preco_item, quantidade) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, obj.getId_DetalhePedido());
            stm.setInt(2, obj.getPedido_id());
            stm.setInt(3, obj.getSku());
            stm.setDouble(4, obj.getPreco_item());
            stm.setInt(5, obj.getQuantidade());
            stm.execute();
        } catch (SQLException e) {
            System.err.println("Falha na insercao");
            System.err.println(e);
            System.exit(1);
        }
        System.out.println("Insercao Efetuada");
    }

    public void atualizar(Detalhe_Pedidos obj) {
        String sql = "UPDATE categoria SET preco_total = ? WHERE pedido_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setDouble(1, obj.getPreco_item());
            stm.setInt(2, obj.getPedido_id());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na atualização.");
            System.err.println(ex);
            System.exit(1);
        }
        System.out.println("Atualização Efetuada!");
    }

    public void remover(Detalhe_Pedidos obj) {
        String sql = "DELETE FROM detalhe_pedidos WHERE pedido_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, obj.getPedido_id());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na remoção.");
            System.err.println(ex);
            System.exit(1);
        }
        System.out.println("Remoção Efetuada!");
    }
    
    public void remover(int id) {
        String sql = "DELETE FROM detalhe_pedidos WHERE pedido_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na remoção.");
            System.err.println(ex);
            System.exit(1);
        }
        System.out.println("Remoção Efetuada!");
    }
}
