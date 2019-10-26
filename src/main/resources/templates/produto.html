<%-- 
    Document   : produto
    Created on : Nov 9, 2015, 2:50:39 PM
    Author     : gabriel
--%>
<%@page import="Model.ProdutoEstoque"%>
<%@page import="Model.Estoque"%>
<%@page import="Dao.EstoqueDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Produto"%>
<%@page import="Dao.ProdutoDao"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Fun Mode Child" />
</jsp:include>

<div class="container">
    <%
        String sku = request.getParameter("sku");
        // Tenta converter o sku recebido para Integer.
        int isku = 0;
        try {
            isku = Integer.parseInt(sku);
        } catch (NumberFormatException nfe) {

        }

        if (isku > 0) {
            ProdutoEstoque produto = new ProdutoDao().consultarSkuEsProduto(isku);
            if (produto == null) {
                out.println("<div class=\"row\"><div class=\"col-md-12\">Produto não encontrado.</div></div>");
            } else {
    %>
    <div class="row">
        <div class="col-md-9">
            <img src="<%=produto.getRefe()%>" alt="" />
            <h2><%=produto.getNome()%></h2>
            <%=produto.getDescricao()%>
            <h3>R$ <%=produto.getPrecoFormatado()%></h3>
        </div>
        <div class="col-md-3">
            <%
                if (produto.getQtdEstoque() > 0) {
            %>
            <button class="btn btn-success btn-lg" onclick="adicionarCarrinho('<%=produto.getSku()%>');"><span class="glyphicon glyphicon-shopping-cart"></span> Adicionar ao carrinho</button>
            <%
            } else {
            %>
            <h2><span class="label label-danger">Esgotado</span></h2>
            <%
                }
            %>
        </div>
    </div>
    <%
            }
        } else {
            out.println("<div class=\"row\"><div class=\"col-md-12\">Produto não encontrado.</div></div>");
        }
    %>

    <%
        // Produtos Relacionados
        ProdutoDao prodDao = new ProdutoDao();
        Produto p = prodDao.consultarSku(isku);
        ArrayList<Produto> r = prodDao.consultarCategoria(p.getCategoria_id());

        if (r.size() <= 0) {
    %>
    <div class="row"><div class="col-md-12"><hr /><h5>Produtos Relacionados não encontrados</h5></div></div>
            <%
            } else {
            %>
    <div class="row"><div class="col-md-12"><hr /><h5>Produtos Relacionados</h5></div></div>
    <div class="row">
        <%
            for (int i = 0; i < r.size() - 1; i++) {
        %>
        <div class="col-sm-4 col-lg-4 col-md-4">
            <div class="thumbnail">
                <img src=<%=r.get(i).getRefe()%> alt="">
                <div class="caption">
                    <h4><a href="produto.jsp?sku=<%=r.get(i).getSku()%>"><%out.println(r.get(i).getNome());%></a></h4>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <%
                    }%>
</div>
</div> <!-- /container -->



<script>
    function adicionarCarrinho(sku) {
        document.location.href = "carrinho.jsp?sku=" + sku;
    }
</script>
<%@include file="includes/footer.jsp"%>
