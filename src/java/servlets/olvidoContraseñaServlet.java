/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.AdministradorJpaController;
import controladores.EstudiantesJpaController;
import controladores.UsuariosJpaController;
import entidades.Administrador;
import entidades.Estudiantes;
import entidades.Usuarios;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "olvidoContraseñaServlet", urlPatterns = {"/olvidoContrase_aServlet"})
public class olvidoContraseñaServlet extends HttpServlet {

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
        String clave1 = request.getParameter("clave1");
        String clave2 = request.getParameter("clave2");

        if (!clave1.equals(clave2)) {
            // Las contraseñas no coinciden
            mensaje = "noCoinciden";
            response.sendRedirect("error.jsp");
            return;
        }

        UsuariosJpaController controladorUsuario = new UsuariosJpaController();

        // Obtener el número de cédula del usuario desde el formulario
        int numeroCedula = Integer.parseInt(request.getParameter("cedula"));

        // Buscar al usuario por su número de cédula
        
        Usuarios usuario = controladorUsuario.findUsuarios(numeroCedula);

        if (usuario == null) {
            // Si no se encuentra ningún usuario con la cédula proporcionada, redirigir a una página de error
            mensaje = "usuarioNoEncontrado";
            response.sendRedirect("error.jsp?respuesta=" + mensaje);
            return;
        }

        // Encontrado el usuario, ahora puedes cambiar su contraseña
        String contraseñaEncriptada = controladorUsuario.EncryptarClave(clave1);

        // Actualizar la contraseña en el objeto del usuario
        usuario.setClaves(contraseñaEncriptada);

        // Guardar los cambios en la base de datos
        controladorUsuario.edit(usuario);

        // Redirigir a una página de confirmación
        mensaje = "guardado";
        response.sendRedirect("vistas/index.jsp?respuesta=" + mensaje);
    }

   public void buscarCorreo(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException, Exception {

    int cedula = Integer.parseInt(request.getParameter("cedula2"));

    // Crear instancias de los controladores JPA
    EstudiantesJpaController estudiantesController = new EstudiantesJpaController();
    AdministradorJpaController admincontrolador = new AdministradorJpaController();

    try {
        // Buscar el estudiante por cédula en la base de datos
        Estudiantes estudiante = estudiantesController.findEstudiantes(cedula);
        // Buscar el administrador por cédula en la base de datos
        Administrador admin = admincontrolador.findAdministrador(cedula);

        // Verificar si se encontró el estudiante y obtener su correo electrónico
        if (estudiante != null) {
            String correo = estudiante.getCorreo();
            // Ahora puedes enviar el correo electrónico al correo asociado
            enviarCorreo(correo);
        }

        // Verificar si se encontró el administrador y obtener su correo electrónico
        if (admin != null) {
            String correo2 = admin.getCorreo();
            // Ahora puedes enviar el correo electrónico al correo asociado
            enviarCorreo(correo2);
        }

        // Redirigir a una página de confirmación
        String mensaje = "correoEnviado";
        response.sendRedirect("vistas/index.jsp?respuesta=" + mensaje);
    } catch (NoResultException ex) {
        // Manejar el caso en el que no se encuentre ningún usuario con la cédula dada
        response.sendRedirect("error.jsp");
    } catch (Exception ex) {
        // Manejar cualquier excepción que pueda ocurrir durante la búsqueda
        ex.printStackTrace();
        response.sendRedirect("error.jsp");
    }
}


    private void enviarCorreo(String correoDestino) {
        // Datos de la cuenta de correo
        String correoOrigen = "peralta9513@gmail.com"; // Cambia por tu dirección de correo
        String password = "ndok qjog axmf ynhd"; // Cambia por la contraseña de tu cuenta de correo

        // Configuración de las propiedades para el servidor SMTP de Gmail
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Autenticación
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoOrigen, password);
            }
        });

        try {
            // Crear un objeto MimeMessage
            Message message = new MimeMessage(session);

            // Establecer el remitente
            message.setFrom(new InternetAddress(correoOrigen));

            // Establecer el destinatario
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));

            // Establecer el asunto del correo
            message.setSubject("Recuperación de contraseña");

            // Contenido del mensaje
            String mensaje = "Hola,\n\nHemos recibido una solicitud para restablecer tu contraseña. "
                    + "Si no solicitaste esto, puedes ignorar este correo.\n\n"
                    + "Para cambiar tu contraseña, haz clic en el siguiente enlace:\n"
                    + "http://10.217.16.25:8080/SenaCarnet/vistas/olvidoContraseña.jsp "
                    + "Si tienes alguna pregunta, contáctanos.";

            // Establecer el contenido del mensaje
            message.setText(mensaje);

            // Enviar el mensaje
            Transport.send(message);

            mensaje = "enviado";
           
        } catch (MessagingException e) {
            // Manejar cualquier excepción que pueda ocurrir durante el envío del correo
            e.printStackTrace();
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
            Logger.getLogger(olvidoContraseñaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(olvidoContraseñaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
