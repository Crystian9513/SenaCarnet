/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import controladores.EstadoCarnetJpaController;
import controladores.EstudiantesJpaController;
import controladores.FormacionJpaController;
import controladores.SedeJpaController;
import controladores.TipodocumentoJpaController;
import controladores.UsuariosJpaController;
import entidades.EstadoCarnet;
import entidades.Estudiantes;
import entidades.Formacion;
import entidades.Sede;
import entidades.Tipodocumento;
import entidades.Usuarios;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
@WebServlet(name = "estudiantesServlet", urlPatterns = {"/estudiantesServlet"})
public class estudiantesServlet extends HttpServlet {

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
                    botonEditar(request, response);
                    break;
                case "eliminar":
                    botonEliminar(request, response);
                    break;
                case "nuevaFormacion":
                    botonNuevaFormacion(request, response);
                    break;
                default:
                    // Acción no válida
                    break;
            }
        }

    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        String mensaje;

        int cedula = Integer.parseInt(request.getParameter("cedula"));
        int tpdocumento = Integer.parseInt(request.getParameter("tipoDocumento"));
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        int formacion = Integer.parseInt(request.getParameter("formacion"));
        int sede = Integer.parseInt(request.getParameter("sede"));
        String correo = request.getParameter("correo");
        Part part = request.getPart("foto");

        String fechaInicio = request.getParameter("vence");
        String rh = request.getParameter("rh");
        int estado = Integer.parseInt(request.getParameter("estado"));
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha1 = formato.parse(fechaInicio);

        int cedula2 = Integer.parseInt(request.getParameter("cedula"));//TABLA DE USUARIO
        String nombres2 = request.getParameter("nombres");//TABLA DE USUARIO
        String apellidos2 = request.getParameter("apellidos");//TABLA DE USUARIO
        String clave = request.getParameter("cedula");//TABLA DE USUARIO

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();//TABLA DE USUARIO
        Usuarios guardarUsuario = new Usuarios();//TABLA DE USUARIO

        String claveEncriptada = controladorUsuario.EncryptarClave(clave);

        EstudiantesJpaController controlador = new EstudiantesJpaController();
        Estudiantes guardarEstudiante = new Estudiantes();

        TipodocumentoJpaController tipo = new TipodocumentoJpaController();
        FormacionJpaController tipoForma = new FormacionJpaController();
        SedeJpaController tipoSede = new SedeJpaController();
        EstadoCarnetJpaController tipoEstado = new EstadoCarnetJpaController();

        try {

            if (controlador.findEstudiantes(cedula) != null) {

                enviarRespuestaError(response, "¡Error! Ya existe un registro con esa cédula.");
                return; // Se agrega un return para terminar la ejecución del método

            }
            if (part == null) {

                return;
            }

            byte[] imagenBytes = part.getInputStream().readAllBytes();

            guardarEstudiante.setCedula(cedula);
            Tipodocumento to = tipo.findTipodocumento(tpdocumento);
            guardarEstudiante.setTipoDocumentoFk(to);
            guardarEstudiante.setNombres(nombres);
            guardarEstudiante.setApellidos(apellidos);
            Formacion fo = tipoForma.findFormacion(formacion);
            guardarEstudiante.setFormacionFk(fo);
            Sede se = tipoSede.findSede(sede);
            guardarEstudiante.setSedeFk(se);
            guardarEstudiante.setCorreo(correo);
            guardarEstudiante.setVenceCarnet(fecha1);
            guardarEstudiante.setRh(rh);
            EstadoCarnet car = tipoEstado.findEstadoCarnet(estado);
            guardarEstudiante.setEstadoCarnetIdestadoCarnet(car);
            guardarEstudiante.setFotografia(imagenBytes); // Guardar la imagen en el campo BLOB

            //TABLA USUARIO
            guardarUsuario.setCedula(cedula2);
            guardarUsuario.setNombres(nombres2);
            guardarUsuario.setApellidos(apellidos2);
            guardarUsuario.setClaves(claveEncriptada);
            guardarUsuario.setRol(1);
            guardarUsuario.setEstadoClave(1);

            controlador.create(guardarEstudiante);
            controladorUsuario.create(guardarUsuario);

            enviarRespuestaExito(response, "¡Registro guardado exitosamente!");

        } catch (Exception e) {

             enviarRespuestaError(response, "¡Error!");;
        }
    }

    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int codigoUsuario = Integer.parseInt(request.getParameter("cedula2"));

        try {

            EstudiantesJpaController controlador = new EstudiantesJpaController();
            UsuariosJpaController controladorUsuario = new UsuariosJpaController();

            // Obtener el estudiante a eliminar
            Estudiantes estudiante = controlador.findEstudiantes(codigoUsuario);

            // Verificar si el estudiante existe
            if (estudiante != null) {
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

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cedula = Integer.parseInt(request.getParameter("cedula2"));
        int tpdocumento = Integer.parseInt(request.getParameter("tipoDocumento2"));
        String nombres = request.getParameter("nombres2");
        String apellidos = request.getParameter("apellidos2");
        int formacion = Integer.parseInt(request.getParameter("formacion2"));
        int sede = Integer.parseInt(request.getParameter("sede2"));
        String correo = request.getParameter("correo2");
        String vencimiento = request.getParameter("vence2");
        String rh = request.getParameter("rh2");

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

        // Obtener el archivo de la foto
        Part part = request.getPart("foto2");
        byte[] nuevaFoto = null;

        // Verificar si se proporcionó una nueva foto
        if (part != null && part.getSize() > 0) {
            // Guardar la nueva foto en un arreglo de bytes
            nuevaFoto = part.getInputStream().readAllBytes();
        }

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
                editarEstudiante.setRh(rh);
                EstadoCarnet car = tipoEstado.findEstadoCarnet(estado);
                editarEstudiante.setEstadoCarnetIdestadoCarnet(car);

                // Verificar si se proporcionó una nueva foto y actualizarla
                if (nuevaFoto != null) {
                    editarEstudiante.setFotografia(nuevaFoto);
                }

                // Actualizar los datos del estudiante
                controlador.edit(editarEstudiante);

                // Actualizar los datos del usuario
                guardarUsuario.setCedula(cedula2);
                guardarUsuario.setNombres(nombres2);
                guardarUsuario.setApellidos(apellidos2);
                guardarUsuario.setClaves(claveEncriptada);
                guardarUsuario.setRol(1);
                guardarUsuario.setEstadoClave(1);
                controladorUsuario.edit(guardarUsuario);

                enviarRespuestaExito(response, "¡Registro editado exitosamente!");
            }

        } catch (Exception e) {

            enviarRespuestaError(response, "¡Error!");
        }

    }

    public void botonNuevaFormacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cedula = Integer.parseInt(request.getParameter("cedula20"));
        int tpdocumento = Integer.parseInt(request.getParameter("tipoDocumento20"));
        String nombres = request.getParameter("nombres20");
        String apellidos = request.getParameter("apellidos20");
        int formacion = Integer.parseInt(request.getParameter("formacion20"));
        int sede = Integer.parseInt(request.getParameter("sede20"));
        String correo = request.getParameter("correo20");
        String vencimiento = request.getParameter("vence20");
        String rh = request.getParameter("rh20");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        int estado = Integer.parseInt(request.getParameter("estado20"));

        int cedula2 = Integer.parseInt(request.getParameter("cedula20"));//TABLA DE USUARIO
        String nombres2 = request.getParameter("nombres20");//TABLA DE USUARIO
        String apellidos2 = request.getParameter("apellidos20");//TABLA DE 
        String clave = request.getParameter("cedula20");//TABLA DE USUARIO

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();//TABLA DE USUARIO
        Usuarios guardarUsuario = new Usuarios();//TABLA DE USUARIO
        String claveEncriptada = controladorUsuario.EncryptarClave(clave);

        EstudiantesJpaController controlador = new EstudiantesJpaController();
        Estudiantes editarEstudiante = controlador.findEstudiantes(cedula);

        TipodocumentoJpaController tipo = new TipodocumentoJpaController();
        FormacionJpaController tipoForma = new FormacionJpaController();
        SedeJpaController tipoSede = new SedeJpaController();
        EstadoCarnetJpaController tipoEstado = new EstadoCarnetJpaController();
        
         // Obtener el archivo de la foto
        Part part = request.getPart("foto20");
        byte[] nuevaFoto = null;

        // Verificar si se proporcionó una nueva foto
        if (part != null && part.getSize() > 0) {
            // Guardar la nueva foto en un arreglo de bytes
            nuevaFoto = part.getInputStream().readAllBytes();
        }

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
                editarEstudiante.setRh(rh);
                EstadoCarnet car = tipoEstado.findEstadoCarnet(estado);
                editarEstudiante.setEstadoCarnetIdestadoCarnet(car);
                
                // Verificar si se proporcionó una nueva foto y actualizarla
                if (nuevaFoto != null) {
                    editarEstudiante.setFotografia(nuevaFoto);
                }

                //TABLA USUSARIO
                guardarUsuario.setCedula(cedula2);
                guardarUsuario.setNombres(nombres2);
                guardarUsuario.setApellidos(apellidos2);
                guardarUsuario.setClaves(claveEncriptada);
                guardarUsuario.setRol(1);

                controladorUsuario.edit(guardarUsuario);
                controlador.edit(editarEstudiante);
                enviarRespuestaExito(response, "¡Registro editado exitosamente!");
            }

        } catch (Exception e) {
             enviarRespuestaError(response, "¡Error!");

        }

    }

    protected void botonImportar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file5"); // Obtener el archivo cargado desde la solicitud
        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream(); // Obtener el flujo de entrada del archivo

            try {
                // Iniciando procesamiento del archivo
                System.out.println("Iniciando procesamiento del archivo...");

                // Cargar el controlador JDBC para MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establecer conexión con la base de datos
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/senacarnet", "root", "27478426*cP");
                System.out.println("Conexión establecida con la base de datos...");

                // Crear un lector de CSV
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                // Preparar la consulta para insertar datos en la base de datos
                String sql = "INSERT INTO estudiantes (CEDULA, TIPO_DOCUMENTO_FK, NOMBRES, APELLIDOS, FORMACION_FK, SEDE_FK, FOTOGRAFIA, CORREO, VENCE_CARNET, ESTADO_CARNET_IDESTADO_CARNET,RH) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
                PreparedStatement statement = conn.prepareStatement(sql);

                // Leer el archivo CSV y guardar los datos en la base de datos
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length >= 10) { // Asegúrate de tener suficientes columnas según tu tabla
                        statement.setInt(1, Integer.parseInt(data[0]));
                        statement.setInt(2, Integer.parseInt(data[1]));
                        statement.setString(3, data[2]);
                        statement.setString(4, data[3]);
                        statement.setInt(5, Integer.parseInt(data[4]));
                        statement.setInt(6, Integer.parseInt(data[5]));
                        statement.setString(7, data[6]);
                        statement.setString(8, data[7]);

                        // Convertir la fecha de texto a un objeto java.sql.Date
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Ajusta el formato según el formato de tu fecha en el CSV
                        java.util.Date date = sdf.parse(data[8]); // Parsear la fecha de texto a un objeto java.util.Date
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime()); // Convertir el objeto java.util.Date a java.sql.Date

                        // Asignar la fecha a los parámetros de la consulta
                        statement.setDate(9, sqlDate);

                        statement.setInt(10, Integer.parseInt(data[9]));

                        statement.setString(11, data[10]);

                        statement.executeUpdate();
                        System.out.println("Registro insertado en la base de datos: " + Arrays.toString(data));
                    } else {
                        // Imprimir un mensaje de advertencia si el registro no tiene suficientes columnas
                        System.out.println("Advertencia: El registro no tiene suficientes columnas.");
                    }
                }

                // Cerrar recursos
                statement.close();
                conn.close();
                reader.close();

                // Imprimir mensaje de éxito en la consola
                System.out.println("Procesamiento del archivo completado con éxito.");
                String mensaje = "guardarAprendiz";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);

            } catch (Exception e) {
                String mensaje = "ErrorImportacion";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(estudiantesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(estudiantesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
