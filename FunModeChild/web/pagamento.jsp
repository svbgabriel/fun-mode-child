<%-- 
    Document   : pagamento
    Created on : Nov 15, 2015, 4:52:47 PM
    Author     : henrique
--%>
<%@page import="Dao.PagamentoDao"%>
<%@page import="Model.Pagamento"%>
<%@page import="Dao.PedidosDao"%>
<%@page import="Model.Detalhe_Pedidos"%>
<%@page import="Model.Pedidos"%>
<%@page import="Model.Produto"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.AbstractMap"%>
<%@page import="Dao.ProdutoDao"%>
<%@page import="Model.Carrinho"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Dao.ContaDao"%>
<%@page import="Model.Conta"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Carrinho carrinho = (Carrinho) session.getAttribute("carrinhocompras");
    boolean erroLogin = false;  
    String erroSalvar = "";
    int idPedido = -1;
    
    
    // Login
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
    

    // Pedido
    if(request.getParameter("salvar") != null && carrinho != null) {
        // Clicou em Salvar Pedido.
        
        if(request.getParameter("numerocartao") == null) {
            erroSalvar += "<li>Informe o número do cartão</li>";
        }
        if(request.getParameter("nomecartao") == null) {
            erroSalvar += "<li>Informe o nome que está no cartão</li>";
        }
        if(request.getParameter("validademes") == null) {
            erroSalvar += "<li>Informe o mês de validade do cartão</li>";
        } 
        if(request.getParameter("validadeano") == null) {
            erroSalvar += "<li>Informe o ano de validade do cartão</li>";
        } 
        if(request.getParameter("codigo") == null) {
            erroSalvar += "<li>Informe o código de verificação do cartão</li>";
        } 
        if(request.getParameter("parcelas") == null) {
            erroSalvar += "<li>Informe o número de parcelas</li>";
        }
        
        if(erroSalvar.length() == 0) {
            if(!carrinho.getLista().isEmpty()) {
                ProdutoDao produtoDao = new ProdutoDao();
                double precoTotal = 0.0;
                Pedidos pedido = new Pedidos();
                Detalhe_Pedidos detalhePedido;

                AbstractMap<Integer, Integer> listaProdutos = carrinho.getLista();
                Iterator<Integer> keySetIterator = listaProdutos.keySet().iterator();
                while(keySetIterator.hasNext()){
                    int sku = keySetIterator.next();
                    Produto produto = produtoDao.consultarSku(sku);

                    detalhePedido = new Detalhe_Pedidos();
                    detalhePedido.setPreco_item(produto.getPreco());
                    detalhePedido.setQuantidade(listaProdutos.get(sku));
                    detalhePedido.setSku(sku);

                    pedido.getDetalhes().add(detalhePedido);

                    precoTotal += detalhePedido.getPreco_item() * detalhePedido.getQuantidade();
                }

                pedido.setPreco_total(precoTotal);
                pedido.setUsuario((String)session.getAttribute("username"));
                idPedido = new PedidosDao().inserirComDetalhes(pedido);

                if(idPedido > -1) {
                    // Salvou o pedido.
                    pedido.setPedido_id(idPedido);

                    // Salva os dados de pagamento
                    Pagamento pagamento = new Pagamento();
                    pagamento.setPedido(pedido);
                    pagamento.setNumeroCartao(request.getParameter("numerocartao"));
                    pagamento.setNomeCartao(request.getParameter("nomecartao"));
                    pagamento.setValidadeMes(Integer.parseInt(request.getParameter("validademes")));
                    pagamento.setValidadeAno(Integer.parseInt(request.getParameter("validadeano")));
                    pagamento.setCodigo(Integer.parseInt(request.getParameter("codigo")));
                    pagamento.setParcelas(Integer.parseInt(request.getParameter("parcelas")));
                    pagamento.setAtivo(true);
                    
                    new PagamentoDao().inserir(pagamento);

                    // Esvazia o carrinho.
                    session.setAttribute("carrinhocompras", null);
                }
            }
        }
    }
%>



<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Fun Mode Child" />
</jsp:include>

<div class="container">
<form method="post" action="pagamento.jsp">
<%
// Verifica se o usuário está logado.
// Fazemos isso verificando se o atributo "username"
//    está presente na session.
if (session.getAttribute("username") == null) {
    // Usuário não está logado. Apresenta form de login.
    %>
    <div class="row">
        <div class="col-md-12">
            <h4 class="mg-btm-50">Por favor, efetue o login para continuar.</h4>
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
            %>
        </div>
    </div>
<%
} else {
%>

    <div class="row">
        <div class="col-md-6">
            <!-- Cartao -->
            <h3>Forma de Pagamento - Cartão de Crédito</h3>
            <div class="row">
                <div class="col-md-8">
                    <div class="form-group">
                        <label for="numerocartao">Número do cartão</label>
                        <input type="text" class="form-control" id="numerocartao" name="numerocartao" maxlength="16" required="required">
                    </div>
                </div>
            </div>
            <div class="row">    
                <div class="col-md-8">
                    <div class="form-group">
                        <label for="nomecartao">Nome</label>
                        <input type="text" class="form-control" id="nomecartao" name="nomecartao" maxlength="18" required="required">
                    </div>
                </div>
            </div>
            <div class="row">    
                <div class="col-md-12">
                    <h5>Validade do Cartão</h5>
                    <div class="row">    
                        <div class="col-md-4">                    
                            <label for="validademes">Mês</label>
                            <div class="form-group">
                                <select name="validademes" class="form-control" required="required">
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
                        <div class="col-md-4">
                            <label for="validadeano">Ano</label>
                            <div class="form-group">
                                <select name="validadeano" class="form-control" required="required">
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2020">2020</option>
                                    <option value="2021">2021</option>
                                    <option value="2022">2022</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <label for="codigo">Código</label>
                            <div class="form-group">
                                <input type="text" placeholder="xxx" class="form-control" id="codigo" name="codigo" maxlength="3" required="required">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <label for="parcelas">Parcelas</label>
                            <div class="form-group">
                                <select name="parcelas" class="form-control" required="required">
                                    <option value="1">1x</option>
                                    <option value="2">2x</option>
                                    <option value="3">3x</option>
                                    <option value="4">4x</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <!-- Endereco -->
            <h3>Endereço de Entrega</h3>
            <%
                ContaDao conta = new ContaDao();
                Conta contaCliente = conta.consultar((String) session.getAttribute("username"));
            %>
            <p><%=contaCliente.getLogradouro()%></p>
            <p><%=contaCliente.getEndereco()%></p>
            <p><%=contaCliente.getNumero()%></p>
            <p><%=contaCliente.getBairro()%></p>
            <p><%=contaCliente.getCidade()%></p>
            <p>CEP: <%=contaCliente.getCep()%></p>
        </div>
    </div> <!-- /row -->
    <div class="row">
        <div class="col-md-12">
            <!-- Itens -->
            <h3>Itens</h3>
            <%
            if (carrinho != null && !carrinho.getLista().isEmpty()) {
                // Monta a tabela de produtos

                // Para cada SKU que está no carrinho, buscamos o produto 
                //  correspondente no banco.
                // Uma forma mais eficiente (e recomendada) seria buscar uma 
                //  lista de instâncias de Produto para
                //  todos os SKUs do carrinho, de uma única vez.
                ProdutoDao produtoDao = new ProdutoDao();

                // Variáveis para cálculo e apresentação dos valores dos itens e total.
                String precoItemFormatado;
                double precoTotalItem;
                String precoTotalItemFormatado;
                double totalCarrinho = 0.0;
                String totalCarrinhoFormatado;
                %>
                <table class="table">
                    <tr>
                        <th>Item</th>
                        <th style="width:150px;">Quantidade</th>
                        <th style="width:150px;" class="text-right">Preço Unitário</th>
                        <th style="width:150px;" class="text-right">Valor</th>
                    </tr>
                    <%
                        AbstractMap<Integer, Integer> listaProdutos = carrinho.getLista();
                        Iterator<Integer> keySetIterator = listaProdutos.keySet().iterator();
                        while (keySetIterator.hasNext()) {
                            int sku = keySetIterator.next();
                            Produto produto = produtoDao.consultarSku(sku);
                            precoItemFormatado = String.format("%1$,.2f", produto.getPreco());
                            precoTotalItem = produto.getPreco() * listaProdutos.get(sku);
                            precoTotalItemFormatado = String.format("%1$,.2f", precoTotalItem);
                            totalCarrinho += precoTotalItem;
                    %>
                    <tr>
                        <td><%=produto.getNome()%></td>
                        <td><%=listaProdutos.get(sku)%></td>
                        <td class="text-right">R$<%=precoItemFormatado%></td>
                        <td class="text-right">R$<%=precoTotalItemFormatado%></td>
                    </tr>
                    <%
                        } // while
                        totalCarrinhoFormatado = String.format("%1$,.2f", totalCarrinho);
                    %>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td class="text-right"><strong>Total</strong></td>
                        <td class="text-right">R$<%=totalCarrinhoFormatado%></td>
                    </tr>
                </table>
                <div class="row">
                    <div class="col-md-6">
                        <button type="button" class="btn btn-warning btn-xs" onclick="document.location.href = 'carrinho.jsp';">Voltar para o Carrinho</button>
                    </div>
                    <div class="col-md-6 text-right">
                        <button type="submit" class="btn btn-primary" name="salvar" value="1">Finalizar Pedido</button>
                    </div>
                </div>
            <%
                if (request.getParameter("salvar") != null) {
                    if(erroSalvar.length() > 0) {
                        %>
                        <div class="row">
                            <div class="col-md-12">
                                <ul clas="text-danger"><%=erroSalvar%></ul>
                            </div>
                        </div>
                        <%
                    } else {
                        // Se clicou em Salvar, exibe a mensagem de erro ou sucesso aqui.
                        if (idPedido == -1) {
                            %>
                            <div class="row">
                                <div class="col-md-5">
                                    <div class="alert alert-danger mg-top-30" role="alert"><span class="glyphicon glyphicon-exclamation-sign"></span> Não foi possível salvar o pedido.</div>
                                </div>
                            </div>
                            <%
                        } else {
                            %>
                            <div class="row">
                                <div class="col-md-5">
                                    <div class="alert alert-success mg-top-30" role="alert"><span class="glyphicon glyphicon-ok-sign"></span> Pedido salvo com sucesso!</div>
                                </div>
                            </div>
                            <%
                        }
                    }
                }
            } else {
                out.println("<p>Seu carrinho de compras está vazio!</p>");
            } // else carrinho vazio
            %>
            
            
            
        </div>
    </div>
<%
} // else (session.getAttribute("username") == null)
%>

</form>
</div> <!-- /container -->

<%@include file="includes/footer.jsp"%>