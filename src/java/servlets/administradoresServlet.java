/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.AdministradorJpaController;
import controladores.UsuariosJpaController;
import controladores.exceptions.NonexistentEntityException;
import entidades.Administrador;
import entidades.Usuarios;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "administradoresServlet", urlPatterns = {"/administradoresServlet"})
public class administradoresServlet extends HttpServlet {

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

        AdministradorJpaController controladorAdm = new AdministradorJpaController();
        Administrador guardarAdmin = new Administrador();
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        Usuarios guardaUsuario = new Usuarios();

        String clave = request.getParameter("clave");
        String claveEncriptada = controladorAdm.EncryptarClave(clave);

        try {
            if (controladorAdm.findAdministrador(cedula) != null && controlUsuario.findUsuarios(cedula) != null) {
                String mensaje = "existe";
                response.sendRedirect("vistas/administrador.jsp?respuesta=" + mensaje);
                return; // Se agrega un return para terminar la ejecución del método
            }

            guardarAdmin.setNombre(nombre);
            guardarAdmin.setApellido(apellido);
            guardarAdmin.setCedula(cedula);
            guardarAdmin.setClave(clave);
            guardarAdmin.setCorreo(correo);
            controladorAdm.create(guardarAdmin);

            guardaUsuario.setCedula(cedula);
            guardaUsuario.setNombres(nombre);
            guardaUsuario.setApellidos(apellido);
            guardaUsuario.setClaves(claveEncriptada);
            guardaUsuario.setRol(2);
            guardaUsuario.setEstadoClave(2);
            controlUsuario.create(guardaUsuario);

            String mensaje = "guardado";
            response.sendRedirect("vistas/administrador.jsp?respuesta=" + mensaje);
        } catch (Exception e) {
            String mensaje = "errorAlguardar";
            response.sendRedirect("vistas/administrador.jsp?respuesta=" + mensaje);
        }
    }

    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NonexistentEntityException {

        String mensaje;
        int cedula = Integer.parseInt(request.getParameter("cedula2"));

        AdministradorJpaController controlador = new AdministradorJpaController();
        controlador.destroy(cedula);

        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        controlUsuario.destroy(cedula);

        mensaje = "eliminado";
        response.sendRedirect("vistas/administrador.jsp?respuesta=" + mensaje);

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cedula = Integer.parseInt(request.getParameter("cedula2"));
        String nombre = request.getParameter("nombre2");
        String apellido = request.getParameter("apellido2");
        String correo = request.getParameter("correo2");

        AdministradorJpaController controladorAdm = new AdministradorJpaController();
        Administrador adminExistente = controladorAdm.findAdministrador(cedula);

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();
        Usuarios usuarioExistente = controladorUsuario.findUsuarios(cedula);

        try {
            if (adminExistente != null && usuarioExistente != null) {
                // Si el administrador y el usuario existen, proceder con la edición
                // Actualizar los campos del administrador
                adminExistente.setNombre(nombre);
                adminExistente.setApellido(apellido);
                adminExistente.setCorreo(correo);
                adminExistente.setCedula(cedula);

                // Actualizar los campos del usuario
                usuarioExistente.setNombres(nombre);
                usuarioExistente.setApellidos(apellido);
                usuarioExistente.setCedula(cedula);
                usuarioExistente.setRol(2);

                // Actualizar otros campos según sea necesario
                // Guardar los cambios en la base de datos
                controladorAdm.edit(adminExistente);
                controladorUsuario.edit(usuarioExistente);

                String mensaje = "editado";
                response.sendRedirect("vistas/administrador.jsp?respuesta=" + mensaje);
            } else {
                // Si el administrador o el usuario no existen, manejar el error
                String mensaje = "Error al editar: no se encontró el administrador o el usuario";
                response.sendRedirect("vistas/administrador.jsp?respuesta=" + mensaje);
            }
        } catch (Exception e) {
            // Imprimir el seguimiento de la pila para depuración
            e.printStackTrace();
            String mensaje = "Error al editar: " + e.getMessage();
            response.sendRedirect("vistas/administrador.jsp?respuesta=" + mensaje);
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
            Logger.getLogger(administradoresServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(administradoresServlet.class.getName()).log(Level.SEVERE, null, ex);
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
