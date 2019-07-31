package Dao;

import Model.Estoque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EstoqueDao {

    private final Connection con;

    public EstoqueDao() {
        con = new DataSource("funchildmode").getCon();
    }

    public ArrayList consultar(String chave) {
        ArrayList res = new ArrayList();
        String sql = "SELECT * FROM estoque";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, chave);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Estoque obj = new Estoque();
                obj.setSku(rs.getInt(1));
                obj.setQuantidadeEstoque(rs.getInt(2));
                res.add(obj);
            }
        } catch (SQLException e) {
            System.err.println("Produto nao encontrado");
            System.err.println(e);
            System.exit(1);
        }
        return res;
    }

    public Estoque consultarQtdEstoque(int sku) {
        Estoque obj = new Estoque();
        String sql = "SELECT * FROM estoque WHERE sku = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, sku);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {                
                obj.setSku(rs.getInt(1));
                obj.setQuantidadeEstoque(rs.getInt(2));
            }
        } catch (SQLException e) {
            System.err.println("Produto nao encontrado");
            System.err.println(e);
            obj = null;
        }
        return obj;
    }
    
    public void inserir(Estoque obj) {
        String sql = "INSERT INTO estoque (sku, quantidadeEstoque) "
                + "VALUES (?, ?)";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, obj.getSku());
            stm.setInt(2, obj.getQuantidadeEstoque());
            stm.execute();
        } catch (SQLException e) {
            System.err.println("Falha na insercao");
            System.err.println(e);
            System.exit(1);
        }
        System.out.println("Insercao Efetuada");
    }

    public void atualizar(Estoque obj) {
        String sql = "UPDATE estoque SET quantidadeEstoque = ? WHERE sku = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setDouble(1, obj.getQuantidadeEstoque());
            stm.setInt(2, obj.getSku());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na atualização.");
            System.err.println(ex);
            System.exit(1);
        }
        System.out.println("Atualização Efetuada!");
    }

    public void remover(Estoque obj) {
        String sql = "DELETE FROM produtos WHERE sku = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, obj.getSku());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na remoção.");
            System.err.println(ex);
            System.exit(1);
        }
        System.out.println("Remoção Efetuada!");
    }
}
