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
        response.sendRedirect("index.jsp");
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
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/tabla.css"/>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

        <style>
            #videoContainer {
                display: flex;
                flex-direction: row;
                align-items: flex-start;
                width: 100%;
                max-width: 300px;
            }

            #videoElement, #canvasElement, #fotoTomada {
                width: 100%;
                height: auto;
            }

            #canvasElement, #fotoTomada {
                display: none;
            }
        </style>

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
            function obtenerDatosEstudiantes(Cedula, TipoDocumentoFk, Nombres, Apellidos, FormacionFk, SedeFk, Correo, VenceCarnet)
            {
                $('#cedula2').val(Cedula);
                $('#tipoDocumento2').val(TipoDocumentoFk);
                $('#nombres2').val(Nombres);
                $('#apellidos2').val(Apellidos);
                $('#formacion2').val(FormacionFk);
                $('#sede2').val(SedeFk);
                $('#correo2').val(Correo);
                $('#vence2').val(VenceCarnet);


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
            }

        </script>


    </head>
    <body  style="background-color: #fefafb;">
        <%--MENU INICIO --%>
        <nav class="navbar text-l navbar-expand-lg "  style="background-color: #6acd56;">
            <div class="container">

                <div class="row">

                    <div class="col-md-2">
                        <a href="index.jsp">
                            <img class="" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">
                        </a>
                    </div>

                    <div class="col-md-2 text-center">
                        <h2 class="mt-3 letras"> SENA </h2>
                    </div>
                    <div class="col-md-7">
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
                                aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse mt-2" id="navbarNavDropdown">
                            <ul class="navbar-nav ms-auto navbar-brand">
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page" href="#">Estudiantes</a>
                                </li>
                                <li class="nav-item ">
                                    <a class="nav-link" href="administrador.jsp">Administradores</a>
                                </li>
                                <li class="nav-item ">
                                    <a class="nav-link" href="sedesFormaciones.jsp">Sedes y Formaciones</a>
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

                    <h1 class="letra text-center pt-5 pb-3">Informacion de Estudiantes</h1>

                    <div class="container">
                        <div class="row">
                            <div class="col-md-4 col-sd-12">
                                <form action="<%=request.getContextPath()%>" method="post" class="pt-2">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-md-6 col-sd-12"><b>Nuevo estudiante:</b></div>
                                        <button id="" type="button" class="btn" style="background-color: #6acd56;" data-bs-toggle="modal" data-bs-target="#formularioModal"><b>Formulario</b></button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-4 col-sd-12">
                                <form action="<%=request.getContextPath()%>" method="post" class="pt-2">
                                    <div class="input-group ">
                                        <div class="input-group-text col-md-8 col-sd-12"><b>Importar datos de Exel:</b></div>
                                        <button id="" type="button" class="btn" style="background-color: #6acd56;" ><b>Importar</b></button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-4 col-sd-12">
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
                                    <div class="row justify-content-center">
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
                                                                <th scope="col">Opcines</th>

                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                            <%
                                                                EstudiantesJpaController controlador = new EstudiantesJpaController();
                                                                List<Estudiantes> estudi = controlador.findEstudiantesEntities();

                                                                if (estudi.isEmpty()) {


                                                            %>

                                                            <tr>
                                                                <td colspan="10" class="text-center">No se encontraron Estudiantes en la base de datos.</td>
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
                                                                <td> 
                                                                    <% SimpleDateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd");
                                                                        Date venceCarnet = est.getVenceCarnet();
                                                                        String fechaVencimiento2 = venceCarnet != null ? dateFormatInput.format(venceCarnet) : "Sin fecha";%>
                                                                    <button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" 
                                                                            data-bs-target="#formularioModal2" onclick="obtenerDatosEstudiantes('<%= est.getCedula()%>',
                                                                                            '<%= est.getTipoDocumentoFk().getIdTipoDocumento()%>',
                                                                                            '<%= est.getNombres()%>',
                                                                                            '<%= est.getApellidos()%>',
                                                                                            '<%= est.getFormacionFk().getIdFormacion()%>',
                                                                                            '<%= est.getSedeFk().getIdSede()%>',
                                                                                            '<%= est.getCorreo()%>',
                                                                                            '<%= fechaVencimiento2%>')">
                                                                        Opciones
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
                    <a  href="http://www.facebook.com">Facebook</a>
                    <img src="../img/icon_instagram.png" alt="alt"/>
                    <a href="http://www.instagram.com">Instagram</a>
                    <img src="../img/icon_github.png" alt="alt"/>
                    <a href="https://github.com/Crystian9513">Github</a>
                </div>
                <div class="col-lg-8 col-md-6 col-sd-6">

                    <h5 class="pt-2">Copyright <%= new java.util.Date().getYear() + 1900%> Crystian Jesus Peralta Arias. <br>
                        Desarrollador Wed
                    </h5>

                </div>
            </div>
        </footer>

        <!-- MODAL GUARDAR INICIO-->
        <div class="modal fade" id="formularioModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">

                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/estudiantesServlet" method="post" class="row g-2 " enctype="multipart/form-data">

                            <h2 class="pt-5 pb-4 text-center">Estudiantes</h2>
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
                                            class="from-selec col-6 " required min="1">
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
                                    <input type="file" class="form-control" id="foto" name="foto" accept="image/*" >
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Tomar Foto:</b></div>
                                    <button id="openModalButton" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#cameraModal">Abrir Modal</button>
                                    
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text col-6"><b>Fecha de Vencimiento del carnet:</b></div>
                                    <input type="date" class="form-control" id="vence" name="vence" >
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

                            <h2 class="pt-5 pb-4 text-center">Estudiantes</h2>
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
                            <div class="col-12 text-center py-5 pt-5"><!--bottones-->
                                <button type="submit" class="btn botones  px-4"
                                        name="action" value="Editar" style="background-color: #6acd56;"><b>Actualizar</b></button>
                                <button type="submit" class="btn " name="action" value="Eliminar" style="background-color: #6acd56;"><b>Eliminar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>                           
        <!-- MODAL EDITAR FINAL-->

        <!-- Modal FOTO-->
        <div class="modal fade" id="cameraModal" tabindex="-1" aria-labelledby="cameraModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="cameraModalLabel">Funcionamiento de la cámara</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div id="videoContainer">
                            <video id="videoElement" autoplay></video>
                            <canvas id="canvasElement"></canvas>
                            <img id="fotoTomada" src="" alt="Foto Tomada">
                        </div>
                        <!-- Botones para abrir y cerrar la cámara -->
                        <button id="abrirCamara" class="btn btn-primary">Abrir Cámara</button>
                        <button id="cerrarCamara" class="btn btn-secondary" style="display:none;">Cerrar Cámara</button>
                        <button id="tomarFoto" class="btn btn-success">Tomar Foto</button>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="link">
                                                                                const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
                                                                                const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))
        </script> 
        <script>
            var videoStream;

            document.getElementById('abrirCamara').addEventListener('click', function () {
                navigator.mediaDevices.getUserMedia({video: true})
                        .then(function (stream) {
                            var video = document.getElementById('videoElement');
                            video.srcObject = stream;
                            video.play();
                            videoStream = stream;
                            document.getElementById('abrirCamara').style.display = 'none';
                            document.getElementById('cerrarCamara').style.display = 'inline-block';
                        })
                        .catch(function (error) {
                            console.log("Error al acceder a la cámara: ", error);
                        });
            });

            document.getElementById('cerrarCamara').addEventListener('click', function () {
                videoStream.getTracks().forEach(function (track) {
                    track.stop();
                });
                document.getElementById('abrirCamara').style.display = 'inline-block';
                document.getElementById('cerrarCamara').style.display = 'none';
                document.getElementById('videoElement').srcObject = null;
            });

            document.getElementById('tomarFoto').addEventListener('click', function () {
                var video = document.getElementById('videoElement');
                var canvas = document.getElementById('canvasElement');
                var context = canvas.getContext('2d');

                // Ajustar el tamaño del canvas al tamaño del video
                canvas.width = video.videoWidth;
                canvas.height = video.videoHeight;

                context.drawImage(video, 0, 0, canvas.width, canvas.height);
                var fotoTomada = canvas.toDataURL('image/png');

                // Mostrar la foto tomada
                var fotoTomadaElement = document.getElementById('fotoTomada');
                fotoTomadaElement.src = fotoTomada;
                fotoTomadaElement.style.display = 'inline-block';
                canvas.style.display = 'none'; // Oculta el canvas después de tomar la foto

                // Opcional: mostrar el botón de descargar
                var fotoCamara = document.getElementById('fotoCamara');
                fotoCamara.value = fotoTomada;
                fotoCamara.style.display = 'inline-block';
            });
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
<%break;
            default:
                throw new AssertionError();
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
