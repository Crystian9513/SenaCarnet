/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "CoordinadorServlet", urlPatterns = {"/CoordinadorServlet"})
public class CoordinadorServlet extends HttpServlet {

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
                EstadoCarnet car = tipoEstado.findEstadoCarnet(estado);
                editarEstudiante.setEstadoCarnetIdestadoCarnet(car);

                if (photo != null) {
                    editarEstudiante.setFotografia(photo);
                }

                //TABLA USUSARIO
                guardarUsuario.setCedula(cedula2);
                guardarUsuario.setNombres(nombres2);
                guardarUsuario.setApellidos(apellidos2);
                guardarUsuario.setClaves(claveEncriptada);
                guardarUsuario.setRol(4);

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
