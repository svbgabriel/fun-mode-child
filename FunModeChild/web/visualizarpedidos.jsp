<%-- 
    Document   : visualizarpedidos
    Created on : 15/11/2015, 16:50:27
    Author     : gabriel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Dao.PedidosDao"%>
<%@page import="Dao.ContaDao"%>
<%@page import="Model.Conta"%>
<%@page import="Model.Pedidos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Fun Mode Child" />
</jsp:include>
<%
    PedidosDao pedidodao = new PedidosDao();
    Conta conta = new Conta();
    ContaDao contadao = new ContaDao();
    String user = (String) session.getAttribute("username");

    conta = contadao.consultar(user);
%>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2>Status dos Pedidos</h2>


            <div>


                <%
                    if (conta != null) {
                        ArrayList<Pedidos> p = pedidodao.consultarUsuario(conta.getUsuario());
                %>
                <table class="table table-striped table-responsive">
                    <tr> 
                        <td>
                            Pedido
                        </td>
                        <td>
                            Data do Pedido
                        </td>
                        <td>
                            Valor
                        </td>
                        <td>
                            <br>
                        </td>
                    </tr>
                    <%
                        if (p.size() > 0) {
                            for (int i = 0; i < p.size(); i++) {
                    %>
                    <tr>
                        <td>
                            <%=p.get(i).getPedido_id()%>
                        </td>
                        <td>
                            <%=p.get(i).getData_pedido()%>
                        </td>
                        <td>
                            R$<%=p.get(i).getPreco_total()%>
                        </td>
                        <td>
                            <a href="visualizardetalhes.jsp?pedido_id=<%=p.get(i).getPedido_id()%>">Detalhes</a>
                        </td>


                    </tr>
                    <%
                        }
                    } else {
                    %>

                </table>

            </div>

            <div class="alert alert-warning" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                Não foram encontrados pedidos
            </div>
            <a href="javascript:history.back(1);" class="btn btn-default">Voltar</a>
            <%
                }
            } else {
            %>
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                Erro ao verificar o login
            </div>
            <a href="javascript:history.back(1);" class="btn btn-default">Voltar</a>
            <%
                }
            %>
        </div>
        <%@include file="includes/footer.jsp"%>