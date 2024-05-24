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
        <link href="../css/app.css" rel="stylesheet">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="../js/alertas.js"></script>
        <script src="../js/app.js"></script>

        <script>
            $(document).ready(function () {
                // Manejador de evento para el botón de guardar en el formulario
                $('#btnGuardarSede').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioSede').serialize();
                    formData += '&accion=guardar';

                    enviarPeticion(formData, handleSuccessGuardar, handleError);
                });

                // Manejador de evento para el botón de eliminar en el modal de edición
                $('#btnEliminarSede').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioSedeOpciones').serialize();
                    formData += '&accion=eliminar';

                    enviarPeticion(formData, handleSuccessEliminar, handleError);
                });

                // Manejador de evento para el botón de actualizar en el modal de edición
                $('#btnEditarSede').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioSedeOpciones').serialize();
                    formData += '&accion=actualizar';

                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                // Función para enviar la petición AJAX común
                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../SedesServlet',
                        data: formData,
                        success: function (response) {
                            successCallback(response);
                        },
                        error: function (xhr, status, error) {
                            errorCallback('Error al conectar con el servlet: ' + error);
                        }
                    });
                }

                // Función para manejar la respuesta exitosa de la solicitud de guardar
                function handleSuccessGuardar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        limpiarFormulario('FormularioSede')
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de eliminar
                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarSede");
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
                        var boton = document.getElementById("btnCerrarSede");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                $('#btnLimpiarModalSedes').click(function () {
                    limpiarFormulario('FormularioSede');
                });

                function limpiarFormulario(formularioId) {
                    $('#' + formularioId)[0].reset();
                }


                // Función para manejar el error de la solicitud AJAX
                function handleError(errorMessage) {
                    mostrarError(errorMessage);
                }

                // Función para cargar y mostrar la tabla de personas
                function cargarTabla() {
                    $.ajax({
                        type: 'GET',
                        url: '../ConsultaSede',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaSede tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaSede tbody').append('<tr><td colspan="3" class="text-center">No se encontraron sedes en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, sedes) {
                                    var row = '<tr>' +
                                            '<td>' + sedes.codigo + '</td>' +
                                            '<td>' + sedes.nombre + '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#ModalSedeOpciones" ' +
                                            'onclick="obtenerDatosSede(' + sedes.codigo + ', \'' + sedes.nombre + '\')">Opciones</button>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaSede tbody').append(row);
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

        <script>
            $(document).ready(function () {
                // Manejador de evento para el botón de guardar en el formulario
                $('#btnGuardadoFormacion').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioFormaciones').serialize();
                    formData += '&accion=guardar';

                    enviarPeticion(formData, handleSuccessGuardar, handleError);
                });

                // Manejador de evento para el botón de eliminar en el modal de edición
                $('#btnEliminarFormacion').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioFormacionesopciones').serialize();
                    formData += '&accion=eliminar';

                    enviarPeticion(formData, handleSuccessEliminar, handleError);
                });

                // Manejador de evento para el botón de actualizar en el modal de edición
                $('#btnActualizarFormacion').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioFormacionesopciones').serialize();
                    formData += '&accion=actualizar';

                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                // Función para enviar la petición AJAX común
                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../FormacionesServlet',
                        data: formData,
                        success: function (response) {
                            successCallback(response);
                        },
                        error: function (xhr, status, error) {
                            errorCallback('Error al conectar con el servlet: ' + error);
                        }
                    });
                }

                // Función para manejar la respuesta exitosa de la solicitud de guardar
                function handleSuccessGuardar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        limpiarFormulario('FormularioFormaciones')
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de eliminar
                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarFormacion");
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
                        var boton = document.getElementById("btnCerrarFormacion");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                $('#btnLimpiarModalFormaciones').click(function () {
                    limpiarFormulario('FormularioFormaciones');
                });

                function limpiarFormulario(formularioId) {
                    $('#' + formularioId)[0].reset();
                }

                // Función para manejar el error de la solicitud AJAX
                function handleError(errorMessage) {
                    mostrarError(errorMessage);
                }

                // Función para cargar y mostrar la tabla de personas
                function cargarTabla() {
                    $.ajax({
                        type: 'GET',
                        url: '../ConsultaFormaciones',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaFormacion tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaFormacion tbody').append('<tr><td colspan="4" class="text-center">No se encontraron formaciones en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, formaciones) {
                                    var row = '<tr>' +
                                            '<td>' + formaciones.codigo + '</td>' +
                                            '<td>' + formaciones.nombre + '</td>' +
                                            '<td>' + formaciones.sedeNombre + '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#ModalFormacionOpciones" ' +
                                            'onclick="obtenerDatosFormacion(' + formaciones.codigo + ', \'' + formaciones.nombre + '\', \'' + formaciones.sedeId + '\')">Opciones</button>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaFormacion tbody').append(row);
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

            <jsp:include page="../Componentes/Sidebar.jsp" ></jsp:include>
                <div class="main">
                <jsp:include page="../Componentes/nav.jsp" ></jsp:include>
                    <main class="content">
                    <%--CONTENIDO INICIO --%>
                    <div class="container">
                        <div class="row">
                            <div class="col-12">  
                                <div class="container">
                                    <div class="row">
                                        <div class="col-lg-10 col-md-10 col-sd-12"><h1 class="letra  pb-3">Informacion de Sedes </h1></div>
                                        <div class="col-lg-2 col-md-2 col-sd-12"><img class="float-end" src="../img/inicioSesion_sena.jpg" width="70px" height="70px" alt="alt"/></div>
                                    </div>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-6 col-sd-12">
                                            <div class="input-group mb-2">
                                                <div class="input-group-text col-md-6 col-8"><b>Nueva Sede:</b> </div>
                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalSedes"><b>Formulario</b></button>
                                            </div>
                                        </div> 
                                        <div class="col-md-6 col-sd-12">
                                            <div class="input-group mb-2">
                                                <div class="input-group-text col-4"><b>Buscar:</b></div>
                                                <input type="text" class="form-control" id="filtro1">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <section class="intro mb-2">
                                    <div class="bg-image" >
                                        <div class="mask d-flex align-items-center h-100">
                                            <div class="container tableContenido">
                                                <div class="row justify-content-center" data-aos="zoom-in"  data-aos-duration="500">
                                                    <div class="col-12"> 
                                                        <div class="card-body p-0 ">
                                                            <%--TABLA INICIO --%>
                                                            <div class="table-responsive table-scroll table-sm" data-mdb-perfect-scrollbar="true" style="position: relative; max-height: 200px">
                                                                <table id="tablaSede" class="table table-striped table-sm  mb-0 text-center ">
                                                                    <thead class="" style="background-color: #018E42;">
                                                                        <tr class="">
                                                                            <th class="text-white">Codigo</th>
                                                                            <th class="text-white">Nombres</th>
                                                                            <th class="text-white">Opcines</th>
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

                    <!-- CONTNIDO FORMACION INICIO -->
                    <div class="container py-1">
                        <p class="letra pt-4">Informacion de Formaciones </p>
                        <div class="container">
                            <div class="row">
                                <div class="col-md-6 col-sd-12">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-md-6 col-8"><b>Nueva Formacion:</b></div>
                                        <button id="editarBtnFormaciones" type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalFormaciones">
                                            <b>Formulario</b>
                                        </button>
                                    </div>
                                </div>
                                <div class="col-md-6 col-sd-12">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-4"><b>Buscar:</b></div>
                                        <input type="text" class="form-control" id="filtro2">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" data-aos="zoom-in"  data-aos-duration="500">
                            <div class="col-md-12" data-aos="zoom-in"  data-aos-duration="500">
                                <!-- TABLA FORMACIONES INICIO -->
                                <div class="table-responsive table-scroll table-sm" data-mdb-perfect-scrollbar="true" style="position: relative; max-height: 400px">
                                    <table id="tablaFormacion" class="table table-striped table-sm  mb-0 text-center ">
                                        <thead class="" style="background-color: #018E42;">
                                            <tr>
                                                <th class="text-white">Codigo</th>
                                                <th class="text-white">Nombre</th>
                                                <th class="text-white">Sede</th>
                                                <th class="text-white">Opciones </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
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
                $('#filtro1').on('input', function () {
                    var valorFiltro = $(this).val().toLowerCase(); // Obtener el valor del campo de búsqueda y convertirlo a minúsculas

                    // Iterar sobre cada fila de la tabla
                    $('#tablaSede tbody tr').filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(valorFiltro) > -1); // Mostrar u ocultar la fila según el filtro
                    });
                });

                // Manejador de evento para el cambio en el campo de búsqueda de formaciones
                $('#filtro2').on('input', function () {
                    var valorFiltro = $(this).val().toLowerCase(); // Obtener el valor del campo de búsqueda y convertirlo a minúsculas

                    // Iterar sobre cada fila de la tabla
                    $('#tablaFormacion tbody tr').filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(valorFiltro) > -1); // Mostrar u ocultar la fila según el filtro
                    });
                });
            });
</script>