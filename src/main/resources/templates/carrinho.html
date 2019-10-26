<%-- 
    Document   : carrinho
    Created on : Nov 9, 2015, 3:28:19 PM
--%>

<%@page import="Model.Produto"%>
<%@page import="Model.Carrinho"%>
<%@page import="Model.Estoque"%>
<%@page import="Dao.ProdutoDao"%>
<%@page import="Dao.EstoqueDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.lang.String"%>
<%@page import="java.util.AbstractMap"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Fun Mode Child" />
</jsp:include>

<%
    // O carrinho contém apenas o SKU e sua quantidade.
    Carrinho carrinho = (Carrinho) session.getAttribute("carrinhocompras");
    if (carrinho == null) {
        // Carrinho ainda não existe na session. Cria um novo.
        carrinho = new Carrinho();
    }

    String message = "";

    if (request.getParameter("alt") != null) {
        // Clicou no botão Alterar!
        EstoqueDao estoqueDao = new EstoqueDao();
        ProdutoDao produtoDao = new ProdutoDao();

        String[] skualt = request.getParameterValues("skualt");
        String[] qtde = request.getParameterValues("qtde");

        for (int i = 0; i < skualt.length; i++) {
            int isku = Integer.parseInt(skualt[i]);
            int iqtd = Integer.parseInt(qtde[i]);

            if (iqtd < 1) {
                // A quantidade foi atualizada para 0, 
                // portanto removemos o produto do carrinho.
                carrinho.remove(isku);
            } else {
                // Quantidade do produto foi alterada.
                // Verifica se tem saldo em estoque.
                Estoque estoque = estoqueDao.consultarQtdEstoque(isku);
                if (estoque != null && estoque.getQuantidadeEstoque() < iqtd) {
                    Produto produto = produtoDao.consultarSku(isku);
                    message += "<li>Estoque insuficiente para o produto <strong>" + produto.getNome() + "</strong>!</li>";
                    // Atualiza o produto no carrinho com a quantidade que tem em estoque.
                    carrinho.atualiza(isku, estoque.getQuantidadeEstoque());
                } else {
                    carrinho.atualiza(isku, iqtd);
                }
            }
        }
    }

    if (request.getParameter("sku") != null) {
        // Recebeu um sku. Adiciona ou atualiza o carrinho.

        // Alguém pode passar ?sku=a, maldosamente, por checamos
        //  a exception NumberFormatException.
        try {
            if (request.getParameter("del") != null && request.getParameter("del").equals("1")) {
                carrinho.remove(Integer.parseInt(request.getParameter("sku")));
            } else {
                carrinho.adiciona(Integer.parseInt(request.getParameter("sku")), 1);
            }
            // Atualiza (ou cria, se não existir) o carrinho na session.
            session.setAttribute("carrinhocompras", carrinho);
        } catch (NumberFormatException nfe) {
            //out.println("<p>Tentou zuar, né?</p>");
        }
    }
%>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2>Meu carrinho</h2>
            <%
                // Lista os produtos do carrinho.
                AbstractMap<Integer, Integer> listaProdutos = carrinho.getLista();
                if (carrinho.getLista().isEmpty()) {
                    out.println("<p>Seu carrinho de compras está vazio!</p>");
                } else {
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
            <form method="post" action="carrinho.jsp">
                <table class="table">
                    <tr>
                        <th>Item</th>
                        <th style="width:150px;">Quantidade</th>
                        <th style="width:150px;" class="text-right">Preço Unitário</th>
                        <th style="width:150px;" class="text-right">Valor</th>
                        <th style="width:30px;">&nbsp;</th>
                    </tr>
                    <%
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
                        <td>
                            <input type="hidden" name="skualt" value="<%=sku%>" />
                            <input type="text" name="qtde" value="<%=listaProdutos.get(sku)%>" class="form-control text-right" />
                        </td>
                        <td class="text-right">R$<%=precoItemFormatado%></td>
                        <td class="text-right">R$<%=precoTotalItemFormatado%></td>
                        <td><button type="button" class="btn btn-danger btn-xs" onclick="removerItem('<%=sku%>');"><span class="glyphicon glyphicon-trash"></span></button></td>
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
                        <td>&nbsp;</td>
                    </tr>
                </table>
                <div class="row">
                    <div class="col-md-8">
                        <%if (message.length() > 0) {%>
                        <span class="label label-warning">Atenção!</span>
                        <ul>
                            <%=message%>
                        </ul>
                        <%} else {%>
                        &nbsp;
                        <%}%>
                    </div>
                    <div class="col-md-4 text-right">
                        <button type="submit" class="btn btn-info" name="alt" value="1"><span class="glyphicon glyphicon-floppy-disk"></span> Salvar alterações</button>
                        <button type="button" class="btn btn-primary" onclick="document.location.href = 'pagamento.jsp';"><span class="glyphicon glyphicon-chevron-right"></span> Finalizar compra</button>
                    </div>
                </div>
            </form>
            <%
                    } // else
%>
            <button type="button" class="btn btn-success mg-top-50" onclick="document.location.href = './index.jsp';"><span class="glyphicon glyphicon-chevron-left"></span> Continuar comprando</button>
        </div>
    </div>
</div>

<script>
    function removerItem(sku) {
        document.location.href = "carrinho.jsp?del=1&sku=" + sku;
    }
</script>

<%@include file="includes/footer.jsp"%>
