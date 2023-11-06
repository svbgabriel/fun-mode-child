<%--
    Document   : prodestaqueresp
    Created on : 15/11/2015, 15:47:40
    Author     : gabriel
--%>

<%@page import="Model.Produto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header_admin.jsp">
    <jsp:param name="titulo" value="FMC Admin"/>
</jsp:include>
<%
    Produto product = new Produto();
%>
<%@include file="footer_admin.jsp"%>
