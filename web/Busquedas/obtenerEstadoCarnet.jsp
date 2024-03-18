<%@page import="entidades.EstadoCarnet"%>
<%@page import="java.util.List"%>
<%@page import="controladores.EstadoCarnetJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("estadoCarnetTipo"));

    EstadoCarnetJpaController ses = new EstadoCarnetJpaController();
    List lista = ses.findEstadoCarnetEntities();

    for (int i = 0; i < lista.size(); i++) {
        EstadoCarnet tipo2 = (EstadoCarnet) lista.get(i);

        if (tipo2.getIdestadoCarnet() == id) {

            out.print("<option value='" + tipo2.getIdestadoCarnet() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdestadoCarnet() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>