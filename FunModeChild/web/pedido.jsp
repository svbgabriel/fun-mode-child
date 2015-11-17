<%-- 
    Document   : pedido
    Created on : Nov 11, 2015, 2:18:03 PM
    Author     : gabriel
--%>

<%@page import="Dao.PedidosDao"%>
<%@page import="Model.Pedidos"%>
<%@page import="Model.Detalhe_Pedidos"%>
<%@page import="Model.Produto"%>
<%@page import="java.util.Iterator"%>
<%@page import="Dao.ProdutoDao"%>
<%@page import="java.util.AbstractMap"%>
<%@page import="Model.Carrinho"%>
<%@page import="Model.Conta"%>
<%@page import="Dao.ContaDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    boolean erroLogin = false;
    Carrinho carrinho = (Carrinho)session.getAttribute("carrinhocompras");
    int idPedido = -1;
    
    if(request.getParameter("entrar") != null) {
        // Clicou no botão "Entrar" do form de login.
        Conta conta = new ContaDao().login(request.getParameter("usuario"), request.getParameter("senha"));
        if(conta == null) {
            erroLogin = true;
        } else {
            // Login efetuado com sucesso.
            // Guarda o usuario_id na session.
            session.setAttribute("username", conta.getUsuario());
            session.setAttribute("nome", conta.getNome());
        }
    }
    
    if(request.getParameter("salvar") != null && carrinho != null) {
        // Clicou em Salvar Pedido.
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
                // Salvou o pedido. Esvazia o carrinho.
                session.setAttribute("carrinhocompras", null);
            }
        }
    }
%>

<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Fun Mode Child" />
</jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2>Finalizar Pedido</h2>
            <%
              // Verifica se o usuário está logado.
              // Fazemos isso verificando se o atributo "username"
              //    está presente na session.
              if(session.getAttribute("username") == null) {
                  // Usuário não está logado. Apresenta form de login.
                  %>
                  <form method="post" action="pedido.jsp">
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
                      if(erroLogin) {
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
                    // Usuário não está logado. Lista carrinho.
                    // Lista os produtos do carrinho
                    if(carrinho != null && !carrinho.getLista().isEmpty()) {
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
                        <form method="post" action="pedido.jsp">
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
                            while(keySetIterator.hasNext()){
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
                                    <button type="button" class="btn btn-warning btn-xs" onclick="document.location.href='carrinho.jsp';">Voltar para o Carrinho</button>
                                </div>
                                <div class="col-md-6 text-right">
                                    <button type="submit" class="btn btn-primary" name="salvar" value="1">Finalizar Pedido</button>
                                </div>
                            </div>
                        </form>
                    <%
                        if(request.getParameter("salvar") != null) {
                            // Se clicou em Salvar, exibe a mensagem de erro ou sucesso aqui.
                            if(idPedido == -1) {
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
                    } else {
                        out.println("<p>Seu carrinho de compras está vazio!</p>");
                    } // else carrinho
                } // else usuário logado
            %>
        </div>
    </div>
</div>

<%@include file="includes/footer.jsp"%>