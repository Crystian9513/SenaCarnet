

<%@page import="entidades.Formacion"%>
<%@page import="controladores.FormacionJpaController"%>
<%@page import="java.util.List"%>

<%
    int id = Integer.parseInt(request.getParameter("formacionesTipos"));

    FormacionJpaController ses = new FormacionJpaController();
    List lista = ses.findFormacionEntities();

    for (int i = 0; i < lista.size(); i++) {
        Formacion tipo2 = (Formacion) lista.get(i);

        if (tipo2.getIdFormacion() == id) {

            out.print("<option value='" + tipo2.getIdFormacion() + "' selected>");
            out.print(tipo2.getNombre());
            out.print("</option>");
        } else {
            out.print("<option value='" + tipo2.getIdFormacion() + "' >");
            out.print(tipo2.getNombre());
            out.print("</option>");

        }

    }
%>