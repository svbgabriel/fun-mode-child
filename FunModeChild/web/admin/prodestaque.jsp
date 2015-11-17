<%-- 
    Document   : prodestaque
    Created on : 05/11/2015, 13:24:44
    Author     : gabriel
--%>
<%@page import="Model.Produto"%>
<%@page import="Dao.ProdutoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Dao.CategoriaDao"%>
<%@page import="Model.Categoria"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header_admin.jsp">
    <jsp:param name="titulo" value="FMC Admin"/>
</jsp:include>

<form action="prodestaqueresp.jsp" method="post">
    <div class="container mg-btm-30">
        <h1>Produtos em destaque</h1>
        <p>Pesquisar Produto:</p>
        SKU <input type="text" name="sku" required="required"/>
        <br><button type="submit" class="btn btn-primary">Pesquisar</button>
    </div>
</form>

<%@include file="footer_admin.jsp"%>
