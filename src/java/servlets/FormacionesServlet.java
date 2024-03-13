/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.FormacionJpaController;
import controladores.SedeJpaController;
import entidades.Formacion;
import entidades.Sede;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
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
          String boton = request.getParameter("action");
        switch (boton) {
            case "Guardar":
                botonGuardar(request, response);
                break;
            case "Eliminar":
                botonElimina(request, response);
                break;
            case "Editar":
                botonEdicion(request, response);
                break;

            default:

        }
    }
    
     public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mensaje;

        int codigo = Integer.parseInt(request.getParameter("codigo2"));
        String nombre = request.getParameter("nombre2");
        int sedeListass = Integer.parseInt(request.getParameter("SedesLista"));

         FormacionJpaController for1 = new FormacionJpaController();
         Formacion guardaFormacion = new Formacion();
         SedeJpaController sedes = new SedeJpaController();

        try {

            if (for1.findFormacion(codigo) != null) {
                mensaje = "existeFormacion";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            } else {

                guardaFormacion.setIdFormacion(codigo);
                guardaFormacion.setNombre(nombre);
                Sede des = sedes.findSede(sedeListass);
                guardaFormacion.setSedeId(des);

                for1.create(guardaFormacion);

                mensaje = "guardadoFormacion";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);

            }

        } catch (Exception e) {
            mensaje = "errorformacion";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        }

    }

    public void botonElimina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mensaje;

        int codigo = Integer.parseInt(request.getParameter("codigoEliminar2"));
        FormacionJpaController fo = new FormacionJpaController();
       
            try {
                fo.destroy(codigo);
                mensaje = "FormacionEliminada";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
           } catch (Exception e) {
               mensaje = "errorEliminada";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        }

         
    }

    public void botonEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoEliminar2"));
        String nombre = request.getParameter("nombreEliminar2");
        int sedeListass = Integer.parseInt(request.getParameter("SedesLista2"));

        FormacionJpaController se = new FormacionJpaController();
        Formacion formacionEditada = se.findFormacion(codigo);
        SedeJpaController sedes = new SedeJpaController();

        if (formacionEditada != null) {
            // Actualizar los datos de la sede
            formacionEditada.setNombre(nombre);
            Sede des = sedes.findSede(sedeListass);
            formacionEditada.setSedeId(des);
            try {
                
                System.out.println("datos");
                
                se.edit(formacionEditada);
                String mensaje = "Ediciónguardada";
                // Codificar el mensaje de respuesta
                mensaje = URLEncoder.encode(mensaje, "UTF-8");
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            } catch (Exception e) {
                 String mensaje = "erroreditarr";
                
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            }
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
