<%-- 
    Document   : index
    Created on : 02/11/2015, 14:56:55
    Author     : Henrique
--%>

<%@page import="Model.ProdutoEstoque"%>
<%@page import="Model.Estoque"%>
<%@page import="Dao.EstoqueDao"%>
<%@page import="java.util.List"%>
<%@page import="Model.Produto"%>
<%@page import="Dao.ProdutoDao"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Dao.CategoriaDao"%>
<%@page import="Model.Categoria"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Fun Mode Child" />
</jsp:include>

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <img src="assets/img/logo-sm.png" alt="Fun Mode Child" class="img-responsive" />
            <div class="list-group">
                <%
                    CategoriaDao dao = new CategoriaDao();
                    ArrayList<Categoria> listaCategorias = dao.consultar();
                    for (Categoria c : listaCategorias) {
                %>
                <a href="index.jsp?cat=<%=c.getCategoria_id()%>" class="list-group-item"><%out.println(c.getNome());%></a>
                <%
                    }
                %>
            </div>
        </div>
        <div class="col-md-9">
            <div class="row">
                <div class="col-md-12">
                    <div id="promovidos" class="carousel slide" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <%
                                String ativo = "";
                                ProdutoDao produtoDao = new ProdutoDao();
                                ArrayList<Produto> produtosPromovidos = produtoDao.listarPromovidos();
                                for (int i = 0; i < produtosPromovidos.size(); i++) {
                                    if (i == 0) {
                                        ativo = " active";
                                    } else {
                                        ativo = "";
                                    }
                                    out.println("<li data-target=\"#promovidos\" data-slide-to=\"" + i + "\" class=\"" + ativo + "\"></li>");
                                }
                            %>

                        </ol>
                        <div class="carousel-inner" role="listbox">
                            <%
                                for (int i = 0; i < produtosPromovidos.size(); i++) {
                                    if (i == 0) {
                                        out.println("<div class=\"item active\">");
                                    } else {
                                        out.println("<div class=\"item\">");
                                    }
                                    out.println("<a href=\"produto.jsp?sku=" + produtosPromovidos.get(i).getSku() + "\">");
                                    out.println("<img src=\"" + produtosPromovidos.get(i).getRefeBig() + "\" alt=\"\" />");
                                    
                                    out.println("<div class=\"carousel-caption\">");
                                    out.println("<h3>" + produtosPromovidos.get(i).getNome() + "</h3>");
                                    out.println("</div>");
                                    out.println("</a>");
                                    out.println("</div>");
                                }
                            %>
                        </div>
                        <a class="left carousel-control" href="#promovidos" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                        </a>
                        <a class="right carousel-control" href="#promovidos" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right"></span>
                        </a>
                    </div>
                </div>
            </div>

            <div class="row">
                <%
                    String cat = request.getParameter("cat");
                    // Se não recebeu o parâmetro "cat", assume a categoria 1.
                    // Ao entrar na página pela primeira vez, por exemplo,
                    //  o parâmetro "cat" não é recebido.
                    if (cat == null) {
                        cat = "0";
                    }
                    // Tenta converter a categoria recebida para Integer.
                    int categ;
                    try {
                        categ = Integer.parseInt(cat);
                    } catch (NumberFormatException nfe) {
                        categ = 0;
                    }

                    // Consulta os produtos da categoria.
                    ArrayList<ProdutoEstoque> d = produtoDao.consultarCategoriaQtdEstoque(categ, 9);
                    if (d.size() == 0) {
                        out.println("<div class=\"col-sm-12 col-lg-12 col-md-12\">Nenhum produto encontrado.</div>");
                    } else {
                        for (ProdutoEstoque p : d) {
                %>
                <div class="col-sm-4 col-lg-4 col-md-4">
                    <div class="thumbnail">
                        <img src="<%out.println(p.getRefe());%>" alt="">
                        <div class="caption">   
                            <h4><a href="produto.jsp?sku=<%=p.getSku()%>"><%out.println(p.getNome());%></a></h4>
                            <div class="row">
                                <div class="col-md-5">
                                    <span class="preco">R$<%out.println(p.getPrecoFormatado());%></span>
                                </div>
                                <div class="col-md-7 text-right">                               
                                    <%
                                        // Retirado exibiçao de descriçao do produto do index (Henrique 15/11 00:35)
                                        if (p.getQtdEstoque() > 0) {
                                    %>
                                    <a class="btn btn-default btn-xs" href="carrinho.jsp?sku=<%=p.getSku()%>">Adicionar ao carrinho</a>
                                    <%
                                    } else {
                                    %>
                                    <span class="label label-danger">Esgotado</span>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>
                        </div>
                    </div>     
                </div>
                <%
                        }
                    }
                %>
            </div>      
            <!-- Footer -->
            <footer>
                <div class="row">
                    <div class="col-lg-12">
                        <p>Copyright &copy; FUN MODE CHILD 2015</p>
                    </div>
                </div>
            </footer>
        </div>
    </div> <!-- /.row -->
</div> <!-- /.container -->

<%@include file="includes/footer.jsp"%>