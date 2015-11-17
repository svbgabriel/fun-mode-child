<%-- 
    Document   : cancelarpedido
    Created on : 15/11/2015, 21:47:43
    Author     : gabriel
--%>

<%@page import="Model.Detalhe_Pedidos"%>
<%@page import="Model.Pedidos"%>
<%@page import="Dao.Detalhe_PedidosDao"%>
<%@page import="Dao.PedidosDao"%>
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
    //Detalhe_Pedidos pedidodet = dpedidos.consultar(p_id);
%>
<div class="container">
    <div class="row">
        <div class="col-md-12">
<%            
    if (pedido.getUsuario() != null) {        
      
        dpedidos.remover(ip_id);
        pedidodao.remover(pedido);        
%>
            <div class="alert alert-success" role="alert">
                <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
                Pedido <%=pedido.getPedido_id()%> foi cancelado :(
            </div>
            <a href="index.jsp" class="btn btn-default">Voltar para a home</a>
<%        
    } else {
%>
        <div class="alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            Erro ao cancelar o pedido
        </div>
        <a href="javascript:history.back(1);" class="btn btn-default">Voltar</a>
<%
    }
%>    
        </div>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
