<%-- 
    Document   : cadresp
    Created on : 04/11/2015, 12:23:28
    Author     : gabriel
--%>
<%@page import="Dao.ContaDao"%>
<%@page import="Model.Conta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Cadastro" />
</jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
        <%
            Conta obj = new Conta();
            obj.setUsuario(request.getParameter("usuario"));
            obj.setNome(request.getParameter("nome"));
            obj.setEmail(request.getParameter("email"));
            obj.setCpf(request.getParameter("cpf"));
            obj.setLogradouro(request.getParameter("logradouro"));
            obj.setEndereco(request.getParameter("endereco"));
            obj.setBairro(request.getParameter("bairro"));
            obj.setNumero(request.getParameter("numero"));
            obj.setComplemento(request.getParameter("complemento"));
            obj.setCep(request.getParameter("cep"));
            obj.setCidade(request.getParameter("cidade"));
            obj.setEstado(request.getParameter("estado"));
            obj.setTelefone(request.getParameter("telefone"));
            obj.setSenha(request.getParameter("password"));

            ContaDao banco = new ContaDao();
            boolean sucesso = banco.inserir(obj);

            if(sucesso) {
            %>
            <div class="alert alert-success" role="alert">
                <span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
                Cadastro efetuado com sucesso!
            </div>
            <a href="index.jsp" class="btn btn-default">Voltar para a home</a>
            <%
            } else {
            %>
            <div class="alert alert-danger" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                Erro ao efetuar o cadastro
            </div>
            <a href="javascript:history.back(1);" class="btn btn-default">Voltar</a>
            <%
            }
        %>
        </div>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
