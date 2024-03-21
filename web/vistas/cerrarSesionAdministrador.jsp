
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
 HttpSession sesion = request.getSession(); 
    sesion.removeAttribute("user");    
    sesion=null;
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies
    response.sendRedirect("../index.jsp");

%>
