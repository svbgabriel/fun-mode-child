<%-- 
    Document   : pagamento
    Created on : Nov 15, 2015, 4:52:47 PM
    Author     : henrique
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Dao.ContaDao"%>
<%@page import="Model.Conta"%>

<%
    boolean erroLogin = false;

    if (request.getParameter("entrar") != null) {
        // Clicou no botão "Entrar" do form de login.
        Conta conta = new ContaDao().login(request.getParameter("usuario"), request.getParameter("senha"));
        if (conta == null) {
            erroLogin = true;
        } else {
            // Login efetuado com sucesso.
            // Guarda o usuario_id na session.
            session.setAttribute("username", conta.getUsuario());
            session.setAttribute("nome", conta.getNome());
        }
    }
%>

<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Fun Mode Child" />
</jsp:include>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <%
                // Verifica se o usuário está logado.
                // Fazemos isso verificando se o atributo "username"
                //    está presente na session.
                if (session.getAttribute("username") == null) {
                    // Usuário não está logado. Apresenta form de login.
            %>
            <h4 class="mg-btm-50">Por favor, efetue o login para continuar.</h4>
            <form method="post" action="pagamento.jsp">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="usuario">Usuário</label>
                            <input type="text" class="form-control" id="usuario" name="usuario">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="senha">Senha</label>
                            <input type="password" class="form-control" id="senha" name="senha">
                        </div>
                    </div>
                </div>
                <button type="submit" name="entrar" value="1" class="btn btn-default">Entrar</button>
            </form>
            <%
                if (erroLogin) {
                    // Tentou fazer login e deu erro. Mostra mensagem aqui.
            %>
            <div class="row">
                <div class="col-md-5">
                    <div class="alert alert-danger mg-top-30" role="alert"><span class="glyphicon glyphicon-exclamation-sign"></span> Dados de acesso inválidos.</div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <form action="pedido.jsp" method="post">
                <h3>Forma de Pagamento - Cartão de Crédito</h3>
                <div class="row">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label for="numcard">Número do cartão</label>
                            <input type="text" class="form-control" id="numcard"  name="numcard" maxlength="16" required="required">
                        </div>
                    </div>
                </div>
                <div class="row">    
                    <div class="col-md-4">
                        <div class="form-group">
                            <label for="nomecartao">Nome</label>
                            <input type="text" class="form-control" id="nomecartao" name="nomecartao" maxlength="50" required="required">
                        </div>
                    </div>
                </div>
                <label for="validade">Validade do Cartão</label>
                <div class="row">    
                    <div class="col-md-2">
                        <label for="mes">Mês</label>
                        <div class="form-group">
                            <select name="mes" class="form-control">
                                <option value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <label for="ano">Ano</label>
                        <div class="form-group">
                            <select name="ano" class="form-control">
                                <option value="15">2015</option>
                                <option value="16">2016</option>
                                <option value="17">2017</option>
                                <option value="18">2018</option>
                                <option value="19">2019</option>
                                <option value="20">2020</option>
                                <option value="21">2021</option>
                                <option value="22">2022</option>
                            </select>
                        </div>

                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">
                        <label for="codigo">Código</label>
                        <div class="form-group">
                            <input type="text" placeholder="xxx" class="form-control" id="codigo" name="codigo" maxlength="3" required="required">
                        </div>
                    </div>
                    <div class="col-md-2">
                        <label for="parcelas">Parcelas</label>
                        <div class="form-group">
                            <select name="parcelas" class="form-control">
                                <option value="1">1x</option>
                                <option value="2">2x</option>
                                <option value="3">3x</option>
                                <option value="4">4x</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <h4>Endereço de Entrega</h4>
                    <%
                        ContaDao conta = new ContaDao();
                        Conta contaCliente = conta.consultar((String) session.getAttribute("username"));
                    %>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label><%=contaCliente.getLogradouro()%></label>
                            <label><%=contaCliente.getEndereco() + ", "%></label>
                            <label><%=contaCliente.getNumero()%></label><br>
                            <label><%=contaCliente.getBairro() + ", "%></label>
                            <label><%=contaCliente.getCidade()%></label>
                            <label>CEP: <%=contaCliente.getCep() + "."%></label>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-chevron-right"></span> Finalizar Pagamento</button>
            </form>
            <%
                }
            %>
        </div>
    </div>
</div>
<%@include file="includes/footer.jsp"%>