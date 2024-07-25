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

    Usuarios usuario = (Usuarios) sesion.getAttribute("coordinador");

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
        <title>Coordinador</title>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link rel="stylesheet" href="../css/tabla.css"/>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <link href="../css/app.css" rel="stylesheet">
        <script src="../js/alertas.js"></script>
        <script src="../js/app.js"></script>
        <%--letras --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&family=Carlito:wght@400;700&display=swap" rel="stylesheet">


        <script>
            $(document).ready(function () {
                // Manejador de evento para el botón de guardar en el formulario
                $('#btnEliminarEstudiante').click(function (event) {
                    event.preventDefault();

                    var formData = new FormData($('#FormularioEliminadarEstudiante')[0]);  // Selecciona el formulario y crea un objeto FormData
                    formData.append('accion', 'EliminarEstudiante');  // Agrega la acción como un parámetro adicional

                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                // Función para enviar la petición AJAX común
                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../CoordinadorServlet',
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

                // Función para manejar la respuesta exitosa de la solicitud de actualizar
                function handleSuccessActualizar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        $('#ModalEliminarEstudiante').modal('hide');
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Manejador de evento para limpiar los campos del modal cuando se oculta
                $('#ModalEliminarEstudiante').on('hidden.bs.modal', function () {
                    $('#FormularioEliminadarEstudiante').trigger('reset');
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
                            $('#tablaEliminar tbody').empty();

                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaEliminar tbody').append('<tr><td colspan="9" class="text-center">No se encontraron aprendices en la base de datos.</td></tr>');
                            } else {
                                // Iterar sobre los datos recibidos y agregar cada estudiante a la tabla
                                $.each(data, function (index, estudiante) {
                                    var row = '<tr>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#ModalEliminarEstudiante" ' +
                                            'onclick="obtenerDatosEliminarcarnet(\'' +
                                            estudiante.cedula + '\', \'' + estudiante.tipoDocumentoId + '\', \'' +
                                            estudiante.nombres + '\', \'' + estudiante.apellidos + '\', \'' +
                                            estudiante.formacionId + '\', \'' + estudiante.sedeId + '\', \'' +
                                            estudiante.correo + '\', \'' + estudiante.fechaVencimientoCarnet + '\', \'' +
                                            estudiante.estadoCarnetId + '\')">Eliminar Carnet</button>' +
                                            '</td>' +
                                            '<td>' + estudiante.estadoCarnetNombre + '</td>' +
                                            '<td>' + estudiante.cedula + '</td>' +
                                            '<td>' + estudiante.tipoDocumentoNombre + '</td>' +
                                            '<td>' + estudiante.nombres + '</td>' +
                                            '<td>' + estudiante.apellidos + '</td>' +
                                            '<td>' + estudiante.formacionNombre + '</td>' +
                                            '<td>' + estudiante.sedeNombre + '</td>' +
                                            '<td>' + estudiante.fechaVencimientoCarnet + '</td>' +
                                            '</tr>';
                                    $('#tablaEliminar tbody').append(row);
                                });
                            }
                        },
                        error: function (xhr, status, error) {
                            handleError('Error al obtener los datos: ' + error);
                        }
                    });
                }
                // Se llama a la función cargarTabla para cargar y mostrar la tabla al cargar la página por primera vez
                cargarTabla();
            });
        </script>

    </head>
    <body  style="background-color: #fefafb;">
        <div class="wrapper">
            <nav id="sidebar" class="sidebar js-sidebar ">
                <div class="sidebar-content js-simplebar ">
                    <a class="sidebar-brand" href="../vistas/menuPrincipal.jsp">
                        <span class="align-middle">Eliminacion De Carnet</span>
                    </a>
                    <ul class="sidebar-nav">
                        <li class="sidebar-header">
                            Menu
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link" href="../vistas/cerrarSesionCoordinador.jsp">
                                <i class="align-middle" data-feather="square"></i> <span class="align-middle">Salir</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="main">
                <jsp:include page="../Componentes/nav.jsp" ></jsp:include>
                    <main class="content">
                    <%--CONTENIDO INICIO --%>
                    <div class="container">
                        <div class="row">
                            <div class="col-12">
                                <h1 class="letra3 text-center pb-3">Informacion de Aprendices <img  src="../img/inicioSesion_sena.jpg" width="70px" height="70px" alt="alt"/></h1>
                                <h3 class="text-center">Bienvenido  <%if (usuario != null) {
                                        out.print(usuario.getNombres());
                                    }%> aqui podra buscar y eliminar los carnet de los Aprendices.</h3>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-12 col-sd-12">
                                            <div class="input-group mb-2">
                                                <div class="input-group-text col-4"><b>Buscar:</b></div>
                                                <input type="text" class="form-control" id="filtroEliminarCarnet">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <section class="intro mb-2">
                                    <div class="bg-image">
                                        <div class="mask d-flex align-items-center h-100">
                                            <div class="container ">
                                                <div class="row justify-content-center" data-aos="zoom-in"  data-aos-duration="500">
                                                    <div class="col-12 "> 
                                                        <div class="card-body p-0 ">
                                                            <%--TABLA INICIO --%>
                                                            <div class="table-responsive table-scroll" data-mdb-perfect-scrollbar="true" style="position: relative; height: 400px">
                                                                <table id="tablaEliminar" class="table table-striped table-sm mb-0 text-center ">
                                                                    <thead class="" style="background-color: #018E42;">
                                                                        <tr>
                                                                            <th class="text-white">Eliminar-Carnet</th>
                                                                            <th class="text-white">Estado-Carnet</th>
                                                                            <th class="text-white">Cedula</th>
                                                                            <th class="text-white">Tipo de Documento</th>
                                                                            <th class="text-white">Nombres</th>
                                                                            <th class="text-white">Apellidos</th>
                                                                            <th class="text-white">Formacion</th>
                                                                            <th class="text-white">Sede</th>
                                                                            <th class="text-white">Carnet-Vence</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
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
                </main>
                <jsp:include page="../Componentes/footer2.jsp" ></jsp:include>

                </div>
            </div>



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
                $('#filtroEliminarCarnet').on('input', function () {
                    var valorFiltro = $(this).val().toLowerCase(); // Obtener el valor del campo de búsqueda y convertirlo a minúsculas

                    // Iterar sobre cada fila de la tabla
                    $('#tablaEliminar tbody tr').filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(valorFiltro) > -1); // Mostrar u ocultar la fila según el filtro
                    });
                });
            });
</script>
