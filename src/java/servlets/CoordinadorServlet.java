/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

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

        String boton = request.getParameter("action");

        switch (boton) {

            case "Editar":
                botonEditar(request, response);
                break;
            default:
                throw new AssertionError();
        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mensaje;

        int cedula = Integer.parseInt(request.getParameter("cedula2"));
        int tpdocumento = Integer.parseInt(request.getParameter("tipoDocumento2"));
        String nombres = request.getParameter("nombres2");
        String apellidos = request.getParameter("apellidos2");
        int formacion = Integer.parseInt(request.getParameter("formacion2"));
        int sede = Integer.parseInt(request.getParameter("sede2"));
        String correo = request.getParameter("correo2");
        String vencimiento = request.getParameter("vence2");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        int estado = Integer.parseInt(request.getParameter("estado2"));

        int cedula2 = Integer.parseInt(request.getParameter("cedula2"));//TABLA DE USUARIO
        String nombres2 = request.getParameter("nombres2");//TABLA DE USUARIO
        String apellidos2 = request.getParameter("apellidos2");//TABLA DE
        String clave = request.getParameter("cedula2");//TABLA DE USUARIO

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
                editarEstudiante.setVenceCarnet(formatoFecha.parse(vencimiento));
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
                mensaje = "edicionGuardad";

                response.sendRedirect("vistas/coordinador.jsp?respuesta=" + mensaje);
            }

        } catch (Exception e) {

            mensaje = "erroreditarr";
            response.sendRedirect("vistas/coordinador.jsp?respuesta=" + mensaje);
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
