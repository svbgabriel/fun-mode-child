package Dao;

import Model.Produto;
import Model.ProdutoEstoque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDao {

    private final Connection con;

    public ProdutoDao() {
        con = new DataSource("funchildmode").getCon();
    }

    public Produto consultarSku(int sku) {
        Produto obj = new Produto();
        String sql = "SELECT * FROM produtos WHERE sku = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, sku);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                obj.setSku(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setDescricao(rs.getString(3));
                obj.setPreco(rs.getDouble(4));
                obj.setPromovido(rs.getBoolean(5));
                obj.setCategoria_id(rs.getInt(6));
                obj.setRefe(rs.getString(7));
                obj.setRefeBig(rs.getString(8));
                obj.setDt_cadastrado(rs.getDate(9));
            }
        } catch (SQLException e) {
            System.err.println("Produto nao encontrado");
            System.err.println(e);
            obj = null;
        }
        return obj;
    }
    
    public ProdutoEstoque consultarSkuEsProduto(int sku) {
        ProdutoEstoque obj = new ProdutoEstoque();
        String sql = "SELECT A.*,B.quantidadeEstoque FROM produtos A "
                + "INNER JOIN estoque B ON A.sku = B.sku"
                + " WHERE A.sku = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, sku);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                obj.setSku(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setDescricao(rs.getString(3));
                obj.setPreco(rs.getDouble(4));
                obj.setPromovido(rs.getBoolean(5));
                obj.setCategoria_id(rs.getInt(6));
                obj.setRefe(rs.getString(7));
                obj.setRefeBig(rs.getString(8));
                obj.setDt_cadastrado(rs.getDate(9));
                obj.setQtdEstoque(rs.getInt(10));
            }
        } catch (SQLException e) {
            System.err.println("Produto nao encontrado");
            System.err.println(e);
            obj = null;
        }
        return obj;
    }

    public ArrayList consultarCategoriaQtdEstoque (int categoria_id, int limite) {
        ArrayList<ProdutoEstoque> res = new ArrayList<>();
        
        String sql = "SELECT A.*,B.quantidadeEstoque FROM produtos A "
                + "INNER JOIN estoque B ON A.sku = B.sku";
        
        if (categoria_id != 0) {
            sql = sql + " WHERE categoria_id = ?";
        }
        if(limite > 0) {
            sql = sql + " LIMIT ?";
        }
        
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            
            if (categoria_id != 0) {
                stm.setInt(1, categoria_id);
            }
            if(limite > 0) {
                if (categoria_id != 0) {
                    stm.setInt(2, limite);
                } else {
                    stm.setInt(1, limite);
                }
            }
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProdutoEstoque obj = new ProdutoEstoque();
                obj.setSku(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setDescricao(rs.getString(3));
                obj.setPreco(rs.getDouble(4));
                obj.setPromovido(rs.getBoolean(5));
                obj.setCategoria_id(rs.getInt(6));
                obj.setRefe(rs.getString(7));
                obj.setRefeBig(rs.getString(8));
                obj.setDt_cadastrado(rs.getDate(9));
                obj.setQtdEstoque(rs.getInt(10));
                res.add(obj);
            }
        } catch (SQLException e) {
            System.err.println("Produto nao encontrado");
            System.err.println(e);
            //System.exit(1);
        }
        return res;
    }
    
    public ArrayList consultarCategoria(int categoria_id) {
        ArrayList res = new ArrayList();
        if (categoria_id == 0) {
            String sql = "SELECT * FROM produtos";
            try {
                PreparedStatement stm = con.prepareStatement(sql);
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    Produto obj = new Produto();
                    obj.setSku(rs.getInt(1));
                    obj.setNome(rs.getString(2));
                    obj.setDescricao(rs.getString(3));
                    obj.setPreco(rs.getDouble(4));
                    obj.setPromovido(rs.getBoolean(5));
                    obj.setCategoria_id(rs.getInt(6));
                    obj.setRefe(rs.getString(7));
                    obj.setRefeBig(rs.getString(8));
                    obj.setDt_cadastrado(rs.getDate(9));
                    res.add(obj);
                }
            } catch (SQLException e) {
                System.err.println("Produto nao encontrado");
                System.err.println(e);
                //System.exit(1);
            }
            return res;
        } else {
            String sql = "SELECT * FROM produtos WHERE categoria_id = ?";
            try {
                PreparedStatement stm = con.prepareStatement(sql);
                stm.setInt(1, categoria_id);
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    Produto obj = new Produto();
                    obj.setSku(rs.getInt(1));
                    obj.setNome(rs.getString(2));
                    obj.setDescricao(rs.getString(3));
                    obj.setPreco(rs.getDouble(4));
                    obj.setPromovido(rs.getBoolean(5));
                    obj.setCategoria_id(rs.getInt(6));
                    obj.setRefe(rs.getString(7));
                    obj.setRefeBig(rs.getString(8));
                    obj.setDt_cadastrado(rs.getDate(9));
                    res.add(obj);
                }
            } catch (SQLException e) {
                System.err.println("Produto nao encontrado");
                System.err.println(e);
                //System.exit(1);
            }
            return res;
        }
    }

    public void inserir(Produto obj) {
        String sql = "INSERT INTO produtos (sku, nome, descricao, preco, "
                + "promovido, categoria_id, refe, dt_cadastrado, refebig) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, obj.getSku());
            stm.setString(2, obj.getNome());
            stm.setString(3, obj.getDescricao());
            stm.setDouble(4, obj.getPreco());
            stm.setBoolean(5, obj.isPromovido());
            stm.setInt(6, obj.getCategoria_id());
            stm.setString(7, obj.getRefe());
            stm.setString(8, obj.getRefeBig());
            stm.setDate(9, obj.getDt_cadastrado());
            stm.execute();
        } catch (SQLException e) {
            System.err.println("Falha na insercao");
            System.err.println(e);
            //System.exit(1);
        }
        System.out.println("Insercao Efetuada");
    }

    public void atualizar(Produto obj) {
        String sql = "UPDATE produtos SET preco = ? WHERE sku = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setDouble(1, obj.getPreco());
            stm.setInt(2, obj.getSku());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na atualização.");
            System.err.println(ex);
            //System.exit(1);
        }
        System.out.println("Atualização Efetuada!");
    }

    public void remover(Produto obj) {
        String sql = "DELETE FROM produtos WHERE sku = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, obj.getSku());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na remoção.");
            System.err.println(ex);
            //System.exit(1);
        }
        System.out.println("Remoção Efetuada!");
    }

    public ArrayList listarPromovidos() {
        ArrayList res = new ArrayList();
        String sql = "SELECT * FROM produtos WHERE promovido = 1";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Produto obj = new Produto();
                obj.setSku(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setDescricao(rs.getString(3));
                obj.setPreco(rs.getDouble(4));
                obj.setPromovido(rs.getBoolean(5));
                obj.setCategoria_id(rs.getInt(6));
                obj.setRefe(rs.getString(7));
                obj.setRefeBig(rs.getString(8));
                obj.setDt_cadastrado(rs.getDate(9));
                res.add(obj);
            }
        } catch (SQLException e) {
            System.err.println("Produtos nao encontrados");
            System.err.println(e);
            //System.exit(1);
        }
        return res;
    }
}
