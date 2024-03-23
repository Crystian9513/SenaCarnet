/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.AdministradorJpaController;
import controladores.CoordinadorJpaController;
import controladores.EstudiantesJpaController;
import controladores.UsuariosJpaController;
import entidades.Administrador;
import entidades.Coordinador;
import entidades.Estudiantes;
import entidades.Usuarios;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "olvidoContraseñaServlet", urlPatterns = {"/olvidoContrase_aServlet"})
public class olvidoContrasenaServlet extends HttpServlet {

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
            case "Enviar":
                buscarCorreo(request, response);
                break;
            case "Guardar":
                cambiarContraseña(request, response);
                break;
            default:
                throw new AssertionError();
        }

    }

    public void cambiarContraseña(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        String mensaje;

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();

        // Obtener el número de cédula del usuario desde el formulario
        int numeroCedula = Integer.parseInt(request.getParameter("cedula"));

        // Buscar al usuario por su número de cédula
        Usuarios usuario = controladorUsuario.findUsuarios(numeroCedula);

        if (usuario == null) {
            // Si no se encuentra ningún usuario con la cédula proporcionada, redirigir a una página de error
            mensaje = "usuarioNoEncontrado";
            response.sendRedirect("vistas/olvidoContrasena.jsp?respuesta=" + mensaje);

        } else {
            String clave1 = request.getParameter("clave1");
            String clave2 = request.getParameter("clave2");

            if (!clave1.equals(clave2)) {
                // Las contraseñas no coinciden
                mensaje = "noCoinciden";

                // Redirigir de vuelta al formulario de cambio de contraseña sin guardar
                response.sendRedirect("vistas/olvidoContrasena.jsp?respuesta=" + mensaje);
                return;
            }

            // Encontrado el usuario y las contraseñas coinciden, ahora puedes cambiar su contraseña
            String contraseñaEncriptada = controladorUsuario.EncryptarClave(clave1);

            // Actualizar la contraseña en el objeto del usuario
            usuario.setClaves(contraseñaEncriptada);

            // Guardar los cambios en la base de datos
            controladorUsuario.edit(usuario);

            // Redirigir a una página de confirmación
            mensaje = "contraseñaNuevaGuardada";
            response.sendRedirect("vistas/index.jsp?respuesta=" + mensaje);
        }
    }

    public void buscarCorreo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        // Aquí creas un nuevo objeto Usuarios para enviarlo por correo
        Usuarios usuario = new Usuarios();

        // Supongamos que obtienes la información necesaria para el usuario de alguna manera, por ejemplo, de los parámetros de la solicitud HTTP
        usuario.setCedula(Integer.parseInt(request.getParameter("cedula2")));

        // Aquí configuras los demás atributos del usuario según lo que necesites
        int cedula = usuario.getCedula();

        // Crear instancias de los controladores JPA
        EstudiantesJpaController estudiantesController = new EstudiantesJpaController();
        AdministradorJpaController admincontrolador = new AdministradorJpaController();
        CoordinadorJpaController cordinaControlador = new CoordinadorJpaController();

        boolean correoEnviado = false; // Variable para verificar si se envió el correo

        try {
            // Buscar el estudiante por cédula en la base de datos
            Estudiantes estudiante = estudiantesController.findEstudiantes(cedula);
            // Buscar el administrador por cédula en la base de datos
            Administrador admin = admincontrolador.findAdministrador(cedula);
            // Buscar el coordinador por cédula en la base de datos
            Coordinador cordina = cordinaControlador.findCoordinador(cedula);

            // Verificar si se encontró el estudiante y obtener su correo electrónico
            if (estudiante != null) {
                String correo = estudiante.getCorreo();
                // Ahora puedes enviar el correo electrónico al correo asociado
                enviarCorreo(correo, usuario, response, request); // Pasar el usuario como parámetro
                correoEnviado = true; // Actualizar la bandera
            }

            // Verificar si se encontró el administrador y obtener su correo electrónico
            if (admin != null) {
                String correo2 = admin.getCorreo();
                // Ahora puedes enviar el correo electrónico al correo asociado
                enviarCorreo(correo2, usuario, response, request); // Pasar el usuario como parámetro
                correoEnviado = true; // Actualizar la bandera
            }

            // Verificar si se encontró el coordinador y obtener su correo electrónico
            if (cordina != null) {
                String correo3 = cordina.getCorreo(); // Aquí debería ser cordina.getCorreo() en lugar de admin.getCorreo()
                // Ahora puedes enviar el correo electrónico al correo asociado
                enviarCorreo(correo3, usuario, response, request); // Pasar el usuario como parámetro
                correoEnviado = true; // Actualizar la bandera
            }

            // Después de encontrar al usuario
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);

            if (!correoEnviado) {
                // Si no se envió el correo, significa que no se encontró ninguna cédula
                String mensaje = "noCedula";
                response.sendRedirect("vistas/index.jsp?respuesta=" + mensaje);
            } else {

            }
        } catch (Exception ex) {
            // Manejar cualquier excepción que pueda ocurrir durante la búsqueda
            ex.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

private void enviarCorreo(String correoDestino, Usuarios usuario, HttpServletResponse response,
        HttpServletRequest request) throws IOException {
    String correoOrigen = "peralta9513@gmail.com";
    String password = "ndok qjog axmf ynhd";

    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(correoOrigen, password);
        }
    });

    try {
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(correoOrigen));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
        message.setSubject("Recuperación de contraseña");

        // Generar un token único para la URL de recuperación de contraseña
        String token = UUID.randomUUID().toString(); // Generar un UUID aleatorio como token único

        // Construir la URL de recuperación de contraseña con el token único
        String url = "http://10.217.16.42:8080/SenaCarnet/vistas/olvidoContrasena.jsp?token=" + token;

        // Almacenar el token en la sesión del usuario para verificar su validez posteriormente
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("sesionToken", token);

        // Construir el contenido del mensaje con la URL única
        String mensaje = "Hola,\n\nHemos recibido una solicitud para restablecer tu contraseña. "
                + "Si no solicitaste esto, puedes ignorar este correo.\n\n"
                + "Para cambiar tu contraseña, haz clic en el siguiente enlace:\n"
                + "<a href=\"" + url + "\">Cambiar contraseña</a>"
                + " Si tienes alguna pregunta, contáctanos.";

        message.setContent(mensaje, "text/html");

        // Enviar el correo electrónico
        Transport.send(message);

        // Redireccionar a la página de inicio con un mensaje de confirmación
        String mensajeRedireccion = "enviado";
        response.sendRedirect("vistas/index.jsp?respuesta=" + mensajeRedireccion);
        
        // Mensajes de depuración
        System.out.println("Correo enviado correctamente.");
        System.out.println("URL de recuperación de contraseña: " + url);

    } catch (MessagingException e) {
        e.printStackTrace();
        System.out.println("Error al enviar el correo: " + e.getMessage());
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
            Logger.getLogger(olvidoContrasenaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(olvidoContrasenaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
