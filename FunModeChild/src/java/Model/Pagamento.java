package Model;

import java.sql.Date;

/**
 *
 * @author sumlauf
 */
public class Pagamento {
    
    private int pagamentoId;
    private Pedidos pedido;
    private String numeroCartao;
    private String nomeCartao;
    private int validadeMes;
    private int validadeAno;
    private int codigo;
    private int parcelas;
    private boolean ativo;
    private Date dataPagamento;

    /**
     * @return the pagamentoId
     */
    public int getPagamentoId() {
        return pagamentoId;
    }

    /**
     * @param pagamentoId the pagamentoId to set
     */
    public void setPagamentoId(int pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    /**
     * @return the pedido
     */
    public Pedidos getPedido() {
        return pedido;
    }

    /**
     * @param pedido the pedido to set
     */
    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
    }

    /**
     * @return the numeroCartao
     */
    public String getNumeroCartao() {
        return numeroCartao;
    }

    /**
     * @param numeroCartao the numeroCartao to set
     */
    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    /**
     * @return the nomeCartao
     */
    public String getNomeCartao() {
        return nomeCartao;
    }

    /**
     * @param nomeCartao the nomeCartao to set
     */
    public void setNomeCartao(String nomeCartao) {
        this.nomeCartao = nomeCartao;
    }

    /**
     * @return the validadeMes
     */
    public int getValidadeMes() {
        return validadeMes;
    }

    /**
     * @param validadeMes the validadeMes to set
     */
    public void setValidadeMes(int validadeMes) {
        this.validadeMes = validadeMes;
    }

    /**
     * @return the validadeAno
     */
    public int getValidadeAno() {
        return validadeAno;
    }

    /**
     * @param validadeAno the validadeAno to set
     */
    public void setValidadeAno(int validadeAno) {
        this.validadeAno = validadeAno;
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the parcelas
     */
    public int getParcelas() {
        return parcelas;
    }

    /**
     * @param parcelas the parcelas to set
     */
    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    /**
     * @return the ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * @return the dataPagamento
     */
    public Date getDataPagamento() {
        return dataPagamento;
    }

    /**
     * @param dataPagamento the dataPagamento to set
     */
    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    
}
