/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.CoordinadorJpaController;
import controladores.UsuariosJpaController;
import controladores.exceptions.NonexistentEntityException;
import entidades.Coordinador;
import entidades.Usuarios;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Peralta
 */
@MultipartConfig //Para archivos siempre va esto
@WebServlet(name = "CoordinadorDatosServlet", urlPatterns = {"/CoordinadorDatosServlet"})
public class CoordinadorDatosServlet extends HttpServlet {

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
            throws ServletException, IOException, NonexistentEntityException {

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

            default:
                throw new AssertionError();
        }

    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        int cedula = Integer.parseInt(request.getParameter("cedula"));
        String correo = request.getParameter("correo");

        CoordinadorJpaController controlador = new CoordinadorJpaController();
        Coordinador guardarCoor = new Coordinador();
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        Usuarios guardaUsuario = new Usuarios();

        String clave = request.getParameter("clave");
        String claveEncriptada = controlador.EncryptarClave(clave);

        try {

            if (controlador.findCoordinador(cedula) != null && controlUsuario.findUsuarios(cedula) != null) {
                String mensaje = "existe";
                response.sendRedirect("vistas/coordinadorDatos.jsp?respuesta=" + mensaje);
                return; // Se agrega un return para terminar la ejecución del método
            } else {

                guardarCoor.setNombre(nombre);
                guardarCoor.setApellido(apellido);
                guardarCoor.setCedula(cedula);
                guardarCoor.setClave(clave);
                guardarCoor.setCorreo(correo);
                controlador.create(guardarCoor);

                guardaUsuario.setCedula(cedula);
                guardaUsuario.setNombres(nombre);
                guardaUsuario.setApellidos(apellido);
                guardaUsuario.setClaves(claveEncriptada);
                guardaUsuario.setRol(3);
                guardaUsuario.setEstadoClave(2);
                controlUsuario.create(guardaUsuario);

                String mensaje = "guardado";
                response.sendRedirect("vistas/coordinadorDatos.jsp?respuesta=" + mensaje);

            }

        } catch (Exception e) {
            String mensaje = "errorAlguardar";
            response.sendRedirect("vistas/coordinadorDatos.jsp?respuesta=" + mensaje);
        }

    }

    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NonexistentEntityException {

        String mensaje;
        int cedula = Integer.parseInt(request.getParameter("cedula2"));

        CoordinadorJpaController controlador = new CoordinadorJpaController();
        controlador.destroy(cedula);

        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        controlUsuario.destroy(cedula);

        mensaje = "eliminado";
         response.sendRedirect("vistas/coordinadorDatos.jsp?respuesta=" + mensaje);
      
    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre2");
        String apellido = request.getParameter("apellido2");
        int cedula = Integer.parseInt(request.getParameter("cedula2"));
        String correo = request.getParameter("correo2");

        CoordinadorJpaController controlador = new CoordinadorJpaController();
        UsuariosJpaController controlUsuario = new UsuariosJpaController();

        try {
            Coordinador coordinadorExistente = controlador.findCoordinador(cedula);
            Usuarios usuarioExistente = controlUsuario.findUsuarios(cedula);

            if (coordinadorExistente != null && usuarioExistente != null) {
                // Si existen, actualizamos la información
                coordinadorExistente.setNombre(nombre);
                coordinadorExistente.setApellido(apellido);
                coordinadorExistente.setCorreo(correo);
                controlador.edit(coordinadorExistente);

                usuarioExistente.setNombres(nombre);
                usuarioExistente.setApellidos(apellido);

                controlUsuario.edit(usuarioExistente);

                String mensaje = "editado";
                response.sendRedirect("vistas/coordinadorDatos.jsp?respuesta=" + mensaje);
            } else {
                String mensaje = "noExiste";
                response.sendRedirect("vistas/coordinadorDatos.jsp?respuesta=" + mensaje);
            }
        } catch (Exception e) {
            String mensaje = "errorAlEditar";
            response.sendRedirect("vistas/coordinadorDatos.jsp?respuesta=" + mensaje);
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
        try {
            processRequest(request, response);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CoordinadorDatosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CoordinadorDatosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
