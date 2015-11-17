package Dao;

import Model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaDao {

    private final Connection con;

    public CategoriaDao() {
        con = new DataSource("funchildmode").getCon();
    }

    public ArrayList consultar(int chave) {
        ArrayList res = new ArrayList();
        String sql = "SELECT * FROM categoria WHERE categoria_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, chave);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Categoria obj = new Categoria();
                obj.setCategoria_id(rs.getInt(1));
                obj.setNome(rs.getString(2));
                res.add(obj);
            }
        } catch (SQLException e) {
            System.err.println("Produto nao encontrado");
            System.err.println(e);
            System.exit(1);
        }
        return res;
    }
    
    public ArrayList consultar() {
        ArrayList res = new ArrayList();
        String sql = "SELECT * FROM categoria";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Categoria obj = new Categoria();
                obj.setCategoria_id(rs.getInt(1));
                obj.setNome(rs.getString(2));
                res.add(obj);
            }
        } catch (SQLException e) {
            System.err.println("Produtos nao encontrado");
            System.err.println(e);
            System.exit(1);
        }
        return res;
    }    

    public void inserir(Categoria obj) {
        String sql = "INSERT INTO categoria (categoria_id, nome) "
                + "VALUES (?, ?)";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, obj.getCategoria_id());
            stm.setString(2, obj.getNome());
            stm.execute();
        } catch (SQLException e) {
            System.err.println("Falha na insercao");
            System.err.println(e);
            System.exit(1);
        }
        System.out.println("Insercao Efetuada");
    }

    public void atualizar(Categoria obj) {
        String sql = "UPDATE categoria SET nome = ? WHERE categoria_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, obj.getCategoria_id());
            stm.setString(2, obj.getNome());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na atualização.");
            System.err.println(ex);
            System.exit(1);
        }
        System.out.println("Atualização Efetuada!");
    }

    public void remover(Categoria obj) {
        String sql = "DELETE FROM categoria WHERE categoria_id = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, obj.getCategoria_id());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na remoção.");
            System.err.println(ex);
            System.exit(1);
        }
        System.out.println("Remoção Efetuada!");
    }
}
