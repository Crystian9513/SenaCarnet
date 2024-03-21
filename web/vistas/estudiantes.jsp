<%@page import="entidades.EstadoCarnet"%>
<%@page import="controladores.EstadoCarnetJpaController"%>
<%@page import="entidades.Usuarios"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entidades.Sede"%>
<%@page import="controladores.SedeJpaController"%>
<%@page import="entidades.Formacion"%>
<%@page import="controladores.FormacionJpaController"%>
<%@page import="entidades.Tipodocumento"%>
<%@page import="controladores.TipodocumentoJpaController"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Estudiantes"%>
<%@page import="controladores.EstudiantesJpaController"%>

<% HttpSession sesion = request.getSession();

    Usuarios usuario = (Usuarios) sesion.getAttribute("user");

    if (usuario == null) {
        response.sendRedirect("../index.jsp");
    } else {

    }

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Estudiantes</title>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/tabla.css"/>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

        <script>
            // Espera a que el DOM esté completamente cargado
            document.addEventListener("DOMContentLoaded", function () {
                // Encuentra todos los elementos con el atributo data-bs-toggle="popover"
                var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));

                // Itera sobre cada elemento y crea un nuevo objeto Popover para él
                var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
                    return new bootstrap.Popover(popoverTriggerEl);
                });
            });
        </script>

        <script>
            // Función para obtener los datos de la sede y mostrarlos en el modal
            function obtenerDatosEstudiantes(Cedula, TipoDocumentoFk, Nombres, Apellidos, FormacionFk, SedeFk, Correo, VenceCarnet, EstadoCarnetIdestadoCarnet)
            {
                $('#cedula2').val(Cedula);
                $('#tipoDocumento2').val(TipoDocumentoFk);
                $('#nombres2').val(Nombres);
                $('#apellidos2').val(Apellidos);
                $('#formacion2').val(FormacionFk);
                $('#sede2').val(SedeFk);
                $('#correo2').val(Correo);
                $('#vence2').val(VenceCarnet);
                $('#estado2').val(EstadoCarnetIdestadoCarnet);


                // Realiza las solicitudes AJAX para obtener datos adicionales si es necesario
                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerTipoDocumento.jsp",
                    data: {tipoDocumento: TipoDocumentoFk},
                    dataType: "html",
                    success: function (data) {
                        $("#tipoDocumento2").empty().append(data);
                    }
                });

                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerSede.jsp",
                    data: {idsede: SedeFk},
                    dataType: "html",
                    success: function (data) {
                        $("#sede2").empty().append(data);
                    }
                });

                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerFormaciones.jsp",
                    data: {formacionesTipos: FormacionFk},
                    dataType: "html",
                    success: function (data) {
                        $("#formacion2").empty().append(data);
                    }
                });
                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerEstadoCarnet.jsp",
                    data: {estadoCarnetTipo: EstadoCarnetIdestadoCarnet},
                    dataType: "html",
                    success: function (data) {
                        $("#estado2").empty().append(data);
                    }
                });
            }

        </script>

        <script>
            // Función para obtener los datos de la sede y mostrarlos en el modal
            function obtenerDatosEstudiantes2(Cedula, TipoDocumentoFk, Nombres, Apellidos, FormacionFk, SedeFk, Correo, VenceCarnet, EstadoCarnetIdestadoCarnet)
            {
                $('#cedula20').val(Cedula);
                $('#tipoDocumento20').val(TipoDocumentoFk);
                $('#nombres20').val(Nombres);
                $('#apellidos20').val(Apellidos);
                $('#formacion20').val(FormacionFk);
                $('#sede20').val(SedeFk);
                $('#correo20').val(Correo);
                $('#vence20').val(VenceCarnet);
                $('#estado20').val(EstadoCarnetIdestadoCarnet);

                $('#cedula20').closest('.input-group').hide();
                $('#tipoDocumento20').closest('.input-group').hide();
                $('#nombres20').closest('.input-group').hide();
                $('#apellidos20').closest('.input-group').hide();
                $('#sede20').closest('.input-group').hide();
                $('#correo20').closest('.input-group').hide();

                // Realiza las solicitudes AJAX para obtener datos adicionales si es necesario
                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerTipoDocumento.jsp",
                    data: {tipoDocumento: TipoDocumentoFk},
                    dataType: "html",
                    success: function (data) {
                        $("#tipoDocumento20").empty().append(data);
                    }
                });

                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerSede.jsp",
                    data: {idsede: SedeFk},
                    dataType: "html",
                    success: function (data) {
                        $("#sede20").empty().append(data);
                    }
                });

                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerFormaciones.jsp",
                    data: {formacionesTipos: FormacionFk},
                    dataType: "html",
                    success: function (data) {
                        $("#formacion20").empty().append(data);
                    }
                });
                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerEstadoCarnet.jsp",
                    data: {estadoCarnetTipo: EstadoCarnetIdestadoCarnet},
                    dataType: "html",
                    success: function (data) {
                        $("#estado20").empty().append(data);
                    }
                });
            }

        </script>

        <script>
            function verReporte4(cedula) {
                // Redirigir al enlace con la cédula como parámetro
                window.open("<%= request.getContextPath()%>/reportes/reporteCarnetCompleto.jsp?cedula=" + cedula, "_blank");
            }
        </script>


    </head>
    <body  style="background-color: #fefafb;">
        <%--MENU INICIO --%>
        <nav class="navbar text-l navbar-expand-lg "  style="background-color: #6acd56;">
            <div class="container">

                <div class="row">

                    <div class="col-md-2">
                       
                            <img class="" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">
                       
                    </div>

                    <div class="col-md-2 text-center">
                        <h2 class="mt-3 letras"> SENA </h2>
                    </div>
                    <div class="col-md-8">
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
                                aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse mt-2" id="navbarNavDropdown">
                            <ul class="navbar-nav ms-auto navbar-brand">
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        Aprendiz
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="">Ingresar</a></li>
                                        <li><a class="dropdown-item" href="carnetEliminado.jsp">Carnet Eliminado</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item ">
                                    <a class="nav-link" href="administrador.jsp">Administrador</a>
                                </li>
                                <li class="nav-item ">
                                    <a class="nav-link" href="coordinadorDatos.jsp">Coordinador</a>
                                </li>
                                <li class="nav-item ">
                                    <a class="nav-link" href="sedesFormaciones.jsp">Sede-Formacion</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="menuPrincipal.jsp">Menu Principal</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="cerrarSesionAdministrador.jsp">Salir</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
        <%--MENU FINAL --%>
        <%--CONTENIDO INICIO --%>
        <div class="container">
            <div class="row">
                <div class="col-12">

                    <h1 class="letra text-center pt-3 pb-3">Informacion del Aprendiz</h1>

                    <div class="container">
                        <div class="row">
                            
                            <div class="col-md-6 col-sd-12">
                                <form action="<%=request.getContextPath()%>/estudiantesServlet" method="post" enctype="multipart/form-data" class="pt-2">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-4"><b>Aprendices:</b></div>
                                        <input type="file" class="form-control" name="file5" id="fileInput2" required="1">
                                        <button type="submit" class="btn" name="action" value="Importar2" style="background-color: #6acd56;"><b>Importar</b></button>

                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6 col-sd-12">
                                <form action="<%=request.getContextPath()%>" method="post" class="pt-2">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-md-9 col-sd-12"><b>Nuevo estudiante:</b></div>
                                        <button id="" type="button" class="btn" style="background-color: #6acd56;" data-bs-toggle="modal" data-bs-target="#formularioModal"><b>Formulario</b></button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6 col-sd-12">
                                <form action="<%=request.getContextPath()%>/usuarioServlet" method="post" enctype="multipart/form-data" class="pt-2">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-4"><b>Usuarios:</b></div>
                                        <input type="file" class="form-control" name="file3" id="fileInput3" required="1">
                                        <button type="submit" class="btn" name="action" value="Importar" style="background-color: #6acd56;"><b>Importar</b></button>
                                    </div>
                                </form>
                            </div>
                                    <!--  <div class="col-md-6 col-sd-12">
                                <form action="<%=request.getContextPath()%>" method="post" class="pt-2">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-md-9 col-sd-12"><b>Encriptar Clave de Usuarios Nuevos:</b></div>
                                        <button id="" type="button" class="btn" style="background-color: #6acd56;" data-bs-toggle="modal" data-bs-target="#formularioModal"><b>Formulario</b></button>
                                    </div>
                                </form>
                            </div> -->
                            <div class="col-md-12 col-sd-12">
                                <form action="<%=request.getContextPath()%>" method="post" class="pt-2">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-4"><b>Buscar:</b></div>
                                        <input type="text" class="form-control" id="filtro1">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <section class="intro mb-5">
                        <div class="bg-image" >
                            <div class="mask d-flex align-items-center h-100">
                                <div class="container tableContenido">
                                    <div class="row justify-content-center" data-aos="zoom-in"  data-aos-duration="500">
                                        <div class="col-12 tableContenido"> 
                                            <div class="card-body p-0 ">
                                                <%--TABLA INICIO --%>
                                                <div class="table-responsive table-scroll" data-mdb-perfect-scrollbar="true" style="position: relative; height: 700px">
                                                    <table id="tablaEstudiantes" class="table table-striped table-sm mb-0 text-center ">
                                                        <thead class="" style="background-color: #263642;">
                                                            <tr>
                                                                <th scope="col">Cedula</th>
                                                                <th scope="col">Tipo de Documento</th>
                                                                <th scope="col">Nombres</th>
                                                                <th scope="col">Apellidos</th>
                                                                <th scope="col">Formacion</th>
                                                                <th scope="col">Sede</th>
                                                                <th scope="col">Correo</th>
                                                                <th scope="col">Fotografia</th>
                                                                <th scope="col">Carnet-Vence</th>
                                                                <th scope="col">Estado-Carnet</th>
                                                                <th scope="col">Actualizar</th>
                                                                <th scope="col">Nueva Formacion</th>
                                                                <th scope="col">Carnet</th>

                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                            <%
                                                                EstudiantesJpaController controlador = new EstudiantesJpaController();
                                                                List<Estudiantes> estudi = controlador.findEstudiantesEntities();

                                                                if (estudi.isEmpty()) {


                                                            %>

                                                            <tr>
                                                                <td colspan="11" class="text-center">No se encontraron Aprendiz en la base de datos.</td>
                                                            </tr>
                                                            <%                                                                } else {

                                                                for (Estudiantes est : estudi) {
                                                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                                    String fechaVencimiento = est.getVenceCarnet() != null ? dateFormat.format(est.getVenceCarnet()) : "Sin fecha";

                                                            %>

                                                            <tr>
                                                                <td> <%= est.getCedula()%> </td>
                                                                <td> <%= est.getTipoDocumentoFk().getNombre()%> </td>
                                                                <td> <%= est.getNombres()%> </td>
                                                                <td> <%= est.getApellidos()%> </td>
                                                                <td> <%= est.getFormacionFk().getNombre()%> </td>
                                                                <td> <%= est.getSedeFk().getNombre()%> </td>
                                                                <td> <%= est.getCorreo()%> </td>
                                                                <td> 
                                                                    <!-- Utiliza el atributo data-bs-html="true" para que el contenido del popover se interprete como HTML -->
                                                                    <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-html="true"
                                                                          data-bs-content='<img src="<%= est.getFotografia()%>" width="120px" height="120px">'
                                                                          <button class="btn btn-primary btn-md fw-bold" type="button" disabled>Foto</button>
                                                                    </span>
                                                                </td>
                                                                <td> <%= fechaVencimiento%> </td>
                                                                <td> <%= est.getEstadoCarnetIdestadoCarnet()%> </td>
                                                                <td> 
                                                                    <% SimpleDateFormat dateFormatInput2 = new SimpleDateFormat("yyyy-MM-dd");
                                                                        Date venceCarnet2 = est.getVenceCarnet();
                                                                        String fechaVencimiento22 = venceCarnet2 != null ? dateFormatInput2.format(venceCarnet2) : "Sin fecha";%>
                                                                    <button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" 
                                                                            data-bs-target="#formularioModal2" onclick="obtenerDatosEstudiantes('<%= est.getCedula()%>',
                                                                                            '<%= est.getTipoDocumentoFk().getIdTipoDocumento()%>',
                                                                                            '<%= est.getNombres()%>',
                                                                                            '<%= est.getApellidos()%>',
                                                                                            '<%= est.getFormacionFk().getIdFormacion()%>',
                                                                                            '<%= est.getSedeFk().getIdSede()%>',
                                                                                            '<%= est.getCorreo()%>',
                                                                                            '<%= fechaVencimiento22%>',
                                                                                            '<%= est.getEstadoCarnetIdestadoCarnet().getIdestadoCarnet()%>')">
                                                                        Actualizar
                                                                    </button>

                                                                </td>
                                                                <td> 
                                                                    <% SimpleDateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd");
                                                                        Date venceCarnet = est.getVenceCarnet();
                                                                        String fechaVencimiento2 = venceCarnet != null ? dateFormatInput.format(venceCarnet) : "Sin fecha";%>
                                                                    <button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" 
                                                                            data-bs-target="#formularioModal3" onclick="obtenerDatosEstudiantes2('<%= est.getCedula()%>',
                                                                                            '<%= est.getTipoDocumentoFk().getIdTipoDocumento()%>',
                                                                                            '<%= est.getNombres()%>',
                                                                                            '<%= est.getApellidos()%>',
                                                                                            '<%= est.getFormacionFk().getIdFormacion()%>',
                                                                                            '<%= est.getSedeFk().getIdSede()%>',
                                                                                            '<%= est.getCorreo()%>',
                                                                                            '<%= fechaVencimiento2%>',
                                                                                            '<%= est.getEstadoCarnetIdestadoCarnet().getIdestadoCarnet()%>')">
                                                                        Nueva Formacion
                                                                    </button>

                                                                </td>
                                                                <td> 
                                                                    <button type="button" class="btn btn-outline-danger btn-sm" onclick="verReporte4('<%= est.getCedula()%>')">
                                                                        Ver
                                                                    </button>
                                                                </td>

                                                            </tr>
                                                            <%
                                                                    }

                                                                }
                                                            %>
                                                        </tbody>
                                                    </table>
                                                    <%--CONTENIDO FINAL --%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>  
        </div>
        <%--CONTENIDO FINAL --%>

        <footer class="py-3 mt-2 text-center" style="background-color: #6acd56;">
            <div class="row">
                <div class="col-lg-4 col-md-6 col-sd-6 pt-3">
                    <img src="../img/icon_facebook.png" alt="alt"/>
                    <a  href="http://www.facebook.com">Facebook</a><br>
                    <img src="../img/icon_instagram.png" alt="alt"/>
                    <a href="http://www.instagram.com">Instagram</a><br>
                    <img src="../img/icon_github.png" alt="alt"/>
                    <a href="https://github.com/Crystian9513">Github</a>
                </div>
                <div class="col-lg-8 col-md-6 col-sd-6">

                    <h5 class="pt-2">Copyright <%= java.time.LocalDate.now().getYear()%>
                        Crystian Jesus Peralta Arias y Sebastian Navaja. <br>
                        Desarrollador Web.
                    </h5>
                    <h6>Telefono: +57 300 7836674 </h6>
                    <h6>Correo: crystian_9513@hotmail.com</h6>
                </div>
            </div>
        </footer>

        <!-- MODAL GUARDAR INICIO-->
        <div class="modal fade" id="formularioModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">

                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/estudiantesServlet" method="post" class="row g-2 " enctype="multipart/form-data">

                            <h2 class="pt-5 pb-4 text-center">Registrar Aprendiz</h2>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Cedula:</b></div>
                                    <input type="number" class="form-control" id="cedula" name="cedula" required min="1" max="999999999999">
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                                    <select name="tipoDocumento" id="tipoDocumento"
                                            class="from-selec-sm col-6" required>
                                        <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            TipodocumentoJpaController se = new TipodocumentoJpaController();
                                            List lista = se.findTipodocumentoEntities();

                                            for (int i = 0; i < lista.size(); i++) {
                                                Tipodocumento tipo = (Tipodocumento) lista.get(i);
                                                out.print("<option value='" + tipo.getIdTipoDocumento() + "'>");
                                                out.print(tipo.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>

                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Nombres: </b></div>
                                    <input type="text" class="form-control" id="nombres" name="nombres" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Apellidos:</b></div>
                                    <input type="text" class="form-control" id="apellidos" name="apellidos" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Formacion:</b></div>
                                    <select name="formacion" id="formacion"
                                            class="from-selec col-6 " required >
                                        <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            FormacionJpaController controF = new FormacionJpaController();
                                            List listaC = controF.findFormacionEntities();

                                            for (int i = 0; i < listaC.size(); i++) {
                                                Formacion form = (Formacion) listaC.get(i);
                                                out.print("<option value='" + form.getIdFormacion() + "'>");
                                                out.print(form.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Sede:</b></div>
                                    <select name="sede" id="sede"
                                            class="from-selec col-6" required min="1">
                                        <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            SedeJpaController ses = new SedeJpaController();
                                            List lista2 = ses.findSedeEntities();

                                            for (int i = 0; i < lista2.size(); i++) {
                                                Sede de = (Sede) lista2.get(i);
                                                out.print("<option value='" + de.getIdSede() + "'>");
                                                out.print(de.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Correo: </b></div>
                                    <input type="email" class="form-control" id="correo" name="correo" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fotografia:</b></div>
                                    <input type="file" class="form-control" id="foto" name="foto" accept="image/*" required>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fecha de Vencimiento del carnet:</b></div>
                                    <input type="date" class="form-control" id="vence" name="vence" required>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Estado del Carnet:</b></div>
                                    <select name="estado" id="estado"
                                            class="from-selec col-6" required min="1">
                                        <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            EstadoCarnetJpaController sese = new EstadoCarnetJpaController();
                                            List lista3 = sese.findEstadoCarnetEntities();

                                            for (int i = 0; i < lista3.size(); i++) {
                                                EstadoCarnet des = (EstadoCarnet) lista3.get(i);
                                                out.print("<option value='" + des.getIdestadoCarnet() + "'>");
                                                out.print(des.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!--bottones-->
                                <button type="submit" class="btn botones px-4 " style="background-color: #6acd56;"
                                        name="action" value="Guardar"><b>Guardar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>                           
        <!-- MODAL GUARDAR FINAL-->

        <!-- MODAL EDITAR INICIO-->
        <div class="modal fade" id="formularioModal2" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">

                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/estudiantesServlet" method="post" class="row g-2 "  enctype="multipart/form-data">

                            <h2 class="pt-5 pb-4 text-center">Aprendiz</h2>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Cedula:</b></div>
                                    <input type="number" class="form-control" id="cedula2" name="cedula2" required min="1" max="999999999999" readonly>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                                    <select name="tipoDocumento2" id="tipoDocumento2"
                                            class="from-selec-sm col-6" required>
                                        <option value="" disabled selected hidden>-- Elija --</option>

                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Nombres: </b></div>
                                    <input type="text" class="form-control" id="nombres2" name="nombres2" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Apellidos:</b></div>
                                    <input type="text" class="form-control" id="apellidos2" name="apellidos2" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Formacion:</b></div>
                                    <select name="formacion2" id="formacion2"
                                            class="from-selec col-6 " required min="1">
                                        <option value="" disabled selected hidden>-- Elija --</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Sede:</b></div>
                                    <select name="sede2" id="sede2"
                                            class="from-selec col-6" required min="1">
                                        <option value="" disabled selected hidden>-- Elija --</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Correo: </b></div>
                                    <input type="email" class="form-control" id="correo2" name="correo2" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fotografia:</b></div>
                                    <input type="file" class="form-control" id="foto2" name="foto2" accept="image/*" >
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fecha de Vencimiento del carnet:</b></div>
                                    <input type="date" class="form-control" id="vence2" name="vence2" >
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Estado del Carnet:</b></div>
                                    <select name="estado2" id="estado2"
                                            class="from-selec col-6" required min="1" >


                                    </select>
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!--bottones-->
                                <button type="submit" class="btn botones  px-4"
                                        name="action" value="Editar" style="background-color: #6acd56;"><b>Actualizar</b></button>
                                <!--   <button type="submit" class="btn " name="action" value="Eliminar" style="background-color: #6acd56;"><b>Eliminar</b></button>-->  
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>                           
        <!-- MODAL EDITAR FINAL-->

        <!-- MODAL EDITAR INICIO-->
        <div class="modal fade" id="formularioModal3" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">

                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/estudiantesServlet" method="post" class="row g-2 "  enctype="multipart/form-data">

                            <h2 class="pt-5 text-center">Aprendiz</h2>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Cedula:</b></div>
                                    <input type="number" class="form-control" id="cedula20" name="cedula20" required min="1" max="999999999999" readonly>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                                    <select name="tipoDocumento20" id="tipoDocumento20"
                                            class="from-selec-sm col-6" required>
                                        <option value="" disabled selected hidden>-- Elija --</option>

                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Nombres: </b></div>
                                    <input type="hidden" class="form-control" id="nombres20" name="nombres20" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Apellidos:</b></div>
                                    <input type="text" class="form-control" id="apellidos20" name="apellidos20" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Sede:</b></div>
                                    <select name="sede20" id="sede20"
                                            class="from-selec col-6" required min="1">
                                        <option value="" disabled selected hidden>-- Elija --</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Correo: </b></div>
                                    <input type="email" class="form-control" id="correo20" name="correo20" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Formacion:</b></div>
                                    <select name="formacion20" id="formacion20"
                                            class="from-selec col-6 " required min="1">
                                        <option value="" disabled selected hidden>-- Elija --</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fotografia:</b></div>
                                    <input type="file" class="form-control" id="foto20" name="foto20" accept="image/*" >
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fecha de Vencimiento del carnet:</b></div>
                                    <input type="date" class="form-control" id="vence20" name="vence20" >
                                </div>
                            </div>
                            <div class="col-12">

                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Estado del Carnet:</b></div>
                                    <select name="estado20" id="estado20"
                                            class="from-selec col-6" required min="1" >


                                    </select>
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!--bottones-->
                                <button type="submit" class="btn botones  px-4"
                                        name="action" value="NuevaFormacion" style="background-color: #6acd56;"><b>Actualizar</b></button>
                                <!--   <button type="submit" class="btn " name="action" value="Eliminar" style="background-color: #6acd56;"><b>Eliminar</b></button>-->  
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>                           
        <!-- MODAL EDITAR FINAL-->

        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="link">
                                                                        const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
                                                                        const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))
        </script> 

    </body>
</html>

<%
    String mensaje = request.getParameter("respuesta");

    if (mensaje != null) {

        switch (mensaje) {
            case "Existe":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡La cedula ya existe en la base de datos!',
            'warning'
            );
</script>
<%
        break;
    case "guardar":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El estudiante ha sido guardado!',
            'success'
            );
</script>
<%
        break;
    case "errorAlguardarr":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error al guardar!',
            'warning'
            );
</script>
<%
        break;
    case "eliminado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El estudiante ha sido eliminado!',
            'success'
            );
</script>
<%
        break;
    case "errorEliminar":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error al guardar!',
            'warning'
            );
</script>
<%
        break;
    case "edicionGuardad":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El estudiante ha sido actualizado!',
            'success'
            );
</script>
<%
        break;
    case "erroreditarr":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error al Actualizar!',
            'warning'
            );
</script>
<%
        break;
    case "guardarImportacion2":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡Importacion de Usuarios Exitosa!',
            'success'
            );
</script>
<%
        break;
    case "guardarAprendiz":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡Importacion de Aprendices Exitosa!',
            'success'
            );
</script>
<%
        break;
    case "ErrorImportacion":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error al Importar!',
            'warning'
            );
</script>
<%break;
            default:

        }
    }

%>

<script>
    const filtroInput = document.getElementById("filtro1");
    const filas = document.querySelectorAll("#tablaEstudiantes tbody tr");
    filtroInput.addEventListener("input", function () {
        const filtro = filtroInput.value.trim().toLowerCase();
        filas.forEach(function (fila) {
            const textoFila = fila.textContent.toLowerCase();
            if (textoFila.includes(filtro)) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    });</script>
<script>
    // Evitar el cierre del primer modal cuando se muestra el segundo modal
    $('#cameraModal').on('show.bs.modal', function () {
        $('#formularioModal').modal('hide');
        $('#formularioModal2').modal('hide');
    });

    // Mostrar el primer modal cuando se cierra el segundo modal
    $('#cameraModal').on('hidden.bs.modal', function () {
        $('#formularioModal').modal('show');
    });
</script>
