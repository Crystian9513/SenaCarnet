/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import controladores.UsuariosJpaController;
import entidades.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "RestablecimientoContrasenaServlet", urlPatterns = {"/RestablecimientoContrasenaServlet"})
public class RestablecimientoContrasenaServlet extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "buscar":
                    botonBusqueda(request, response);
                    break;
                case "restablecer":
                    botonRestablecer(request, response);
                    break;
                default:
                    // Acción no válida
                    break;
            }
        }

    }

    public void botonRestablecer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();
        int numeroCedula = Integer.parseInt(request.getParameter("CedulaRestablecer"));
        
        // Buscar al usuario por su número de cédula
        Usuarios usuario = controladorUsuario.findUsuarios(numeroCedula);

        if (usuario == null) {
            enviarRespuestaError(response, "¡Error! Usuario no encontrado!");
        } else {

            String nuevaContraseña = request.getParameter("CedulaRestablecer");

            // Encriptar la nueva contraseña (si es necesario)
            String contraseñaEncriptada = controladorUsuario.EncryptarClave(nuevaContraseña);

            // Actualizar la contraseña en el objeto del usuario
            usuario.setClaves(contraseñaEncriptada);
            usuario.setEstadoClave(1); // Esto asume que 2 es el estado para una contraseña restablecida

            // Guardar los cambios en la base de datos
            controladorUsuario.edit(usuario);

            enviarRespuestaExito(response, "¡Contraseña Restablecida!");
          
        }

}

protected void botonBusqueda(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();
        int numeroCedula = Integer.parseInt(request.getParameter("CedulaRestablecer"));
        Usuarios usuario = controladorUsuario.findUsuarios(numeroCedula);

        try {
              if (usuario != null) {
            enviarRespuestaExito(response, "¡Usuario Encontrado!");
        } else {
            enviarRespuestaError(response, "¡Error! Usuario no encontrado!");
        }
        } catch (Exception e) {
            
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

} catch (Exception ex) {
            Logger.getLogger(RestablecimientoContrasenaServlet.class  

.getName()).log(Level.SEVERE, null, ex);
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

} catch (Exception ex) {
            Logger.getLogger(RestablecimientoContrasenaServlet.class  

.getName()).log(Level.SEVERE, null, ex);
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
