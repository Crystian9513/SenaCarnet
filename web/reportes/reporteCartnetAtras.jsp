<%@page import="net.sf.jasperreports.engine.JasperPrintManager"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="net.sf.jasperreports.engine.JasperPrint" %>
<%@ page import="net.sf.jasperreports.engine.export.JRGraphics2DExporter" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page contentType="multipart/x-mixed-replace;boundary=myboundary" pageEncoding="UTF-8"%>

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

        // Generar el informe Jasper
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, parameters, conexion);

        // Generar las imágenes de todas las páginas del informe
        final String extension = "jpg";
        final float zoom = 1f;
        int pageCount = jasperPrint.getPages().size();
        for (int i = 0; i < pageCount; i++) {
            BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, i, zoom);

            // Convertir la imagen a bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, extension, outputStream);
            byte[] imageBytes = outputStream.toByteArray();

            // Configurar la respuesta HTTP para la imagen
            response.setContentType("image/jpeg");
            response.setContentLength(imageBytes.length);

            // Enviar la imagen al cliente
            response.getOutputStream().write(imageBytes);
            response.getOutputStream().flush();

            // Si no es la última página, enviar un separador para indicar el final de la imagen
            if (i < pageCount - 1) {
                response.getOutputStream().println("--myboundary");
                response.getOutputStream().println("Content-Type: image/jpeg");
                response.getOutputStream().println();
                response.getOutputStream().flush();
            }

            // Cerrar el ByteArrayOutputStream de la imagen individual
            outputStream.close();
        }

        // Cerrar la conexión a la base de datos
        conexion.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
