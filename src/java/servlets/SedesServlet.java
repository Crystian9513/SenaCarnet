/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import controladores.SedeJpaController;
import entidades.Sede;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author Peralta
 */
@MultipartConfig //Para archivos siempre va esto
@WebServlet(name = "SedesServlet", urlPatterns = {"/SedesServlet"})
public class SedesServlet extends HttpServlet {

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
                botonEliminar(request, response);
                break;
            case "Editar":
                botonEditar(request, response);
                break;
            case "Importar":
                botonImportar(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    public void botonGuardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String nombre = request.getParameter("nombre");

        SedeJpaController se = new SedeJpaController();
        Sede guardaSede = new Sede();

        try {
            // Verificar si el código ya existe en la base de datos
            if (se.findSede(codigo) != null) {
                // El código ya existe, enviar notificación
                String mensaje = "Existe";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            } else {
                // El código no existe, proceder con la creación de la sede
                guardaSede.setIdSede(codigo);
                guardaSede.setNombre(nombre);

                se.create(guardaSede);
                String mensaje = "Guardado";
                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            }
        } catch (Exception e) {
            String mensaje = "errorguardar";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);

        }
    }

    protected void botonEliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mensaje;
        try {

            int codigo = Integer.parseInt(request.getParameter("codigoEliminar"));

            SedeJpaController sedeController = new SedeJpaController();
            sedeController.destroy(codigo);

            mensaje = "SedeEliminada";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        } catch (Exception e) {
            mensaje = "ErrorEliminada";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        }

    }

    public void botonEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int codigo = Integer.parseInt(request.getParameter("codigoEliminar"));
        String nombre = request.getParameter("nombreEliminar");

        SedeJpaController sedeController = new SedeJpaController();
        Sede sedeExistente = sedeController.findSede(codigo);

        try {
            if (sedeExistente != null) {
                // La sede existe, proceder con la edición
                sedeExistente.setNombre(nombre);
                sedeController.edit(sedeExistente);

                String mensaje = "Editado";

                response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            }
        } catch (Exception e) {
            String mensaje = "erroreditar";

            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
        }
    }

   protected void botonImportar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("Iniciando procesamiento del archivo...");

            // Verifica si el archivo recibido es nulo
            Part filePart = request.getPart("excelFile");
            if (filePart == null) {
                System.out.println("El archivo recibido es nulo.");
                return;
            }

            InputStream fileContent = filePart.getInputStream();

            Workbook workbook = Workbook.getWorkbook(fileContent);
            Sheet sheet = workbook.getSheet(0);

            int rows = sheet.getRows();

            SedeJpaController sedeController = new SedeJpaController();

            for (int i = 1; i < rows; i++) {
                Cell[] rowCells = sheet.getRow(i);

                int codigo;
                String nombre;

                if (rowCells[0].getType() == CellType.NUMBER) {
                    codigo = Integer.parseInt(rowCells[0].getContents());
                } else {
                    continue; // Salta a la siguiente fila
                }

                if (rowCells[1].getType() == CellType.LABEL) {
                    nombre = rowCells[1].getContents();
                } else {
                    continue; // Salta a la siguiente fila
                }

                Sede nuevaSede = new Sede();
                nuevaSede.setIdSede(codigo);
                nuevaSede.setNombre(nombre);

                // Verificar si el código ya existe en la base de datos
                if (sedeController.findSede(codigo) == null) {
                    sedeController.create(nuevaSede);
                }
            }

            workbook.close();

            String mensaje = "DatosImportados";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
            System.out.println("Procesamiento del archivo completado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            String mensaje = "Error al importar";
            response.sendRedirect("vistas/sedesFormaciones.jsp?respuesta=" + mensaje);
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
