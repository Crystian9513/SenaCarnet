/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.LogoJpaController;
import entidades.Logo;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "LogoServlet", urlPatterns = {"/LogoServlet"})
public class LogoServlet extends HttpServlet {

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
            throws ServletException, IOException, ParseException {

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
            throws ServletException, IOException, ParseException {

        String mensaje;

        int idLogo = Integer.parseInt(request.getParameter("id"));
        Part logo = request.getPart("logo");
        String nombre = request.getParameter("nombre");
        Part firma = request.getPart("firma");

        LogoJpaController controlador = new LogoJpaController();
        Logo actualizar;

        byte[] nuevoLogo = null;
        byte[] nuevaFirma = null;

        if (idLogo == 0) {
            // Crear un nuevo registro
            actualizar = new Logo();
        } else {
            // Actualizar un registro existente
            actualizar = controlador.findLogo(idLogo);
            if (actualizar == null) {
                // Si no se encuentra el registro, redirigir con mensaje de error
                mensaje = "Erroraleditar";
                response.sendRedirect("vistas/menuPrincipal.jsp?respuesta=" + mensaje);
                return;
            }
        }

        // Recuperar los valores existentes antes de realizar cambios
        byte[] imagenExistente = actualizar.getImagen();
        byte[] firmaExistente = actualizar.getFirma();
        String directorExistente = actualizar.getDirector();

        // Verificar si se proporcionó una nueva foto de logo
        if (logo != null && logo.getSize() > 0) {
            nuevoLogo = logo.getInputStream().readAllBytes();
            actualizar.setImagen(nuevoLogo);
        } else {
            // Si no se proporciona una nueva foto de logo, conservar la existente
            actualizar.setImagen(imagenExistente);
        }

        // Verificar si se proporcionó una nueva foto de firma
        if (firma != null && firma.getSize() > 0) {
            nuevaFirma = firma.getInputStream().readAllBytes();
            actualizar.setFirma(nuevaFirma);
        } else {
            // Si no se proporciona una nueva foto de firma, conservar la existente
            actualizar.setFirma(firmaExistente);
        }

        // Establecer el nombre del director
        if (nombre != null && !nombre.isEmpty()) {
            // Si se proporciona un nuevo nombre, actualizarlo
            actualizar.setDirector(nombre);
        } else {
            // Si no se proporciona un nuevo nombre, conservar el existente
            actualizar.setDirector(directorExistente);
        }

        try {
            controlador.edit(actualizar);
            mensaje = "guardado";
            response.sendRedirect("vistas/menuPrincipal.jsp?respuesta=" + mensaje);
        } catch (Exception ex) {
            Logger.getLogger(LogoServlet.class.getName()).log(Level.SEVERE, null, ex);
            mensaje = "Erroraleditar";
            response.sendRedirect("vistas/menuPrincipal.jsp?respuesta=" + mensaje);
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
        } catch (ParseException ex) {
            Logger.getLogger(LogoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException ex) {
            Logger.getLogger(LogoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
