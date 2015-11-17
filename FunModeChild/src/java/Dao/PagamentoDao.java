package Dao;

import Model.Pagamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PagamentoDao {

    private final Connection con;

    public PagamentoDao() {
        con = new DataSource("funchildmode").getCon();
    }

    public void inserir(Pagamento pagamento) {
        String sql = "INSERT INTO pagamento (" +
                        "pedido_id," +
                        "numerocartao," +
                        "nomecartao," +
                        "validademes," +
                        "validadeano," +
                        "codigo," +
                        "parcelas," +
                        "ativo" +
                    ") VALUES (" +
                        "?," +
                        "?," +
                        "?," +
                        "?," +
                        "?," +
                        "?," +
                        "?," +
                        "?" +
                    ")";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, pagamento.getPedido().getPedido_id());
            stm.setString(2, pagamento.getNumeroCartao());
            stm.setString(3, pagamento.getNomeCartao());
            stm.setInt(4, pagamento.getValidadeMes());
            stm.setInt(5, pagamento.getValidadeAno());
            stm.setInt(6, pagamento.getCodigo());
            stm.setInt(7, pagamento.getParcelas());
            stm.setBoolean(8, pagamento.isAtivo());
            stm.execute();
        } catch (SQLException e) {
            System.err.println("Falha na insercao do pagamento");
            System.err.println(e);
        }
        System.out.println("Insercao do Pagamento Efetuada");
    }

}
