/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.SedeJpaController;
import entidades.Sede;
import java.io.BufferedReader;
import java.io.File;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Peralta
 */
@MultipartConfig //Para archivos siempre va esto
@WebServlet(name = "SedesServlet", urlPatterns = {"/SedesServlet"})
public class SedesServlet extends HttpServlet {

 
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
        String boton = request.getParameter("action");

        switch (boton) {
            case "Guardar":
                botonGuardar(request, response);
                break;
            case "Eliminar":
                botonEliminar(request, response);
                break;
            case "Editar":
                botonEditar(request, response);
                break;
            case "Importar":
                botonImportar(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String nombre = request.getParameter("nombre");

        SedeJpaController se = new SedeJpaController();
        Sede guardaSede = new Sede();

        try {
            // Verificar si el código ya existe en la base de datos
            if (se.findSede(codigo) != null) {
                // El código ya existe, enviar notificación
                String mensaje = "Existe";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            } else {
                // El código no existe, proceder con la creación de la sede
                guardaSede.setIdSede(codigo);
                guardaSede.setNombre(nombre);

                se.create(guardaSede);
                String mensaje = "Guardado";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            }
        } catch (Exception e) {
            String mensaje = "errorguardar";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);

        }
    }

    protected void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje;
        try {

            int codigo = Integer.parseInt(request.getParameter("codigoEliminar"));

            SedeJpaController sedeController = new SedeJpaController();
            sedeController.destroy(codigo);

            mensaje = "SedeEliminada";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        } catch (Exception e) {
            mensaje = "ErrorEliminada";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoEliminar"));
        String nombre = request.getParameter("nombreEliminar");

        SedeJpaController sedeController = new SedeJpaController();
        Sede sedeExistente = sedeController.findSede(codigo);

        try {
            if (sedeExistente != null) {
                // La sede existe, proceder con la edición
                sedeExistente.setNombre(nombre);
                sedeController.edit(sedeExistente);

                String mensaje = "Editado";

                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            }
        } catch (Exception e) {
            String mensaje = "erroreditar";

            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        }
    }

   protected void botonImportar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file"); // Obtener el archivo cargado desde la solicitud
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
                String sql = "INSERT INTO sede (NOMBRE) VALUES (?)";
                PreparedStatement statement = conn.prepareStatement(sql);

                // Leer el archivo CSV y guardar los datos en la base de datos
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length >= 1) { // Asegúrate de tener suficientes columnas según tu tabla
                        statement.setString(1, data[0]);
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
                String mensaje = "ArchivosImportados";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);

            } catch (Exception e) {
                 String mensaje = "ErrorImportacion";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
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
