<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="net.sf.jasperreports.engine.JasperPrint" %>
<%@ page contentType="application/pdf" pageEncoding="UTF-8"%>

<%
    // Establecer la conexión a la base de datos
    Connection conexion;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        conexion = DriverManager.getConnection("jdbc:mysql://localhost/senacarnet", "root", "27478426*cP");

        // Obtener el parámetro de la cédula
        String cedulaStr = request.getParameter("cedula");
        int cedula = Integer.parseInt(cedulaStr);

        // Crear un HashMap para almacenar los parámetros del informe
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("id", cedula);

        // Obtener la ruta del archivo Jasper
        String reportPath = application.getRealPath("reportes/carnetAtras.jasper");

        // Generar el informe PDF con los parámetros
        byte[] bytes = JasperRunManager.runReportToPdf(reportPath, parameters, conexion);

        // Configurar la respuesta HTTP para el PDF
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes, 0, bytes.length);
        response.getOutputStream().flush();

        // Cerrar la conexión a la base de datos
        conexion.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
