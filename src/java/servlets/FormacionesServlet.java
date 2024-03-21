/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.FormacionJpaController;
import controladores.SedeJpaController;
import entidades.Formacion;
import entidades.Sede;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
@MultipartConfig //Para archivos siempre va esto
@WebServlet(name = "FormacionesServlet", urlPatterns = {"/FormacionesServlet"})
public class FormacionesServlet extends HttpServlet {

    private String[] extensiones = {".csv"}; // Lista de extensiones permitidas

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
                botonElimina(request, response);
                break;
            case "Editar":
                botonEdicion(request, response);
            case "Importar2":
                botonImportar(request, response);
                break;
                
            default:

        }
    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mensaje;

        int codigo = Integer.parseInt(request.getParameter("codigo2"));
        String nombre = request.getParameter("nombre2");
        int sedeListass = Integer.parseInt(request.getParameter("SedesLista"));

        FormacionJpaController for1 = new FormacionJpaController();
        Formacion guardaFormacion = new Formacion();
        SedeJpaController sedes = new SedeJpaController();

        try {

            if (for1.findFormacion(codigo) != null) {
                mensaje = "existeFormacion";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            } else {

                guardaFormacion.setIdFormacion(codigo);
                guardaFormacion.setNombre(nombre);
                Sede des = sedes.findSede(sedeListass);
                guardaFormacion.setSedeId(des);

                for1.create(guardaFormacion);

                mensaje = "guardadoFormacion";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);

            }

        } catch (Exception e) {
            mensaje = "errorformacion";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        }

    }

    public void botonElimina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mensaje;

        int codigo = Integer.parseInt(request.getParameter("codigoEliminar2"));
        FormacionJpaController fo = new FormacionJpaController();

        try {
            fo.destroy(codigo);
            mensaje = "FormacionEliminada";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        } catch (Exception e) {
            mensaje = "errorEliminada";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        }

    }

    public void botonEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoEliminar2"));
        String nombre = request.getParameter("nombreEliminar2");
        int sedeListass = Integer.parseInt(request.getParameter("SedesLista2"));

        FormacionJpaController se = new FormacionJpaController();
        Formacion formacionEditada = se.findFormacion(codigo);
        SedeJpaController sedes = new SedeJpaController();

        if (formacionEditada != null) {
            // Actualizar los datos de la sede
            formacionEditada.setNombre(nombre);
            Sede des = sedes.findSede(sedeListass);
            formacionEditada.setSedeId(des);
            try {
                se.edit(formacionEditada);
                String mensaje = "Ediciónguardada";
                // Codificar el mensaje de respuesta
                mensaje = URLEncoder.encode(mensaje, "UTF-8");
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            } catch (Exception e) {
                String mensaje = "erroreditarr";

                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            }
        }
    }

   protected void botonImportar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file2"); // Obtener el archivo cargado desde la solicitud
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
                String sql = "INSERT INTO formacion (ID_FORMACION,NOMBRE,SEDE_ID) VALUES (?,?,?)";
                PreparedStatement statement = conn.prepareStatement(sql);

                // Leer el archivo CSV y guardar los datos en la base de datos
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length >= 3) { // Asegúrate de tener suficientes columnas según tu tabla
                        statement.setInt(1, Integer.parseInt(data[0]));
                        statement.setString(2, data[1]);
                        statement.setInt(3, Integer.parseInt(data[2]));
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
