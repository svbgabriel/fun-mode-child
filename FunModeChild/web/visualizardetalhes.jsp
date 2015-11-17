<%-- 
    Document   : visualizardetalhes
    Created on : 15/11/2015, 19:24:59
    Author     : gabriel
--%>

<%@page import="Model.Produto"%>
<%@page import="Dao.ProdutoDao"%>
<%@page import="Dao.Detalhe_PedidosDao"%>
<%@page import="Dao.PedidosDao"%>
<%@page import="Model.Pedidos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Fun Mode Child" />
</jsp:include>
<%
    String p_id = request.getParameter("pedido_id");
    // Tenta converter o id recebido para Integer.
    int ip_id = 0;
        try {
            ip_id = Integer.parseInt(p_id);
        } catch (NumberFormatException nfe) {

        }
        
    String user = (String)session.getAttribute("username");
    
    PedidosDao pedidodao = new PedidosDao();
    Pedidos pedido = pedidodao.consultarUsuarioPedido(user, ip_id);
    Detalhe_PedidosDao dpedidos = new Detalhe_PedidosDao();
    ProdutoDao pdao = new ProdutoDao();
    Produto produto;
%>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <%              
                if (pedido.getUsuario() != null) {
                    pedido.setDetalhes(dpedidos.consultar(p_id));
                    for (int i = 0; i < pedido.getDetalhes().size(); i++) {
                        produto = pdao.consultarSku(pedido.getDetalhes().get(i).getSku());
            %>
            <br>
            <img src=<%=produto.getRefe()%> alt="">
            <br><br>
            <table class="table table-hover">
                <tr>
                    <td>
                        <h5>Pedido:</h5>
                    </td>
                    <td>
                        <h5><%=pedido.getPedido_id()%></h5>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h5>Nome: </h5>
                    </td>
                    <td>
                        <h5><%=produto.getNome()%></h5>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h5>Quantidde: </h5>
                    </td>
                    <td>
                        <h5><%=pedido.getDetalhes().get(i).getQuantidade()%></h5>
                    </td>
                </tr>
                <tr>
                    <td>
                        <h5>Valor: </h5>
                    </td>
                    <td>
                        <h5>R$<%=pedido.getDetalhes().get(i).getPreco_item()%></h5>
                    </td>
                </tr>

            </table>
            <!--            <div>
                            <img src=<%=produto.getRefe()%> alt="">
                            <h3><%=produto.getNome()%></h3>
                            <h3>Quantidade : <%=pedido.getDetalhes().get(i).getQuantidade()%></h3>
                            <h3>Valor Unitário : R$ <%=pedido.getDetalhes().get(i).getPreco_item()%></h3>
                        </div>-->
            <%
                    }
            %>
            <div class="alert alert-warning">
                Deseja cancelar esse pedido ? Atenção essa ação não pode ser desfeita!            
            </div>
            <a class="btn btn-default btn-sm" href="cancelarpedido.jsp?pedido_id=<%=pedido.getPedido_id()%>">Cancelar</a>
            <%        
                } else {
            %>
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                Erro ao verificar os detalhes do pedido
            </div>
            <a href="javascript:history.back(1);" class="btn btn-default">Voltar</a>
            <%
                }
            %>    
        </div>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
