<%-- 
    Document   : sair.jsp
    Created on : Nov 12, 2015, 10:10:52 AM
    Author     : gabriel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  session.removeAttribute("username");
  session.removeAttribute("nome");
  
  response.sendRedirect("./");
%>
