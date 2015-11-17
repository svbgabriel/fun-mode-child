<%-- 
    Document   : entresp
    Created on : 11/11/2015, 22:25:10
    Author     : gabriel
--%>
<%@page import="Dao.ContaDao"%>
<%@page import="Model.Conta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // A lógica de login deve ficar antes do include header.jsp, senão o link
    // "Sair" só aparecerá na barra de navegação após um refresh da página,
    // caso o usuário tenha se logado com sucesso.
    String user = request.getParameter("usuario");
    String pass = request.getParameter("password");            

    ContaDao banco = new ContaDao();
    Conta obj = banco.login(user,pass);
    if(obj != null) {
        session.setAttribute("username", user);
        session.setAttribute("nome", obj.getNome());
    }
%>

<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Cadastro" />
</jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
        <%
            if(obj != null) {
            %>
            <div class="alert alert-success" role="alert">
                <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
                Login efetuado com sucesso!
                Bem-vindo, <%=session.getAttribute("nome")%>
            </div>
            <a href="index.jsp" class="btn btn-default">Voltar para a home</a>
            <%
            } else {
            %>
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                Erro ao efetuar o login
            </div>
            <a href="javascript:history.back(1);" class="btn btn-default">Voltar</a>
            <%
            }
        %>
        </div>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
