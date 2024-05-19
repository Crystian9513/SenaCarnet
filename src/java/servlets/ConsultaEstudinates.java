/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controladores.EstadoCarnetJpaController;
import controladores.EstudiantesJpaController;
import controladores.FormacionJpaController;
import controladores.SedeJpaController;
import controladores.TipodocumentoJpaController;
import entidades.EstadoCarnet;
import entidades.Estudiantes;
import entidades.Formacion;
import entidades.Sede;
import entidades.Tipodocumento;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "ConsultaEstudinates", urlPatterns = {"/ConsultaEstudinates"})
public class ConsultaEstudinates extends HttpServlet {

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

        cargarTabla(request, response);
    }

    protected void cargarTabla(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        // Lógica para consultar los datos de los estudiantes
        EstudiantesJpaController estudiantesController = new EstudiantesJpaController();
        List<Estudiantes> estudiantes = estudiantesController.findEstudiantesEntities();

        // Crear una lista para almacenar objetos JSON personalizados de estudiantes
        List<JsonObject> estudiantesJson = new ArrayList<>();

        // Lógica para consultar los datos de formaciones y sedes
        FormacionJpaController formacionController = new FormacionJpaController();
        SedeJpaController sedeController = new SedeJpaController();
        TipodocumentoJpaController documentoControlador = new TipodocumentoJpaController();
        EstadoCarnetJpaController estadoControlador = new EstadoCarnetJpaController();

        // Iterar sobre la lista de estudiantes y crear objetos JSON personalizados
        for (Estudiantes estudiante : estudiantes) {
            // Crear un nuevo objeto JSON personalizado con los datos del estudiante
            JsonObject estudianteJson = new JsonObject();
            estudianteJson.addProperty("cedula", estudiante.getCedula());
            estudianteJson.addProperty("nombres", estudiante.getNombres());
            estudianteJson.addProperty("apellidos", estudiante.getApellidos());

            // Para el tipo de documento
            Tipodocumento tipo = documentoControlador.findTipodocumento(estudiante.getTipoDocumentoFk().getIdTipoDocumento());
            if (tipo != null) {
                estudianteJson.addProperty("tipoDocumentoId", tipo.getIdTipoDocumento());
                estudianteJson.addProperty("tipoDocumentoNombre", tipo.getNombre());
            }

            // Obtener el objeto de formación del estudiante y agregar sus datos al JSON
            Formacion formacion = formacionController.findFormacion(estudiante.getFormacionFk().getIdFormacion());
            if (formacion != null) {
                estudianteJson.addProperty("formacionId", formacion.getIdFormacion());
                estudianteJson.addProperty("formacionNombre", formacion.getNombre());
            }

            // Obtener el objeto de sede del estudiante y agregar sus datos al JSON
            Sede sede = sedeController.findSede(estudiante.getSedeFk().getIdSede());
            if (sede != null) {
                estudianteJson.addProperty("sedeId", sede.getIdSede());
                estudianteJson.addProperty("sedeNombre", sede.getNombre());
            }

            estudianteJson.addProperty("correo", estudiante.getCorreo());
            // Obtener la fecha de vencimiento del carnet
            Date fechaVencimiento = estudiante.getVenceCarnet();
            if (fechaVencimiento != null) {
                // Formatear la fecha como una cadena
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fechaVencimientoStr = dateFormat.format(fechaVencimiento);
                // Agregar la fecha al JSON
                estudianteJson.addProperty("fechaVencimientoCarnet", fechaVencimientoStr);
            }

            estudianteJson.addProperty("rh", estudiante.getRh());

            EstadoCarnet estadoCarnet = estadoControlador.findEstadoCarnet(estudiante.getEstadoCarnetIdestadoCarnet().getIdestadoCarnet());
            if (estadoCarnet != null) {
                estudianteJson.addProperty("estadoCarnetId", estadoCarnet.getIdestadoCarnet());
                estudianteJson.addProperty("estadoCarnetNombre", estadoCarnet.getNombre());
            }

            // Agregar el objeto JSON personalizado a la lista
            estudiantesJson.add(estudianteJson);
        }

        // Convertir la lista de objetos JSON personalizados a una cadena JSON
        String json = new Gson().toJson(estudiantesJson);

        // Enviar la respuesta JSON al cliente
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
