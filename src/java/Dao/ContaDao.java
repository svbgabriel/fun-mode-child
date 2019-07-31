package Dao;

import Model.Conta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaDao {
    private final Connection con;
    
    public ContaDao() {
        con = new DataSource("funchildmode").getCon();
    }
    
    public Conta consultar(String chave) {
        Conta obj = null;
        String sql = "SELECT * FROM conta WHERE usuario = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, chave);            
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                obj = new Conta();
                obj.setUsuario_id(rs.getInt(1));
                obj.setUsuario(rs.getString(2));
                obj.setNome(rs.getString(3));
                obj.setEmail(rs.getString(4));
                obj.setCpf(rs.getString(5));
                obj.setLogradouro(rs.getString(6));
                obj.setEndereco(rs.getString(7));
                obj.setNumero(rs.getString(8));
                obj.setComplemento(rs.getString(9));
                obj.setBairro(rs.getString(10));
                obj.setCep(rs.getString(11));
                obj.setCidade(rs.getString(12));
                obj.setEstado(rs.getString(13));
                obj.setTelefone(rs.getString(14));
                obj.setSenha(rs.getString(15));
                obj.setActive(rs.getBoolean(16));
            }
        } catch (SQLException e) {
            System.err.println("Usuario nao encontrado");
            System.err.println(e);
        }
        return obj;        
    }
    
    public boolean inserir(Conta obj) {
        String sql = "INSERT INTO conta (usuario, nome, email, cpf, logradouro,"
                + " endereco, numero, complemento, bairro, cep, cidade, estado, "
                + "telefone, senha, active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, true)";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, obj.getUsuario());
            stm.setString(2, obj.getNome());
            stm.setString(3, obj.getEmail());
            stm.setString(4, obj.getCpf());
            stm.setString(5, obj.getLogradouro());
            stm.setString(6, obj.getEndereco());
            stm.setString(7, obj.getNumero());
            stm.setString(8, obj.getComplemento());
            stm.setString(9, obj.getBairro());
            stm.setString(10, obj.getCep());
            stm.setString(11, obj.getCidade());
            stm.setString(12, obj.getEstado());
            stm.setString(13, obj.getTelefone());
            stm.setString(14, obj.getSenha());            
            stm.execute();
        } catch (SQLException e) {
            System.err.println("Falha na insercao");
            System.err.println(e);
            return false;
        }
        System.out.println("Insercao Efetuada");
        return true;
    }
    
    public boolean atualizar(String senha, String user) {
        String sql = "UPDATE conta SET senha = ? WHERE usuario = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, senha);
            stm.setString(2, user);
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na atualização.");
            System.err.println(ex);
            return false;
        }
        return true;
    }
    
    public void remover(Conta obj) {
        String sql = "DELETE FROM conta  WHERE usuario = ? AND senha = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(2, obj.getSenha());
            stm.setString(1, obj.getUsuario());
            stm.execute();
        } catch (SQLException ex) {
            System.err.println("Falha na remoção.");
            System.err.println(ex);
            System.exit(1);
        }
        System.out.println("Remoção Efetuada!");
    }
    
    public Conta login(String usuario, String senha) {
        Conta res = null;
        String sql = "SELECT usuario_id, usuario, nome " +
                       "FROM conta " +
                      "WHERE usuario = ? " +
                        "AND senha = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, usuario);
            stm.setString(2, senha);
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                res = new Conta();
                res.setUsuario_id(rs.getInt("usuario_id"));
                res.setUsuario(rs.getString("usuario"));
                res.setNome(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Usuario nao encontrado");
            System.err.println(e);
        }
        return res;
    }
}
