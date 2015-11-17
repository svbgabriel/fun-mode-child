package Dao;

import Model.Detalhe_Pedidos;
import Model.Pedidos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class PedidosDao {

    private final Connection con;

    public PedidosDao() {
        con = new DataSource("funchildmode").getCon();
    }

    public ArrayList consultar(String chave) {
        ArrayList res = new ArrayList();
        String sql = "SELECT * FROM pedidos WHERE pedido_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, chave);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Pedidos obj = new Pedidos();
                obj.setPedido_id(rs.getInt(1));
                obj.setData_pedido(rs.getDate(2));
                obj.setUsuario(rs.getString(3));
                obj.setPreco_total(rs.getDouble(4));
                res.add(obj);
            }
        } catch (SQLException e) {
            System.err.println("Produto nao encontrado");
            System.err.println(e);
            System.exit(1);
        }
        return res;
    }
    
    public ArrayList consultarUsuario(String chave) {
        ArrayList res = new ArrayList();
        String sql = "SELECT * FROM pedidos WHERE usuario = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, chave);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Pedidos obj = new Pedidos();
                obj.setPedido_id(rs.getInt(1));
                obj.setData_pedido(rs.getDate(2));
                obj.setUsuario(rs.getString(3));
                obj.setPreco_total(rs.getDouble(4));
                res.add(obj);
            }
        } catch (SQLException e) {
            System.err.println("Produto nao encontrado");
            System.err.println(e);
            System.exit(1);
        }
        return res;
    }
    
    public Pedidos consultarUsuarioPedido(String user,int id) {
        Pedidos obj = new Pedidos();
        String sql = "SELECT * FROM pedidos WHERE usuario = ? "
                + "AND pedido_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, user);
            stm.setInt(2, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {                
                obj.setPedido_id(rs.getInt(1));
                obj.setData_pedido(rs.getDate(2));
                obj.setUsuario(rs.getString(3));
                obj.setPreco_total(rs.getDouble(4));
            }
        } catch (SQLException e) {
            System.err.println("Produto nao encontrado");
            System.err.println(e);
        }
        return obj;
    }

    public void inserir(Pedidos obj) {
        String sql = "INSERT INTO pedidos (data_pedido, usuario, preco_total) "
                + "VALUES (?, ?, ?)";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setDate(1, obj.getData_pedido());
            stm.setString(2, obj.getUsuario());
            stm.setDouble(3, obj.getPreco_total());
            stm.execute();
        } catch (SQLException e) {
            System.err.println("Falha na insercao");
            System.err.println(e);
            System.exit(1);
        }
        System.out.println("Insercao Efetuada");
    }

    public void atualizar(Pedidos obj) {
        String sql = "UPDATE categoria SET preco_total = ? WHERE pedido_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setDouble(1, obj.getPreco_total());
            stm.setInt(2, obj.getPedido_id());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na atualização.");
            System.err.println(ex);
            System.exit(1);
        }
        System.out.println("Atualização Efetuada!");
    }

    public void remover(Pedidos obj) {
        String sql = "DELETE FROM pedidos WHERE pedido_id = ?";
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
    
    
    public int inserirComDetalhes(Pedidos pedido) {
        int result = -1;
        String sql = "INSERT INTO pedidos (data_pedido, usuario, preco_total) "
                + "VALUES (SYSDATE(), ?, ?)";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, pedido.getUsuario());
            stm.setDouble(2, pedido.getPreco_total());
            stm.execute();
            
            sql = "SELECT LAST_INSERT_ID()";
            stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                result = rs.getInt(1);
                
                for(Detalhe_Pedidos detalhe : pedido.getDetalhes()) {
                    sql = "INSERT INTO detalhe_pedidos (" +
                            "pedido_id, " +
                            "sku, " +
                            "preco_item, " +
                            "quantidade" +
                          ") VALUES (?, ?, ?, ?)";
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, result);
                    stm.setInt(2, detalhe.getSku());
                    stm.setDouble(3, detalhe.getPreco_item());
                    stm.setInt(4, detalhe.getQuantidade());
                    stm.execute();
                    
                    // Diminui o estoque do produto
                    sql = "UPDATE estoque SET " +
                            "quantidadeEstoque = quantidadeEstoque - ? " +
                            "WHERE sku = ?";
                    stm = con.prepareStatement(sql);
                    stm.setInt(1, detalhe.getQuantidade());
                    stm.setInt(2, detalhe.getSku());
                    stm.execute();
                }
            }
        } catch (SQLException e) {
            System.err.println("Falha na insercao do pedido.");
            System.err.println(e);
            result = -1;
        }

        return result;
    }
}
