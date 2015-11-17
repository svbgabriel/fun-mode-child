<%-- 
    Document   : entrar
    Created on : 15/11/2015, 11:41:39
    Author     : Henrique
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="includes/header.jsp">
    <jsp:param name="titulo" value="Entrar" />
</jsp:include>

<div class="container mg-btm-30">
    <img src="assets/img/logo.png" alt="Fun Mode Child" />
    <h1>Entrar</h1>
    <form action="entresp.jsp" method="post">

        <div class="row">
            <div class="col-md-2">
                <div class="form-group">
                    <label for="usuario">Usuário</label>
                    <input type="text" class="form-control" id="usuario" name="usuario" maxlength="18">
                </div>
            </div>
        </div>
        <div class="row">

            <div class="col-md-2">  
                <div class="form-group">
                    <label for="nome">Senha</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="**********" required="required">
                </div>
            </div>

        </div>    

        <button type="submit" class="btn btn-primary">Entrar</button>
    </form>
</div>
<%@include file="includes/footer.jsp"%>