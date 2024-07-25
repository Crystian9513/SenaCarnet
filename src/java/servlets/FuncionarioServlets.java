package servlets;

import com.google.gson.Gson;
import controladores.AreaTrabajoJpaController;
import controladores.EstadoCarnetJpaController;
import controladores.FuncionariosJpaController;
import controladores.RolFuncionarioJpaController;
import controladores.TipodocumentoJpaController;
import controladores.UsuariosJpaController;
import entidades.AreaTrabajo;
import entidades.EstadoCarnet;
import entidades.Funcionarios;
import entidades.RolFuncionario;
import entidades.Tipodocumento;
import entidades.Usuarios;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.servlet.http.Part;

/**
 *
 * @author Peralta
 */
@MultipartConfig //Para archivos siempre va esto
@WebServlet(name = "FuncionarioServlets", urlPatterns = {"/FuncionarioServlets"})
public class FuncionarioServlets extends HttpServlet {

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
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "guardar":
                    botonGuardar(request, response);
                    break;
                case "actualizar":
                    botonActualizar(request, response);
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
            throws ServletException, IOException, ParseException {

        int cedula = Integer.parseInt(request.getParameter("cedulaFun"));
        int tpdocumento = Integer.parseInt(request.getParameter("tipoDocumentoFun"));
        String nombres = request.getParameter("nombresFun");
        String apellidos = request.getParameter("apellidosFun");
        int rol = Integer.parseInt(request.getParameter("RolFun"));
        int area = Integer.parseInt(request.getParameter("AreaFun"));
        String correo = request.getParameter("correoFun");
        Part part = request.getPart("fotoFun");

        String fechaInicio = request.getParameter("venceFun");
        String rh = request.getParameter("rhFun");
        int estado = Integer.parseInt(request.getParameter("estadoFun"));
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha1 = formato.parse(fechaInicio);
        String centro = request.getParameter("centroGd");
        String regional = request.getParameter("regionalGd");

        int cedula2 = Integer.parseInt(request.getParameter("cedulaFun"));//TABLA DE USUARIO
        String nombres2 = request.getParameter("nombresFun");//TABLA DE USUARIO
        String apellidos2 = request.getParameter("apellidosFun");//TABLA DE USUARIO
        String clave = request.getParameter("cedulaFun");//TABLA DE USUARIO

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();//TABLA DE USUARIO
        Usuarios guardarUsuario = new Usuarios();//TABLA DE USUARIO

        String claveEncriptada = controladorUsuario.EncryptarClave(clave);

        FuncionariosJpaController controlador = new FuncionariosJpaController();
        Funcionarios guardarFuncionario = new Funcionarios();

        TipodocumentoJpaController tipo = new TipodocumentoJpaController();
        RolFuncionarioJpaController tipoRol = new RolFuncionarioJpaController();
        AreaTrabajoJpaController tipoArea = new AreaTrabajoJpaController();
        EstadoCarnetJpaController tipoEstado = new EstadoCarnetJpaController();

        try {

            if (controlador.findFuncionarios(cedula) != null) {

                enviarRespuestaError(response, "¡Error! Ya existe un registro con esa cédula.");
                return; // Se agrega un return para terminar la ejecución del método

            }
            if (part == null) {

                return;
            }

            byte[] imagenBytes = part.getInputStream().readAllBytes();

            guardarFuncionario.setCedula(cedula);
            Tipodocumento to = tipo.findTipodocumento(tpdocumento);
            guardarFuncionario.setTipodocumentoIDTIPODOCUMENTO(to);
            guardarFuncionario.setNombres(nombres);
            guardarFuncionario.setApellidos(apellidos);
            RolFuncionario ro = tipoRol.findRolFuncionario(rol);
            guardarFuncionario.setRolfuncionarioCODIGO(ro);
            AreaTrabajo are = tipoArea.findAreaTrabajo(area);
            guardarFuncionario.setAreatrabajoCODIGO(are);
            guardarFuncionario.setCorreo(correo);
            guardarFuncionario.setVenceCarnet(fecha1);
            guardarFuncionario.setRh(rh);
            EstadoCarnet car = tipoEstado.findEstadoCarnet(estado);
            guardarFuncionario.setEstadocarnetIDESTADOCARNET(car);
            guardarFuncionario.setFotografia(imagenBytes); // Guardar la imagen en el campo BLOB
            guardarFuncionario.setCentroId(centro);
            guardarFuncionario.setRegionalId(regional);

            //TABLA USUARIO
            guardarUsuario.setCedula(cedula2);
            guardarUsuario.setNombres(nombres2);
            guardarUsuario.setApellidos(apellidos2);
            guardarUsuario.setClaves(claveEncriptada);
            guardarUsuario.setRol(4);
            guardarUsuario.setEstadoClave(1);

            controlador.create(guardarFuncionario);
            controladorUsuario.create(guardarUsuario);

            enviarRespuestaExito(response, "¡Registro guardado exitosamente!");

        } catch (Exception e) {

            enviarRespuestaError(response, "¡Error al guardar el registro!");
        }
    }

    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigoUsuario = Integer.parseInt(request.getParameter("cedulaFunEl"));

        try {

            FuncionariosJpaController controlador = new FuncionariosJpaController();
            UsuariosJpaController controladorUsuario = new UsuariosJpaController();//TABLA DE USUARIO

            // Obtener el estudiante a eliminar
            Funcionarios funcionario = controlador.findFuncionarios(codigoUsuario);

            // Verificar si el estudiante existe
            if (funcionario != null) {
                // Eliminar el estudiante de la base de datos
                controlador.destroy(codigoUsuario);
                controladorUsuario.destroy(codigoUsuario);

                enviarRespuestaExito(response, "¡Registro Eliminado exitosamente!");
            } else {
                // Manejar el caso en que el estudiante no existe
                enviarRespuestaError(response, "¡Error!");
            }

        } catch (Exception e) {

            enviarRespuestaError(response, "¡Error!");
        }

    }

    public void botonActualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        int cedula = Integer.parseInt(request.getParameter("cedulaFunEl"));
        int tpdocumento = Integer.parseInt(request.getParameter("tipoDocumentoFunEl"));
        String nombres = request.getParameter("nombresFunEl");
        String apellidos = request.getParameter("apellidosFunEl");
        int rol = Integer.parseInt(request.getParameter("RolFunEl"));
        int area = Integer.parseInt(request.getParameter("AreaFunEl"));
        String correo = request.getParameter("correoFunEl");
        Part part = request.getPart("fotoFunEl");
        byte[] nuevaFoto = null;
        String fechaInicio = request.getParameter("venceFunEl");
        String rh = request.getParameter("rhFunEl");
        int estado = Integer.parseInt(request.getParameter("estadoFunEl"));
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha1 = formato.parse(fechaInicio);
        String centro = request.getParameter("centroEd");
        String regional = request.getParameter("regionalEd");

        int cedula2 = Integer.parseInt(request.getParameter("cedulaFunEl"));
        String nombres2 = request.getParameter("nombresFunEl");
        String apellidos2 = request.getParameter("apellidosFunEl");
        String clave = request.getParameter("cedulaFunEl");

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();
        Usuarios guardarUsuario = controladorUsuario.findUsuarios(cedula2);

        String claveEncriptada = controladorUsuario.EncryptarClave(clave);

        FuncionariosJpaController controlador = new FuncionariosJpaController();
        Funcionarios guardarFuncionario = controlador.findFuncionarios(cedula);

        TipodocumentoJpaController tipo = new TipodocumentoJpaController();
        RolFuncionarioJpaController tipoRol = new RolFuncionarioJpaController();
        EstadoCarnetJpaController tipoEstado = new EstadoCarnetJpaController();
        AreaTrabajoJpaController tipoArea = new AreaTrabajoJpaController();

        if (part != null && part.getSize() > 0) {
            nuevaFoto = part.getInputStream().readAllBytes();
        }

        try {
            if (guardarFuncionario != null) {
                Tipodocumento to = tipo.findTipodocumento(tpdocumento);
                guardarFuncionario.setTipodocumentoIDTIPODOCUMENTO(to);
                guardarFuncionario.setNombres(nombres);
                guardarFuncionario.setApellidos(apellidos);
                RolFuncionario ro = tipoRol.findRolFuncionario(rol);
                guardarFuncionario.setRolfuncionarioCODIGO(ro);
                AreaTrabajo are = tipoArea.findAreaTrabajo(area);
                guardarFuncionario.setAreatrabajoCODIGO(are);
                guardarFuncionario.setCorreo(correo);
                guardarFuncionario.setVenceCarnet(fecha1);
                guardarFuncionario.setRh(rh);
                EstadoCarnet car = tipoEstado.findEstadoCarnet(estado);
                guardarFuncionario.setEstadocarnetIDESTADOCARNET(car);
                guardarFuncionario.setCentroId(centro);
                guardarFuncionario.setRegionalId(regional);

                if (nuevaFoto != null) {
                    guardarFuncionario.setFotografia(nuevaFoto);
                }

                controlador.edit(guardarFuncionario);
            }

            if (guardarUsuario != null) {
                guardarUsuario.setNombres(nombres2);
                guardarUsuario.setApellidos(apellidos2);
                guardarUsuario.setClaves(claveEncriptada);
                guardarUsuario.setRol(4);
                guardarUsuario.setEstadoClave(1);

                controladorUsuario.edit(guardarUsuario);
            }

            enviarRespuestaExito(response, "¡Registro guardado exitosamente!");

        } catch (Exception e) {
            enviarRespuestaError(response, "¡Error al guardar el registro!");
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
        } catch (ParseException ex) {
            Logger.getLogger(FuncionarioServlets.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FuncionarioServlets.class.getName()).log(Level.SEVERE, null, ex);
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
