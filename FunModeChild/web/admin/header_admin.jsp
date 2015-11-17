<%-- 
    Document   : header.jsp
    Created on : Nov 8, 2015, 2:25:18 PM
    Author     : sumlauf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>${param.titulo}</title>
        <!-- CSS -->
        <link rel="stylesheet" href="../assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="../assets/css/fmc.css">
    </head>
    <body>
        <!-- Navegação -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="admin.jsp">Inicio</a>
                </div>
                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="cadastrar.jsp">Cadastrar</a>                            
                        </li>
                        <li>
                            <a href="entrar.jsp">Entrar</a>
                        </li>
                        <li>
                            <a href="carrinho.jsp">Carrinho</a>
                        </li>                        
                    </ul>
                    <%
                        if (session.getAttribute("username") != null) {
                    %>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="./sair.jsp">Sair</a></li>                        
                    </ul>
                    <%
                        }
                    %>
                </div> <!-- /.navbar-collapse -->
            </div> <!-- /.container -->
        </nav>