/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.UsuariosJpaController;
import entidades.Usuarios;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "CambioContraseñaPrimeraVezServlet", urlPatterns = {"/CambioContrase_aPrimeraVezServlet"})
public class CambioContraseñaPrimeraVezServlet extends HttpServlet {

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

        String boton = request.getParameter("action");

        switch (boton) {
            case "Guardar":
                botonGuardar(request, response);
                break;

            default:
                throw new AssertionError();
        }

    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        String mensaje;

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();
        int numeroCedula = Integer.parseInt(request.getParameter("cedula"));
        // Buscar al usuario por su número de cédula
        Usuarios usuario = controladorUsuario.findUsuarios(numeroCedula);

        if (usuario == null) {
            // Si no se encuentra ningún usuario con la cédula proporcionada, redirigir a una página de error
            mensaje = "usuarioNoEncontrado";
            response.sendRedirect("vistas/cambioContrasenaUsuario.jsp?respuesta=" + mensaje);

        } else {
            String clave1 = request.getParameter("clave1");
            String clave2 = request.getParameter("clave2");

            if (!clave1.equals(clave2)) {
                // Las contraseñas no coinciden
                mensaje = "noCoinciden";

                // Redirigir de vuelta al formulario de cambio de contraseña
                response.sendRedirect("vistas/cambioContrasenaUsuario.jsp?respuesta=" + mensaje);
            }
            // Encontrado el usuario, ahora puedes cambiar su contraseña
            String contraseñaEncriptada = controladorUsuario.EncryptarClave(clave1);

            // Actualizar la contraseña en el objeto del usuario
            usuario.setClaves(contraseñaEncriptada);
            usuario.setEstadoClave(2);

            // Guardar los cambios en la base de datos
            controladorUsuario.edit(usuario);

            // Redirigir a una página de confirmación
            mensaje = "Guardada";
            response.sendRedirect("vistas/cambioContrasenaUsuario.jsp?respuesta=" + mensaje);
          
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
        } catch (Exception ex) {
            Logger.getLogger(CambioContraseñaPrimeraVezServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CambioContraseñaPrimeraVezServlet.class.getName()).log(Level.SEVERE, null, ex);
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
