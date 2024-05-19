<%@page import="entidades.AreaTrabajo"%>
<%@page import="java.util.List"%>
<%@page import="controladores.AreaTrabajoJpaController"%>
<%
    int id = Integer.parseInt(request.getParameter("areaTrabajo"));

    AreaTrabajoJpaController controlador = new AreaTrabajoJpaController();
    List lista = controlador.findAreaTrabajoEntities();

    for (int i = 0; i < lista.size(); i++) {
        AreaTrabajo tipo2 = (AreaTrabajo) lista.get(i);

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