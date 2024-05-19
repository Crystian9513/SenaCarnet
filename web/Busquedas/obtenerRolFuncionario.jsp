
<%@page import="entidades.RolFuncionario"%>
<%@page import="java.util.List"%>
<%@page import="controladores.RolFuncionarioJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("rolFuncionarioa"));

    RolFuncionarioJpaController controlador = new RolFuncionarioJpaController();
    List lista = controlador.findRolFuncionarioEntities();

    for (int i = 0; i < lista.size(); i++) {
        RolFuncionario tipo2 = (RolFuncionario) lista.get(i);

        if (tipo2.getCodigo() == id) {

            out.print("<option value='" + tipo2.getCodigo() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getCodigo() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>