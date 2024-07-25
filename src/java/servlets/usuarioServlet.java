    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
     */
    package servlets;

    import entidades.IncriptacionContrasenas;
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.text.SimpleDateFormat;
    import java.util.Arrays;
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
    @WebServlet(name = "usuarioServlet", urlPatterns = {"/usuarioServlet"})
    public class usuarioServlet extends HttpServlet {

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
        String action = request.getParameter("action");

        switch (action) {
            case "ImportarUsuarios":
                importarUsuarios(request, response);
                break;
            case "ImportarEstudiantes":
                importarEstudiantes(request, response);
                break;
            default:
                throw new ServletException("Unsupported action: " + action);
        }
    }

    protected void importarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file3");
        if (filePart != null) {
            try {
                IncriptacionContrasenas encriptar = new IncriptacionContrasenas();
                InputStream inputStream = filePart.getInputStream();
                System.out.println("Iniciando procesamiento del archivo...");

                // Establish database connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/senacarnet", "root", "27478426*cP");
                System.out.println("Conexión establecida con la base de datos...");

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                // Prepare SQL statement for inserting users
                String sql = "INSERT INTO usuarios (CEDULA, NOMBRES, APELLIDOS, CLAVES, ROL, ESTADO_CLAVE) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length >= 6) {
                        statement.setInt(1, Integer.parseInt(data[0]));
                        statement.setString(2, data[1]);
                        statement.setString(3, data[2]);
                        statement.setString(4, data[3]);
                        statement.setInt(5, Integer.parseInt(data[4]));
                        statement.setInt(6, Integer.parseInt(data[5]));

                        statement.executeUpdate();
                        System.out.println("Registro insertado en la base de datos: " + Arrays.toString(data));
                    } else {
                        System.out.println("Advertencia: El registro no tiene suficientes columnas.");
                    }
                }

                statement.close();
                conn.close();
                reader.close();

                System.out.println("Procesamiento del archivo completado con éxito.");
                String mensaje = "guardarImportacion2";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);

                encriptar.Asiganacion();

            } catch (Exception e) {
                e.printStackTrace();
                String mensaje = "ErrorImportacion";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
            }
        }
    }

    protected void importarEstudiantes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file5");
        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();

            try {
                System.out.println("Iniciando procesamiento del archivo...");

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/senacarnet", "root", "27478426*cP");
                System.out.println("Conexión establecida con la base de datos...");

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                String sql = "INSERT INTO estudiantes (CEDULA, TIPO_DOCUMENTO_FK, NOMBRES, APELLIDOS, FORMACION_FK, SEDE_FK, FOTOGRAFIA, CORREO, VENCE_CARNET, ESTADO_CARNET_IDESTADO_CARNET, RH) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length >= 11) {
                        statement.setInt(1, Integer.parseInt(data[0]));
                        statement.setInt(2, Integer.parseInt(data[1]));
                        statement.setString(3, data[2]);
                        statement.setString(4, data[3]);
                        statement.setInt(5, Integer.parseInt(data[4]));
                        statement.setInt(6, Integer.parseInt(data[5]));
                        statement.setString(7, data[6]);
                        statement.setString(8, data[7]);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = sdf.parse(data[8]);
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        statement.setDate(9, sqlDate);

                        statement.setInt(10, Integer.parseInt(data[9]));
                        statement.setString(11, data[10]);

                        statement.executeUpdate();
                        System.out.println("Registro insertado en la base de datos: " + Arrays.toString(data));
                    } else {
                        System.out.println("Advertencia: El registro no tiene suficientes columnas.");
                    }
                }

                statement.close();
                conn.close();
                reader.close();

                System.out.println("Procesamiento del archivo completado con éxito.");
                String mensaje = "guardarAprendiz";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);

                filePart.delete();

            } catch (Exception e) {
                e.printStackTrace();
                String mensaje = "ErrorImportacion";
                response.sendRedirect("vistas/estudiantes.jsp?respuesta=" + mensaje);
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
