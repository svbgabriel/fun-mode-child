<%-- 
    Document   : admin
    Created on : 12/11/2015, 11:29:19
    Author     : gabriel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Model.Categoria"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Categoria"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Dao.CategoriaDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header_admin.jsp">
    <jsp:param name="titulo" value="FMC Admin"/>
</jsp:include>
<ul>
        <div class="container">
        <div class="row">
            <div class="col-md-3">
                <h4>Area de Administraçao</h4>
                <a href="admin.jsp" class="list-group-item">Cadastrar Produto</a>
                <a href="admin.jsp" class="list-group-item">Estoque</a>
                <a href="admin.jsp" class="list-group-item">Verificar Pedidos</a>
                <a href="admin.jsp" class="list-group-item">Verificar Cadastro</a>
                <a href="admin.jsp" class="list-group-item">Produtos em Destaque</a>


            </div>

            <form action="cadastrarProduto.jsp" method="post">
                <div class="col-md-9">
                    <br><br>
                    <div class="row">
                        <div class="col-md-7">
                            <div class="form-group">
                                <label for="nomeProduto">Nome do Produto</label>
                                <input type="text" class="form-control" id="descricao" name="nome" maxlength="255" required="required">
                            </div>
                        </div>
                        <div class="col-md-2">  
                            <div class="form-group">
                                <label for="preço">Preço</label>
                                <input type="text" class="form-control" id="preço" name="preço" required="required">
                            </div>
                        </div>
                        <div class="col-md-3">  
                            <div class="form-group">
                                <label for="categoria">Categoria</label>
                                <select name="categoria" class="form-control">
                                    <option value="">Selecione</option>
                                    <%
                                        CategoriaDao dao = new CategoriaDao();
                                        ArrayList<Categoria> listaCategorias = dao.consultar();
                                        for (Categoria cat : listaCategorias) {
                                    %>
                                    <option value=""><%out.println(cat.getNome());%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-12">  
                            <div class="form-group">
                                <label for="descriçao">Descriçao</label>
                                <textarea class="form-control" id="descricao" name="descricao" required="required"></textarea>
                                <!--<input type="text" class="form-control" id="descricao" name="descricao" required="required">-->
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-md-2">  
                            <div class="form-group">
                                <label for="promover">Promover?</label>
                                <select name="promovido" required="required" class="form-control">
                                    <option value="">Sim</option>
                                    <option value="">Nao</option>

                                </select>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-md-2">  
                            <div class="form-group">
                                <label for="foto">Foto do Produto</label>
                                <input type="file" name="anexo">
                            </div>
                        </div>
                    </div>
                    <a type="submit"  class="btn btn-default " href="#">Cadastrar Produto</a>

                </div>
            </form>
        </div>
    </div>









</ul>
<%@include file="footer_admin.jsp"%>
