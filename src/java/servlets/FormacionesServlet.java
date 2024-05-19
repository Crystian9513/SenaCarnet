/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import controladores.FormacionJpaController;
import controladores.SedeJpaController;
import entidades.Formacion;
import entidades.Sede;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
@WebServlet(name = "FormacionesServlet", urlPatterns = {"/FormacionesServlet"})
public class FormacionesServlet extends HttpServlet {
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
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "guardar":
                    botonGuardar(request, response);
                    break;
                case "actualizar":
                    botonEdicion(request, response);
                    break;
                case "eliminar":
                    botonElimina(request, response);
                    break;
                default:
                    // Acción no válida
                    break;
            }
        }
    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoGd"));
        String nombre = request.getParameter("nombreGd");
        int sedeListass = Integer.parseInt(request.getParameter("SedesListaGd"));

        FormacionJpaController for1 = new FormacionJpaController();
        Formacion guardaFormacion = new Formacion();
        SedeJpaController sedes = new SedeJpaController();

        try {

            if (for1.findFormacion(codigo) != null) {
                enviarRespuestaError(response, "¡Error! Ya existe un registro con ese Codigo.");
            } else {

                guardaFormacion.setIdFormacion(codigo);
                guardaFormacion.setNombre(nombre);
                Sede des = sedes.findSede(sedeListass);
                guardaFormacion.setSedeId(des);

                for1.create(guardaFormacion);

                enviarRespuestaExito(response, "¡Registro guardado exitosamente!");
            }

        } catch (Exception e) {
             enviarRespuestaError(response, "¡Error!");
        }

    }

    public void botonElimina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoOpEl"));
        FormacionJpaController fo = new FormacionJpaController();

        try {
            fo.destroy(codigo);
            enviarRespuestaExito(response, "¡Registro Eliminado exitosamente!");
        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error!");
        }

    }

    public void botonEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoOpEl"));
        String nombre = request.getParameter("nombreOpEl");
        int sedeListass = Integer.parseInt(request.getParameter("SedesListaOpEl"));

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
                enviarRespuestaExito(response, "¡Registro Editado exitosamente!");
            } catch (Exception e) {
               
                enviarRespuestaError(response, "¡Error!");
            }
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
