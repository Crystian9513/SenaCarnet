/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controladores.AreaTrabajoJpaController;
import controladores.EstadoCarnetJpaController;
import controladores.FuncionariosJpaController;
import controladores.RolFuncionarioJpaController;
import controladores.TipodocumentoJpaController;
import entidades.AreaTrabajo;
import entidades.EstadoCarnet;
import entidades.Funcionarios;
import entidades.RolFuncionario;
import entidades.Tipodocumento;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peralta
 */
@WebServlet(name = "ConsultaFuncionarios", urlPatterns = {"/ConsultaFuncionarios"})
public class ConsultaFuncionarios extends HttpServlet {

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

        cargarTabla(request, response);
    }

    protected void cargarTabla(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        FuncionariosJpaController controlador = new FuncionariosJpaController();
        List<Funcionarios> funcionario = controlador.findFuncionariosEntities();

        List<JsonObject> funcionarioJson = new ArrayList<>();

        TipodocumentoJpaController documentoControlador = new TipodocumentoJpaController();
        EstadoCarnetJpaController estadoControlador = new EstadoCarnetJpaController();
        RolFuncionarioJpaController rolcontrolador = new RolFuncionarioJpaController();
        AreaTrabajoJpaController areaControlador = new AreaTrabajoJpaController();

        for (Funcionarios funcio : funcionario) {

            JsonObject funcionariosJson = new JsonObject();
            funcionariosJson.addProperty("cedula", funcio.getCedula());
            funcionariosJson.addProperty("nombre", funcio.getNombres());
            funcionariosJson.addProperty("apellido", funcio.getApellidos());

            Tipodocumento tipo = documentoControlador.findTipodocumento(funcio.getTipodocumentoIDTIPODOCUMENTO().getIdTipoDocumento());
            if (tipo != null) {
                funcionariosJson.addProperty("tipoDocumentoId", tipo.getIdTipoDocumento());
                funcionariosJson.addProperty("tipoDocumentoNombre", tipo.getNombre());

            }
            EstadoCarnet estadoCarnet = estadoControlador.findEstadoCarnet(funcio.getEstadocarnetIDESTADOCARNET().getIdestadoCarnet());
            if (estadoCarnet != null) {
                funcionariosJson.addProperty("estadoCarnetid", estadoCarnet.getIdestadoCarnet());
                funcionariosJson.addProperty("estadoCarnetNombre", estadoCarnet.getNombre());

            }
            AreaTrabajo areaTranajo = areaControlador.findAreaTrabajo(funcio.getAreatrabajoCODIGO().getCodigo());
            if (areaTranajo != null) {
                funcionariosJson.addProperty("areaTrabajoId", areaTranajo.getCodigo());
                funcionariosJson.addProperty("areaTrabajonombre", areaTranajo.getNombre());
            }
            
            RolFuncionario rolFuncionario = rolcontrolador.findRolFuncionario(funcio.getRolfuncionarioCODIGO().getCodigo());
            if (rolFuncionario != null) {
                funcionariosJson.addProperty("funcionarioId", rolFuncionario.getCodigo());
                funcionariosJson.addProperty("funcionarioNombre", rolFuncionario.getNombre());

            }
            funcionariosJson.addProperty("correo", funcio.getCorreo());

            Date fechaVencimiento = funcio.getVenceCarnet();
            if (fechaVencimiento != null) {
                // Formatear la fecha como una cadena
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fechaVencimientoStr = dateFormat.format(fechaVencimiento);
                // Agregar la fecha al JSON
                funcionariosJson.addProperty("fechaVencimientoCarnet", fechaVencimientoStr);
            }
             funcionariosJson.addProperty("rh", funcio.getRh());
            
             funcionarioJson.add(funcionariosJson);
        }
        
         String json = new Gson().toJson(funcionarioJson);
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
