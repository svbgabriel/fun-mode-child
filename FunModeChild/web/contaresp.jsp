<%-- 
    Document   : contaresp
    Created on : 16/11/2015, 01:41:51
    Author     : gabriel
--%>
<%@page import="Dao.ContaDao"%>
<%@page import="Model.Conta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Cadastro" />
</jsp:include>

<%  
    String user = (String)session.getAttribute("username");
    String passold = request.getParameter("passold");
    String passnew = request.getParameter("passnew");
    String cpassnew = request.getParameter("passnew2");

    ContaDao banco = new ContaDao();
    Conta obj = banco.login(user,passold);
%>

<div class="container">
    <div class="row">
        <div class="col-md-12">
        <%
            if(obj != null && (passnew.equals(cpassnew))) {
                if(banco.atualizar(passnew, user)) {
            %>
            <div class="alert alert-success" role="alert">
                <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
               Senha alterada sucesso!
            </div>
            <a href="index.jsp" class="btn btn-default">Voltar para a home</a>
            <%
            } else {
            %>
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                Erro ao alterar a senha
            </div>
            <a href="javascript:history.back(1);" class="btn btn-default">Voltar</a>
            <%
            }
            } else {
            %>
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                Erro ao alterar a senha
            </div>
            <a href="javascript:history.back(1);" class="btn btn-default">Voltar</a>
            <%
                    }
            %>
        </div>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
