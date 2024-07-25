/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Peralta
 */

@MultipartConfig
@WebServlet(name = "ImportacionServlet", urlPatterns = {"/ImportacionServlet"})
public class ImportacionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    String accion = request.getParameter("accion");
    if (accion != null) {
        switch (accion) {
            case "ImportarEstudiante":
                botonImportar(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
                break;
        }
    } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada.");
    }
}


    protected void botonImportar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file5"); // Obtener el archivo cargado desde la solicitud
        if (filePart != null) {
            try (InputStream inputStream = filePart.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/senacarnet", "root", "27478426*cP");
                 PreparedStatement statement = conn.prepareStatement(
                         "INSERT INTO estudiantes (CEDULA, TIPO_DOCUMENTO_FK, NOMBRES, APELLIDOS, FORMACION_FK, SEDE_FK, FOTOGRAFIA, CORREO, VENCE_CARNET, ESTADO_CARNET_IDESTADO_CARNET, RH) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                // Cargar el controlador JDBC para MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length >= 11) {
                        try {
                            statement.setInt(1, Integer.parseInt(data[0]));
                            statement.setInt(2, Integer.parseInt(data[1]));
                            statement.setString(3, data[2]);
                            statement.setString(4, data[3]);
                            statement.setInt(5, Integer.parseInt(data[4]));
                            statement.setInt(6, Integer.parseInt(data[5]));
                            statement.setString(7, data[6]);
                            statement.setString(8, data[7]);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date date = sdf.parse(data[8]);
                            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                            statement.setDate(9, sqlDate);

                            statement.setInt(10, Integer.parseInt(data[9]));
                            statement.setString(11, data[10]);

                            statement.executeUpdate();
                            System.out.println("Registro insertado en la base de datos: " + Arrays.toString(data));
                        } catch (Exception e) {
                            System.err.println("Error al procesar el registro: " + Arrays.toString(data));
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Advertencia: El registro no tiene suficientes columnas.");
                    }
                }

                System.out.println("Procesamiento del archivo completado con éxito.");
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=guardarAprendiz");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=ErrorImportacion");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se ha proporcionado un archivo.");
        }
    }


     protected void botonImportar2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file5"); // Obtener el archivo cargado desde la solicitud
        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream(); // Obtener el flujo de entrada del archivo

            try {
                // Iniciando procesamiento del archivo
                System.out.println("Iniciando procesamiento del archivo...");

                // Cargar el controlador JDBC para MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establecer conexión con la base de datos
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/senacarnet", "root", "27478426*cP");
                System.out.println("Conexión establecida con la base de datos...");

                // Crear un lector de CSV
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                // Preparar la consulta para insertar datos en la base de datos
                String sql = "INSERT INTO estudiantes (CEDULA, TIPO_DOCUMENTO_FK, NOMBRES, APELLIDOS, FORMACION_FK, SEDE_FK, FOTOGRAFIA, CORREO, VENCE_CARNET, ESTADO_CARNET_IDESTADO_CARNET,RH) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
                PreparedStatement statement = conn.prepareStatement(sql);

                // Leer el archivo CSV y guardar los datos en la base de datos
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length >= 10) { // Asegúrate de tener suficientes columnas según tu tabla
                        statement.setInt(1, Integer.parseInt(data[0]));
                        statement.setInt(2, Integer.parseInt(data[1]));
                        statement.setString(3, data[2]);
                        statement.setString(4, data[3]);
                        statement.setInt(5, Integer.parseInt(data[4]));
                        statement.setInt(6, Integer.parseInt(data[5]));
                        statement.setString(7, data[6]);
                        statement.setString(8, data[7]);

                        // Convertir la fecha de texto a un objeto java.sql.Date
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Ajusta el formato según el formato de tu fecha en el CSV
                        java.util.Date date = sdf.parse(data[8]); // Parsear la fecha de texto a un objeto java.util.Date
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime()); // Convertir el objeto java.util.Date a java.sql.Date

                        // Asignar la fecha a los parámetros de la consulta
                        statement.setDate(9, sqlDate);

                        statement.setInt(10, Integer.parseInt(data[9]));

                        statement.setString(11, data[10]);

                        statement.executeUpdate();
                        System.out.println("Registro insertado en la base de datos: " + Arrays.toString(data));
                    } else {
                        // Imprimir un mensaje de advertencia si el registro no tiene suficientes columnas
                        System.out.println("Advertencia: El registro no tiene suficientes columnas.");
                    }
                }

                // Cerrar recursos
                statement.close();
                conn.close();
                reader.close();

                // Imprimir mensaje de éxito en la consola
                System.out.println("Procesamiento del archivo completado con éxito.");
                String mensaje = "guardarAprendiz";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);

            } catch (Exception e) {
                String mensaje = "ErrorImportacion";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
