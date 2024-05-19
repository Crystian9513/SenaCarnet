/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import controladores.AdministradorJpaController;
import controladores.UsuariosJpaController;
import controladores.exceptions.NonexistentEntityException;
import entidades.Administrador;
import entidades.Usuarios;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "guardar":
                    botonGuardar(request, response);
                    break;
                case "actualizar":
                    botonEditar(request, response);
                    break;
                case "eliminar":
                    botonEliminar(request, response);
                    break;
                default:
                    // Acción no válida
                    break;
            }
        }

    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombreGd");
        String apellido = request.getParameter("apellidoGd");
        int cedula = Integer.parseInt(request.getParameter("cedulaGd"));
        String correo = request.getParameter("correoGd");

        AdministradorJpaController controladorAdm = new AdministradorJpaController();
        Administrador guardarAdmin = new Administrador();
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        Usuarios guardaUsuario = new Usuarios();

        String clave = request.getParameter("claveGd");
        String claveEncriptada = controladorAdm.EncryptarClave(clave);

        try {
            if (controladorAdm.findAdministrador(cedula) != null && controlUsuario.findUsuarios(cedula) != null) {
               enviarRespuestaError(response, "¡Error! Ya existe un registro con esa cédula.");
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

             enviarRespuestaExito(response, "¡Registro guardado exitosamente!");
        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");
        }
    }

    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NonexistentEntityException {

        int cedula = Integer.parseInt(request.getParameter("cedulaEd"));

        try {
              AdministradorJpaController controlador = new AdministradorJpaController();
        controlador.destroy(cedula);

        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        controlUsuario.destroy(cedula);

        enviarRespuestaExito(response, "¡Registro Eliminado exitosamente!");
        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");
        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cedula = Integer.parseInt(request.getParameter("cedulaEd"));
        String nombre = request.getParameter("nombreEd");
        String apellido = request.getParameter("apellidoEd");
        String correo = request.getParameter("correoEd");

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

                 enviarRespuestaExito(response, "¡Registro Editado exitosamente!");
            } else {
                enviarRespuestaError(response, "¡Error!");
            }
        } catch (Exception e) {
             enviarRespuestaError(response, "¡Error!");
        }
    }
    
     // Método para enviar una respuesta JSON de éxito
    private void enviarRespuestaExito(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("estado", "exito");
        respuesta.put("mensaje", mensaje);

        String json = new Gson().toJson(respuesta);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // Método para enviar una respuesta JSON de error
    private void enviarRespuestaError(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("estado", "error");
        respuesta.put("mensaje", mensaje);

        String json = new Gson().toJson(respuesta);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
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
