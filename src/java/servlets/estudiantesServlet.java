/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.EstudiantesJpaController;
import controladores.FormacionJpaController;
import controladores.SedeJpaController;
import controladores.TipodocumentoJpaController;
import controladores.UsuariosJpaController;
import entidades.Estudiantes;
import entidades.Formacion;
import entidades.Sede;
import entidades.Tipodocumento;
import entidades.Usuarios;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private String pathFiles = "";
    private File uploads;
    private String[] extensiones = {".ico", ".png", ".jpg", ".jpeg"};

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
            case "Eliminar":
                botonEliminar(request, response);
                break;
            case "Editar":
                botonEditar(request, response);
                break;
            default:
                throw new AssertionError();
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

        try {

            if (controlador.findEstudiantes(cedula) != null) {

                mensaje = "Existe";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);

            }
            if (part == null) {

                return;
            }
            pathFiles = getServletContext().getResource("vistas/fotos").getPath().replace("build", "");
            uploads = new File(pathFiles);

            if (isExtension(part.getSubmittedFileName(), extensiones)) {
                String photo = saveFile(part, uploads);

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
                guardarEstudiante.setFotografia(photo);
                guardarEstudiante.setVenceCarnet(fecha1);

                //TABLA USUSARIO
                guardarUsuario.setCedula(cedula2);
                guardarUsuario.setNombres(nombres2);
                guardarUsuario.setApellidos(apellidos2);
                guardarUsuario.setClaves(claveEncriptada);
                guardarUsuario.setRol(1);

                controlador.create(guardarEstudiante);
                controladorUsuario.create(guardarUsuario);

                mensaje = "guardar";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);

            }

        } catch (Exception e) {

            mensaje = "errorAlguardarr";
            response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
        }

    }

    public void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mensaje;
        int codigoUsuario = Integer.parseInt(request.getParameter("cedula2"));

        try {

            EstudiantesJpaController controlador = new EstudiantesJpaController();
            UsuariosJpaController controladorUsuario = new UsuariosJpaController();

            // Obtener el estudiante a eliminar
            Estudiantes estudiante = controlador.findEstudiantes(codigoUsuario);

            // Verificar si el estudiante existe
            if (estudiante != null) {
                // Obtener la foto del estudiante
                String fotoEstudiante = estudiante.getFotografia();

                // Eliminar el estudiante de la base de datos
                controlador.destroy(codigoUsuario);
                controladorUsuario.destroy(codigoUsuario);

                // Verificar si se proporcionó una ruta de foto
                if (fotoEstudiante != null && !fotoEstudiante.isEmpty()) {
                    // Eliminar la foto del estudiante del servidor
                    File fileToDelete = new File(getServletContext().getRealPath("vistas/fotos" + fotoEstudiante));
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                    }
                }

                mensaje = "eliminado";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
            } else {
                // Manejar el caso en que el estudiante no existe
                mensaje = "El estudiante no existe";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
            }

        } catch (Exception e) {

            mensaje = "errorEliminar";
            response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
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

        // Obtener el archivo de la foto
        Part part = request.getPart("foto2");
        String photo = null;

        // Verificar si se proporcionó una nueva foto
        if (part != null && part.getSize() > 0) {
            // Guardar la nueva foto en el servidor
            pathFiles = getServletContext().getResource("vistas/fotos").getPath().replace("build", "");
            uploads = new File(pathFiles);
            if (isExtension(part.getSubmittedFileName(), extensiones)) {
                photo = saveFile(part, uploads);
            }
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

                if (photo != null) {
                    editarEstudiante.setFotografia(photo);
                }

                //TABLA USUSARIO
                guardarUsuario.setCedula(cedula2);
                guardarUsuario.setNombres(nombres2);
                guardarUsuario.setApellidos(apellidos2);
                guardarUsuario.setClaves(claveEncriptada);
                guardarUsuario.setRol(1);

                controladorUsuario.edit(guardarUsuario);
                controlador.edit(editarEstudiante);
                mensaje = "edicionGuardad";

                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
            }

        } catch (Exception e) {

            mensaje = "erroreditarr";
            response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
        }

    }

    private String saveFile(Part part, File pathUploads) {
        String pathAbsolute = "";

        try {

            Path path = Paths.get(part.getSubmittedFileName());
            String fileName = path.getFileName().toString();
            InputStream input = part.getInputStream();

            if (input != null) {
                File file = new File(pathUploads, fileName);
                pathAbsolute = "fotos/" + fileName;
                Files.copy(input, file.toPath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pathAbsolute;
    }

    private boolean isExtension(String fileName, String[] extensions) {
        for (String et : extensions) {
            if (fileName.toLowerCase().endsWith(et)) {
                return true;
            }
        }

        return false;
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
