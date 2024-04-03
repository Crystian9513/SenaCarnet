<%@page import="entidades.Usuarios"%>
<%@page import="entidades.Usuarios"%>
<%@page import="entidades.Formacion"%>
<%@page import="controladores.FormacionJpaController"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Sede"%>
<%@page import="controladores.SedeJpaController"%>
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
        <title>Sedes y Formaciones</title>
        <link rel="stylesheet" href="../css/tabla.css"/>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

        <script>
            // Función para obtener los datos de la sede y mostrarlos en el modal
            function obtenerDatosSede(idSede, nombreSede) {
                // Actualiza los campos del modal con los datos de la sede seleccionada
                document.getElementById('codigoModal2').value = idSede;
                document.getElementById('nombreModal2').value = nombreSede;
            }
        </script>

        <script>
            function obtenerDatosFormacion(IdFormacion, Nombre, SedeId) {
                // Actualiza los campos del modal con los datos de la formación seleccionada
                $("#codigoModal5").val(IdFormacion);
                $("#nombreModal5").val(Nombre);
                $("#SedesLista2").val(SedeId);

                // Realiza la solicitud AJAX para obtener los datos adicionales si es necesario
                $.ajax({
                    type: "POST",
                    url: "../Busquedas/obtenerSede.jsp",
                    data: {idsede: SedeId},
                    dataType: "html",
                    success: function (data) {
                        $("#SedesLista2").empty().append(data);
                    }
                });
            }
        </script>
    </head>
    <body  style="background-color: #fefafb;">     

        <%--MENU INICIO --%>
        <nav class="navbar text-l navbar-expand-lg "  style="background-color: #6acd56;">
            <div class="container justify-content-center align-items-center">
                <div class="col-md-2 col-12 text-center">

                    <img class="" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">

                </div>
                <div class="col-md-2 col-12 text-center">
                    <h2 class="mt-3 letras"> SENA </h2>
                </div>
                <div class="col-md-8 col-12 text-center">
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
                                    <li><a class="dropdown-item" href="estudiantes.jsp">Ingresar</a></li>
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
                                <a class="nav-link" href="#">Sede-Formacion</a>
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
        </nav>
        <%--MENU FINAL --%>

        <%--CONTENIDO INICIO --%>
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <h1 class="letra text-center pt-3 pb-3">Informacion de Sedes</h1>
                    <div class="container">
                        <div class="row">
                            <div class="col-md-6 col-sd-12">
                                <form action="<%=request.getContextPath()%>" method="post" class="pt-2">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-md-6 col-8"><b>Nueva Sede:</b></div>
                                        <button type="button" class="btn" style="background-color: #6acd56;" data-bs-toggle="modal" data-bs-target="#formularioModal"><b>Formulario</b></button>
                                    </div>
                                </form>
                            </div> 
                            <div class="col-md-6 col-sd-12">
                                <form action="<%=request.getContextPath()%>" method="post" class="pt-2">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-4"><b>Buscar:</b></div>
                                        <input type="text" class="form-control" id="filtro1">
                                    </div>
                                </form>
                            </div>

                        </div>
                    </div>

                    <section class="intro mb-2">
                        <div class="bg-image" >
                            <div class="mask d-flex align-items-center h-100">
                                <div class="container tableContenido">
                                    <div class="row justify-content-center" data-aos="zoom-in"  data-aos-duration="500">
                                        <div class="col-12 tableContenido"> 
                                            <div class="card-body p-0 ">
                                                <%--TABLA INICIO --%>
                                                <div class="table-responsive table-scroll table-sm" data-mdb-perfect-scrollbar="true" style="position: relative; max-height: 200px">
                                                    <table id="tablaSede" class="table table-striped table-sm  mb-0 text-center ">
                                                        <thead class="" style="background-color: #263642;">
                                                            <tr class="text-light">
                                                                <th scope="col">Codigo</th>
                                                                <th scope="col">Nombres</th>
                                                                <th scope="col">Opcines</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                            <%
                                                                SedeJpaController controlador = new SedeJpaController();
                                                                List<Sede> sede = controlador.findSedeEntities();

                                                                if (sede.isEmpty()) {

                                                            %>

                                                            <tr>
                                                                <td colspan="3" class="text-center">No se encontraron Sedes en la base de datos.</td>
                                                            </tr>
                                                            <%                                                                } else {
                                                                for (Sede des : sede) {
                                                            %>
                                                            <tr>
                                                                <td> <%= des.getIdSede()%> </td>
                                                                <td> <%= des.getNombre()%></td>

                                                                <td> 
                                                                    <button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" 
                                                                            data-bs-target="#formulario2Modal" onclick="obtenerDatosSede('<%= des.getIdSede()%>',
                                                                                            '<%= des.getNombre()%>')" >
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

        <!-- CONTNIDO FORMACION INICIO -->
        <div class="container py-1">
            <p class="letra text-center py-4">Informacion de Formaciones </p>
            <div class="container">
                <div class="row">
                    <form action="<%=request.getContextPath()%>/estudiantesServlet" method="post" class="row g-2 " enctype="multipart/form-data">
                        <div class="col-md-6 col-sd-12">
                            <div class="input-group mb-2">
                                <div class="input-group-text col-md-6 col-8"><b>Nueva Formacion:</b></div>
                                <button id="editarBtnFormaciones" type="button" class="btn " style="background-color: #6acd56;" data-bs-toggle="modal" data-bs-target="#formulario3Modal"><b>Formulario</b></button>
                            </div>
                        </div>
                             
                        <div class="col-md-6 col-sd-12">
                            <div class="input-group mb-2">
                                <div class="input-group-text col-4"><b>Buscar:</b></div>
                                <input type="text" class="form-control" id="filtro2">
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <div class="row" data-aos="zoom-in"  data-aos-duration="500">
                <div class="col-md-12" data-aos="zoom-in"  data-aos-duration="500">
                    <!-- TABLA FORMACIONES INICIO -->
                    <div class="table-responsive table-scroll table-sm" data-mdb-perfect-scrollbar="true" style="position: relative; max-height: 400px">
                        <table id="tablaFormacion" class="table table-striped table-sm  mb-0 text-center ">
                            <thead class="" style="background-color: #263642;">
                                <tr>
                                    <th scope="col">Codigo</th>
                                    <th scope="col">Nombre</th>
                                    <th scope="col">Formacion</th>
                                    <th scope="col">Opcciones </th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    FormacionJpaController controFormacion = new FormacionJpaController();
                                    List<Formacion> forma = controFormacion.findFormacionEntities();

                                    if (forma.isEmpty()) {
                                %>
                                <tr>
                                    <td colspan="4">No se encontraron Formaciones en la base de datos.</td>
                                </tr>
                                <%
                                } else {
                                    for (Formacion sion : forma) {


                                %>
                                <tr> 
                                    <td> <%= sion.getIdFormacion()%> </td>
                                    <td> <%= sion.getNombre()%> </td>
                                    <td> <%= sion.getSedeId().getNombre()%> </td>

                                    <td>
                                        <button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#formulario4Modal"
                                                onclick="obtenerDatosFormacion(<%= sion.getIdFormacion()%>, '<%= sion.getNombre()%>',
                                                            '<%= sion.getSedeId().getIdSede()%>')" >
                                            Opciones
                                        </button>
                                    </td>
                                </tr>

                                <%    }
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- TABLA FORMACIONES FINAL -->

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

        <!-- MODALES DE SEDES GUARDAR INICIO -->
        <div class="modal fade" id="formularioModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/SedesServlet" method="post" class="row g-2 "
                              onsubmit="return validarCamposVacios();"
                              >
                            <h2 class="pt-5 pb-4 text-center">Registrar Sede</h2>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Codigo:</b></div>
                                    <input type="number" class="form-control" id="codigoModal" name="codigo" required min="1" max="999999999999">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Nombre:</b></div>

                                    <input type="text" class="form-control" id="nombreModal" name="nombre" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                                <button type="submit" class="btn botones  px-4"
                                        value="Guardar" name="action" style="background-color: #6acd56;"><b>Guardar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODALES DE SEDES GUARDAR FINAL -->

        <!-- MODALES DE SEDES OPCIONES INICIO -->
        <div class="modal fade" id="formulario2Modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/SedesServlet" method="post" class="row g-2 " >
                            <h2 class="pt-5 pb-4 text-center">Sede</h2>
                            <div class="col-12">
                                <div class="input-group m">
                                    <div class="input-group-text col-5"><b>Codigo:</b></div>
                                    <input type="number" class="form-control" id="codigoModal2" name="codigoEliminar" required min="1" readonly>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group ">
                                    <div class="input-group-text col-5"><b>Nombre:</b></div>

                                    <input type="text" class="form-control" id="nombreModal2" name="nombreEliminar" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
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
        <!-- MODALES DE SEDES OPCIONES FINAL -->

        <!-- MODALES DE FORMACIONES GUARDAR INICIO -->
        <div class="modal fade" id="formulario3Modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/FormacionesServlet" method="post" class="row g-2 "
                              onsubmit="return validarCamposVacios2();">
                            <h2 class="pt-5 pb-4 text-center">Registrar Formacion</h2>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Codigo:</b></div>

                                    <input type="number" class="form-control" id="codigoModal3" name="codigo2" required min="1" max="999999999999">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Nombre:</b></div>
                                    <input type="text" class="form-control" id="nombreModal3" name="nombre2" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Sedes</b></div>
                                    <select name="SedesLista" id="SedesLista"
                                            class="from-selec col-7"  required>
                                        <option value="" disabled selected hidden>-- Elija --</option>
                                        <%
                                            SedeJpaController se = new SedeJpaController();
                                            List lista = se.findSedeEntities();

                                            for (int i = 0; i < lista.size(); i++) {
                                                Sede de = (Sede) lista.get(i);
                                                out.print("<option value='" + de.getIdSede() + "'>");
                                                out.print(de.getNombre());
                                                out.print("</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                                <button type="submit" class="btn botones px-4 "  style="background-color: #6acd56;"
                                        name="action" value="Guardar" ><b>Guardar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- MODALES DE FORMACIONES GUARDAR FINAL -->

        <!-- MODALES DE FORMACIONES OPCIONES INICIO -->
        <div class="modal fade" id="formulario4Modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <form action="<%=request.getContextPath()%>/FormacionesServlet" method="post" class="row g-2 "
                              >
                            <h2 class="pt-5 pb-4 text-center">Formacion</h2>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Codigo:</b></div>
                                    <input type="number" class="form-control" id="codigoModal5" name="codigoEliminar2" required min="1" readonly>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Nombre:</b></div>
                                    <input type="text" class="form-control" id="nombreModal5" name="nombreEliminar2" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Sedes</b></div>
                                    <select name="SedesLista2" id="SedesLista2"
                                            class="from-selec col-7"  required>

                                    </select>
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
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

        <!-- MODALES DE FORMACIONES OPCIONES FINAL -->


        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

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
                                      'El codigo ya existe en la base de datos',
                                      'warning'
                                      );
</script>

<%
        break;
    case "Guardado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡La sede se ha sido guardada!',
            'success'
            );
</script>
<%
        break;
    case "errorguardar":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '!Error al Guardar¡',
            'warning'
            );
</script>
<%
        break;
    case "SedeEliminada":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡La sede ha sido eliminada!',
            'success'
            );
</script>
<%
        break;
    case "ErrorEliminada":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error al Eliminar!',
            'warning'
            );
</script>
<%
        break;
    case "Editado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡La sede ha sido actualizada!',
            'success'
            );
</script>
<%
        break;
    case "erroreditar":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '!Error al Editar¡',
            'warning'
            );
</script>
<%
        break;
    case "existeFormacion":
%>
<script>
    Swal.fire(
            '¡Oops!',
            'El codigo ya existe en la base de datos',
            'warning'
            );
</script>
<%
        break;
    case "guardadoFormacion":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡La Formacion ha sido guardada!',
            'success'
            );
</script>
<%
        break;
    case "errorEliminada":
%>
<script>
    Swal.fire(
            '¡Oops!',
            'Error al Eliminar',
            'warning'
            );
</script>
<%
        break;
    case "FormacionEliminada":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡La formacion ha sido eliminada!',
            'success'
            );
</script>
<%
        break;
    case "Ediciónguardada":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡La formacion ha sido actualizada!',
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
            'Error al Editar',
            'warning'
            );
</script>
<%
        break;
    case "ArchivosImportados":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡Archivo Importado!',
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
            'Error al Importar',
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
    const filas = document.querySelectorAll("#tablaSede tbody tr");

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
    });
</script>
<script>
    const filtroInput2 = document.getElementById("filtro2");
    const filasFormacion = document.querySelectorAll("#tablaFormacion tbody tr"); // Cambio de nombre aquí
    filtroInput2.addEventListener("input", function () {
        const filtro = filtroInput2.value.trim().toLowerCase();

        filasFormacion.forEach(function (fila) { // Cambio de nombre aquí también
            const textoFila = fila.textContent.toLowerCase();
            if (textoFila.includes(filtro)) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    });
</script>
