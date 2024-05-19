/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import controladores.EstadoCarnetJpaController;
import controladores.EstudiantesJpaController;
import controladores.FormacionJpaController;
import controladores.InformacionCarnetJpaController;
import controladores.SedeJpaController;
import controladores.TipodocumentoJpaController;
import controladores.UsuariosJpaController;
import entidades.EstadoCarnet;
import entidades.Estudiantes;
import entidades.Formacion;
import entidades.InformacionCarnet;
import entidades.Sede;
import entidades.Tipodocumento;
import entidades.Usuarios;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
@WebServlet(name = "CoordinadorServlet", urlPatterns = {"/CoordinadorServlet"})
public class CoordinadorServlet extends HttpServlet {

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
                case "EliminarEstudiante":
                    botonEditar(request, response);
                    break;
                default:
                    // Acción no válida
                    break;
            }
        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cedula = Integer.parseInt(request.getParameter("cedulaEE"));
        int tpdocumento = Integer.parseInt(request.getParameter("tipoDocumentoEE"));
        String nombres = request.getParameter("nombresEE");
        String apellidos = request.getParameter("apellidosEE");
        int formacion = Integer.parseInt(request.getParameter("formacionEE"));
        int sede = Integer.parseInt(request.getParameter("sedeEE"));
        String correo = request.getParameter("correoEE");
        String vencimiento = request.getParameter("venceEE");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        int estado = Integer.parseInt(request.getParameter("estadoEE"));

        int cedula2 = Integer.parseInt(request.getParameter("cedulaEE"));//TABLA DE USUARIO
        String nombres2 = request.getParameter("nombresEE");//TABLA DE USUARIO
        String apellidos2 = request.getParameter("apellidosEE");//TABLA DE
        String clave = request.getParameter("cedulaEE");//TABLA DE USUARIO

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();//TABLA DE USUARIO
        Usuarios guardarUsuario = new Usuarios();//TABLA DE USUARIO
        String claveEncriptada = controladorUsuario.EncryptarClave(clave);

        EstudiantesJpaController controlador = new EstudiantesJpaController();
        Estudiantes editarEstudiante = controlador.findEstudiantes(cedula);

        TipodocumentoJpaController tipo = new TipodocumentoJpaController();
        FormacionJpaController tipoForma = new FormacionJpaController();
        SedeJpaController tipoSede = new SedeJpaController();
        EstadoCarnetJpaController tipoEstado = new EstadoCarnetJpaController();

        LocalDate fechaSistema = LocalDate.now();
        Date fechaSistemaDate = Date.from(fechaSistema.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        //TABLA IMFORMACION_CARNET
        InformacionCarnetJpaController controladorInfo = new InformacionCarnetJpaController();
        InformacionCarnet guardarInfo = new InformacionCarnet();

        try {

            if (editarEstudiante != null) {

                editarEstudiante.setCedula(cedula);
                Tipodocumento to = tipo.findTipodocumento(tpdocumento);
                editarEstudiante.setTipoDocumentoFk(to);
                editarEstudiante.setNombres(nombres);
                editarEstudiante.setApellidos(apellidos);
                Formacion form = tipoForma.findFormacion(formacion);
                editarEstudiante.setFormacionFk(form);
                Sede se = tipoSede.findSede(sede);
                editarEstudiante.setSedeFk(se);
                editarEstudiante.setCorreo(correo);
                EstadoCarnet car = tipoEstado.findEstadoCarnet(estado);
                editarEstudiante.setEstadoCarnetIdestadoCarnet(car);

                //TABLA USUARIO
                guardarUsuario.setCedula(cedula2);
                guardarUsuario.setNombres(nombres2);
                guardarUsuario.setApellidos(apellidos2);
                guardarUsuario.setClaves(claveEncriptada);
                guardarUsuario.setRol(4);
                
                guardarInfo.setCedula(cedula);
                guardarInfo.setNombres(nombres);
                guardarInfo.setApellidos(apellidos);
                guardarInfo.setFormacion(formacion);
                guardarInfo.setFechaEliminacion(fechaSistemaDate);
                
                controladorInfo.create(guardarInfo);

                controladorUsuario.edit(guardarUsuario);
                controlador.edit(editarEstudiante);
                enviarRespuestaExito(response, "¡Registro editado exitosamente!");
            }

        } catch (Exception e) {

            enviarRespuestaError(response, "¡Error al Editar el registro!");
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
