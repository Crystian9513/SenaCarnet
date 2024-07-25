<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Base64"%>
<%@page import="entidades.Estudiantes"%>
<%@page import="controladores.EstudiantesJpaController"%>
<%@page import="controladores.EstadoCarnetJpaController"%>
<%@page import="entidades.EstadoCarnet"%>
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
        <title>Aprendiz</title>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="../css/app.css" rel="stylesheet">
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="../css/tabla.css"/>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="../js/alertas.js"></script>
        <script src="../js/app.js"></script>
         <%--letras --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&family=Carlito:wght@400;700&display=swap" rel="stylesheet">


        <script>
            function verReporte4(cedula) {
                // Redirigir al enlace con la cédula como parámetro
                window.open("<%= request.getContextPath()%>/reportes/reporteCarnetCompleto.jsp?cedula=" + cedula, "_blank");
            }
        </script>

        <script>
            $(document).ready(function () {
                // Manejador de evento para el botón de guardar en el formulario
                $('#btnGuardarEst').click(function (event) {
                    event.preventDefault();
                    var formData = new FormData($('#FormularioEstudiantes')[0]); // Selecciona el formulario y crea un objeto FormData
                    formData.append('accion', 'guardar'); // Agrega la acción como un parámetro adicional

                    enviarPeticion(formData, handleSuccessGuardar, handleError);
                });
                $('#btnEliminarEst').click(function (event) {
                    event.preventDefault();
                    var formData = new FormData($('#FormularioEstudiantesActualizar')[0]); // Selecciona el formulario y crea un objeto FormData
                    formData.append('accion', 'eliminar'); // Agrega la acción como un parámetro adicional

                    enviarPeticion(formData, handleSuccessEliminar, handleError);
                });
                $('#btnActualizarEst').click(function (event) {
                    event.preventDefault();
                    var formData = new FormData($('#FormularioEstudiantesActualizar')[0]); // Selecciona el formulario y crea un objeto FormData
                    formData.append('accion', 'actualizar'); // Agrega la acción como un parámetro adicional

                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });
                $('#btnNuevaFormacion').click(function (event) {
                    event.preventDefault();
                    var formData = new FormData($('#FormularioNuevaFormacion')[0]); // Selecciona el formulario y crea un objeto FormData
                    formData.append('accion', 'nuevaFormacion'); // Agrega la acción como un parámetro adicional

                    enviarPeticion(formData, handleSuccessNuevoModal, handleError);
                });
                // Función para enviar la petición AJAX común
                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../estudiantesServlet',
                        data: formData,
                        processData: false, // Evita que jQuery convierta el objeto FormData en una cadena
                        contentType: false, // Evita que jQuery establezca automáticamente el encabezado Content-Type
                        success: function (response) {
                            successCallback(response);
                        },
                        error: function (xhr, status, error) {
                            errorCallback('Error al conectar con el servlet: ' + error);
                        }
                    });
                }

                function limpiarFormulario(formularioId) {
                    $('#' + formularioId)[0].reset();
                }

                // Función para manejar la respuesta exitosa de la solicitud de guardar
                function handleSuccessGuardar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        limpiarFormulario('FormularioEstudiantes');
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de eliminar
                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarEstudinate");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de actualizar
                function handleSuccessActualizar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarEstudinate");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de nueva formacion
                function handleSuccessNuevoModal(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarNuevaFormacion");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                $('#btnLimpiarModalEstudinates').click(function () {
                    limpiarFormulario('FormularioEstudiantes');
                });
                // Manejador de evento para limpiar los campos del modal cuando se oculta
                $('#ModalEstudiantes').on('hidden.bs.modal', function () {
                    $('#FormularioEstudiantes').trigger('reset');
                });
                // Función para manejar el error de la solicitud AJAX
                function handleError(errorMessage) {
                    mostrarError(errorMessage);
                }

                function cargarTabla() {
                    $.ajax({
                        type: 'GET',
                        url: '../ConsultaEstudinates',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaEstudiantes tbody').empty();

                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaEstudiantes tbody').append('<tr><td colspan="13" class="text-center">No se encontraron estudiantes en la base de datos.</td></tr>');
                            } else {
                                // Iterar sobre los datos recibidos y agregar cada estudiante a la tabla
                                $.each(data, function (index, estudiante) {
                                    var row = '<tr>' +
                                            '<td>' + estudiante.cedula + '</td>' +
                                            '<td>' + estudiante.tipoDocumentoNombre + '</td>' +
                                            '<td>' + estudiante.nombres + '</td>' +
                                            '<td>' + estudiante.apellidos + '</td>' +
                                            '<td>' + estudiante.formacionNombre + '</td>' +
                                            '<td>' + estudiante.sedeNombre + '</td>' +
                                            '<td>' + estudiante.correo + '</td>' +
                                            '<td>' + estudiante.fechaVencimientoCarnet + '</td>' +
                                            '<td>' + estudiante.rh + '</td>' +
                                            '<td>' + estudiante.estadoCarnetNombre + '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" ' +
                                            'data-bs-target="#ModalEstudiantesActualizar" onclick="obtenerDatosEstudiantes(\'' +
                                            estudiante.cedula + '\', \'' + estudiante.tipoDocumentoId + '\', \'' +
                                            estudiante.nombres + '\', \'' + estudiante.apellidos + '\', \'' +
                                            estudiante.formacionId + '\', \'' + estudiante.sedeId + '\', \'' +
                                            estudiante.correo + '\', \'' + estudiante.fechaVencimientoCarnet + '\', \'' +
                                            estudiante.rh + '\', \'' + estudiante.estadoCarnetId + '\')">Actualizar</button>' +
                                            '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" ' +
                                            'data-bs-target="#ModalNuevaFormacion" onclick="obtenerDatosEstudiantes2(\'' +
                                            estudiante.cedula + '\', \'' + estudiante.tipoDocumentoId + '\', \'' +
                                            estudiante.nombres + '\', \'' + estudiante.apellidos + '\', \'' +
                                            estudiante.formacionId + '\', \'' + estudiante.sedeId + '\', \'' +
                                            estudiante.correo + '\', \'' + estudiante.fechaVencimientoCarnet + '\', \'' +
                                            estudiante.rh + '\', \'' + estudiante.estadoCarnetId + '\')">Nueva Formacion</button>' +
                                            '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" ' +
                                            'onclick="verReporte4(\'' + estudiante.cedula + '\')">Ver</button>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaEstudiantes tbody').append(row);
                                });
                            }
                        },
                        error: function (xhr, status, error) {
                            handleError('Error al obtener los datos: ' + error);
                        }
                    });
                }
                // Se llama a la funci?n cargarTabla para cargar y mostrar la tabla al cargar la p?gina por primera vez
                cargarTabla();
            });
        </script>

    </head>
    <body  style="background-color: #fefafb;">

        <div class="wrapper">

            <jsp:include page="../Componentes/Sidebar.jsp" ></jsp:include>
                <div class="main">
                <jsp:include page="../Componentes/nav.jsp" ></jsp:include>
                    <main class="content">
                        <div class="container">
                            <div class="row">
                                <div class="col-12">
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-lg-10 col-md-10 col-sd-12"><h1 class="letra  pb-3">Informacion del Aprendiz</h1></div>
                                            <div class="col-lg-2 col-md-2 col-sd-12"><img class="float-end" src="../img/inicioSesion_sena.jpg" width="70px" height="70px" alt="alt"/></div>
                                        </div>
                                    </div>
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-md-6 col-12">
                                                <div class="input-group mb-2">
                                                    <div class="input-group-text col-md-8 col-8"><b>Nuevo estudiante:</b></div>
                                                    <button id="" type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalEstudiantes"><b>Formulario</b></button>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-12">
                                                <div class="input-group mb-2">
                                                    <div class="input-group-text col-4"><b>Buscar:</b></div>
                                                    <input type="text" class="form-control" id="filtroEstudiantes">
                                                </div>
                                            </div>
                                       <%-- <div class="col-md-6 col-12">
                                               <form action="<%=request.getContextPath()%>/usuarioServlet" method="post" enctype="multipart/form-data" class="pt-2">
                                                   <div class="input-group mb-2">
                                                       <div class="input-group-text col-3"><b>Aprendices:</b></div>
                                                       <input type="file" class="form-control" name="file5" id="fileInput2" required="1" accept=".csv">
                                                       <button type="submit" class="btn text-white" name="action" value="ImportarEstudiantes" style="background-color: #018E42;"><b>Importar</b></button>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="col-md-6 col-12">
                                            <form action="<%=request.getContextPath()%>/usuarioServlet" method="post" enctype="multipart/form-data" class="pt-2">
                                                <div class="input-group mb-2">
                                                    <div class="input-group-text col-4"><b>Usuarios:</b></div>
                                                    <input type="file" class="form-control" name="file3" id="fileInput3" required="1" accept=".csv">
                                                    <button type="submit" class="btn text-white"  name="action " value="ImportarUsuarios" style="background-color: #018E42;"><b>Importar</b></button>
                                                </div>
                                            </form>
                                        </div>--%>
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
                                                                    <thead class="" style="background-color: #018E42;">
                                                                        <tr>
                                                                            <th class="text-white">Cedula</th>
                                                                            <th class="text-white">Tipo de Documento</th>
                                                                            <th class="text-white">Nombres</th>
                                                                            <th class="text-white">Apellidos</th>
                                                                            <th class="text-white">Formacion</th>
                                                                            <th class="text-white">Sede</th>
                                                                            <th class="text-white">Correo</th>
                                                                            <th class="text-white">Carnet-Vence</th>
                                                                            <th class="text-white">RH</th>
                                                                            <th class="text-white">Estado-Carnet</th>
                                                                            <th class="text-white">Actualizar</th>
                                                                            <th class="text-white">Nueva Formacion</th>
                                                                            <th class="text-white">Carnet</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                    </tbody>
                                                                </table>

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

                </main>
                <jsp:include page="../Componentes/footer2.jsp" ></jsp:include>

                </div>
            </div>
        <jsp:include page="../Componentes/modalesEditar.jsp" ></jsp:include>  
        <jsp:include page="../Componentes/modales.jsp" ></jsp:include>


        <script src="../js/DatosTablas.js"></script>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    </body>
</html>
<script>
            $(document).ready(function () {
                // Manejador de evento para el cambio en el campo de búsqueda
                $('#filtroEstudiantes').on('input', function () {
                    var valorFiltro = $(this).val().toLowerCase(); // Obtener el valor del campo de búsqueda y convertirlo a minúsculas

                    // Iterar sobre cada fila de la tabla
                    $('#tablaEstudiantes tbody tr').filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(valorFiltro) > -1); // Mostrar u ocultar la fila según el filtro
                    });
                });
            });
</script>