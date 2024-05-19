/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import controladores.CoordinadorJpaController;
import controladores.UsuariosJpaController;
import controladores.exceptions.NonexistentEntityException;
import entidades.Coordinador;
import entidades.Usuarios;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        String nombre = request.getParameter("nombreCorGd");
        String apellido = request.getParameter("apellidoCorGd");
        int cedula = Integer.parseInt(request.getParameter("cedulaCorGd"));
        String correo = request.getParameter("correoCorGd");

        CoordinadorJpaController controlador = new CoordinadorJpaController();
        Coordinador guardarCoor = new Coordinador();
        UsuariosJpaController controlUsuario = new UsuariosJpaController();
        Usuarios guardaUsuario = new Usuarios();

        String clave = request.getParameter("claveCorGd");
        String claveEncriptada = controlador.EncryptarClave(clave);

        try {

            if (controlador.findCoordinador(cedula) != null && controlUsuario.findUsuarios(cedula) != null) {
                enviarRespuestaError(response, "¡Error! Ya existe un registro con esa cédula.");
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

                enviarRespuestaExito(response, "¡Registro guardado exitosamente!");
            }

        } catch (Exception e) {
             enviarRespuestaError(response, "¡Error!");
        }

    }

    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NonexistentEntityException {

        int cedula = Integer.parseInt(request.getParameter("cedulaCd"));

        try {
            CoordinadorJpaController controlador = new CoordinadorJpaController();
            controlador.destroy(cedula);

            UsuariosJpaController controlUsuario = new UsuariosJpaController();
            controlUsuario.destroy(cedula);

            enviarRespuestaExito(response, "¡Registro Eliminado exitosamente!");
        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");
        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombreCd");
        String apellido = request.getParameter("apellidoCd");
        int cedula = Integer.parseInt(request.getParameter("cedulaCd"));
        String correo = request.getParameter("correoCd");

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
