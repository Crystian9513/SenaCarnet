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
        <title>Area y Rol de Trabajo</title>
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
                $('#btnGuardarArea').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioArea').serialize();
                    formData += '&accion=guardar';

                    enviarPeticion(formData, handleSuccessGuardar, handleError);
                });

                // Manejador de evento para el botón de eliminar en el modal de edición
                $('#btnEliminarArea').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioAreaOpciones').serialize();
                    formData += '&accion=eliminar';

                    enviarPeticion(formData, handleSuccessEliminar, handleError);
                });

                // Manejador de evento para el botón de actualizar en el modal de edición
                $('#btnEditarArea').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioAreaOpciones').serialize();
                    formData += '&accion=actualizar';

                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                // Función para enviar la petición AJAX común
                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../AreaTrabajoServlet',
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
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de eliminar
                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de actualizar
                function handleSuccessActualizar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                $('#btnLimpiarModalAreas').click(function () {
                    limpiarFormulario('FormularioArea');
                });

                // Manejador de evento para limpiar los campos del modal cuando se oculta
                $('#ModalArea').on('hidden.bs.modal', function () {
                    $('#FormularioArea').trigger('reset');
                });

                // Función para manejar el error de la solicitud AJAX
                function handleError(errorMessage) {
                    mostrarError(errorMessage);
                }

                // Función para cargar y mostrar la tabla de personas
                function cargarTabla() {
                    $.ajax({
                        type: 'GET',
                        url: '../ConsultaAreaTrabajo',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaArea tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaArea tbody').append('<tr><td colspan="3" class="text-center">No se encontraron areas de trabajo en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, Area) {
                                    var row = '<tr>' +
                                            '<td>' + Area.codigo + '</td>' +
                                            '<td>' + Area.nombre + '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#ModalAreasOpciones" ' +
                                            'onclick="obtenerDatosAreas(' + Area.codigo + ', \'' + Area.nombre + '\')">Opciones</button>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaArea tbody').append(row);
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
                $('#btnGuardarRol').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioRol').serialize();
                    formData += '&accion=guardar';

                    enviarPeticion(formData, handleSuccessGuardar, handleError);
                });

                // Manejador de evento para el botón de eliminar en el modal de edición
                $('#btnEliminarRol').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioRolOpciones').serialize();
                    formData += '&accion=eliminar';

                    enviarPeticion(formData, handleSuccessEliminar, handleError);
                });

                // Manejador de evento para el botón de actualizar en el modal de edición
                $('#btnEditarRol').click(function (event) {
                    event.preventDefault();

                    var formData = $('#FormularioRolOpciones').serialize();
                    formData += '&accion=actualizar';

                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                // Función para enviar la petición AJAX común
                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../RolTrabajoServlet',
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
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de eliminar
                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de actualizar
                function handleSuccessActualizar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }
                
                $('#btnLimpiarModalRol').click(function () {
                    limpiarFormulario('FormularioRol');
                });
                
                // Manejador de evento para limpiar los campos del modal cuando se oculta
                $('#ModalArea').on('hidden.bs.modal', function () {
                    $('#FormularioRol').trigger('reset');
                });

                // Función para manejar el error de la solicitud AJAX
                function handleError(errorMessage) {
                    mostrarError(errorMessage);
                }

                // Función para cargar y mostrar la tabla de personas
                function cargarTabla() {
                    $.ajax({
                        type: 'GET',
                        url: '../ConsultaRolesTrabajo',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaRol tbody').empty();
                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaRol tbody').append('<tr><td colspan="3" class="text-center">No se encontraron rol de los trabajadores en la base de datos.</td></tr>');
                            } else {
                                $.each(data, function (index, rol) {
                                    var row = '<tr>' +
                                            '<td>' + rol.codigo + '</td>' +
                                            '<td>' + rol.nombre + '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" data-bs-target="#ModalRolOpciones" ' +
                                            'onclick="obtenerDatosRoles(' + rol.codigo + ', \'' + rol.nombre + '\')">Opciones</button>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaRol tbody').append(row);
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
                                        <div class="col-lg-10 col-md-10 col-sd-12"><h1 class="letra  pb-3">Informacion de Area de Trabajo </h1></div>
                                        <div class="col-lg-2 col-md-2 col-sd-12"><img class="float-end" src="../img/inicioSesion_sena.jpg" width="70px" height="70px" alt="alt"/></div>
                                    </div>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-6 col-sd-12">
                                            <div class="input-group mb-2">
                                                <div class="input-group-text col-md-6 col-8"><b>Nueva Area:</b> </div>
                                                <button type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalArea"><b>Formulario</b></button>
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
                                                                <table id="tablaArea" class="table table-striped table-sm  mb-0 text-center ">
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
                        <p class="letra pt-4">Informacion de Roles de Trabajo </p>
                        <div class="container">
                            <div class="row">
                                <div class="col-md-6 col-sd-12">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-md-6 col-8"><b>Nueva Rol:</b></div>
                                        <button id="editarBtnFormaciones" type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalRol">
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
                                    <table id="tablaRol" class="table table-striped table-sm  mb-0 text-center ">
                                        <thead class="" style="background-color: #018E42;">
                                            <tr>
                                                <th class="text-white">Codigo</th>
                                                <th class="text-white">Nombre</th>
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

        <jsp:include page="../Componentes/modales.jsp" ></jsp:include>  
        <jsp:include page="../Componentes/modalesEditar.jsp" ></jsp:include>  

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
                    $('#tablaArea tbody tr').filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(valorFiltro) > -1); // Mostrar u ocultar la fila según el filtro
                    });
                });

                // Manejador de evento para el cambio en el campo de búsqueda de formaciones
                $('#filtro2').on('input', function () {
                    var valorFiltro = $(this).val().toLowerCase(); // Obtener el valor del campo de búsqueda y convertirlo a minúsculas

                    // Iterar sobre cada fila de la tabla
                    $('#tablaRol tbody tr').filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(valorFiltro) > -1); // Mostrar u ocultar la fila según el filtro
                    });
                });
            });
</script>