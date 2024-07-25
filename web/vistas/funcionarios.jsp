
<%@page import="entidades.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Funcionarios</title>
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
            function verReporte5(cedula) {
                // Redirigir al enlace con la cédula como parámetro
                window.open("<%= request.getContextPath()%>/reportes/reporteCarnetFuncionario.jsp?cedula=" + cedula, "_blank");
            }
        </script>

        <script>
            $(document).ready(function () {
                // Manejador de evento para el botón de guardar en el formulario
                $('#btnGuardarFun').click(function (event) {
                    event.preventDefault();

                    var formData = new FormData($('#FormularioFuncionarios')[0]);  // Selecciona el formulario y crea un objeto FormData
                    formData.append('accion', 'guardar');  // Agrega la acción como un parámetro adicional

                    enviarPeticion(formData, handleSuccessGuardar, handleError);
                });

                $('#btnEliminarFun').click(function (event) {
                    event.preventDefault();

                    var formData = new FormData($('#FormularioEditarFuncionarios')[0]);  // Selecciona el formulario y crea un objeto FormData
                    formData.append('accion', 'eliminar');  // Agrega la acción como un parámetro adicional

                    enviarPeticion(formData, handleSuccessEliminar, handleError);
                });

                $('#btnActualizarFun').click(function (event) {
                    event.preventDefault();

                    var formData = new FormData($('#FormularioEditarFuncionarios')[0]);  // Selecciona el formulario y crea un objeto FormData
                    formData.append('accion', 'actualizar');  // Agrega la acción como un parámetro adicional

                    enviarPeticion(formData, handleSuccessActualizar, handleError);
                });

                // Función para enviar la petición AJAX común
                function enviarPeticion(formData, successCallback, errorCallback) {
                    $.ajax({
                        type: 'POST',
                        url: '../FuncionarioServlets',
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

                $('#btnLimpiarModalFuncionario').click(function () {
                    limpiarFormulario('FormularioFuncionarios');
                });

                // Función para manejar la respuesta exitosa de la solicitud de guardar
                function handleSuccessGuardar(response) {
                    if (response.estado === "exito") {
                        mostrarExito(response.mensaje);
                        limpiarFormulario('FormularioFuncionarios');
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                // Función para manejar la respuesta exitosa de la solicitud de eliminar
                function handleSuccessEliminar(response) {
                    if (response.estado === "exito") {
                        var boton = document.getElementById("btnCerrarfuncionario");
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
                        var boton = document.getElementById("btnCerrarfuncionario");
                        boton.click();
                        mostrarExito(response.mensaje);
                        cargarTabla();
                    } else {
                        mostrarError(response.mensaje);
                    }
                }

                $('#btnLimpiarModalFuncionarios').click(function () {
                    limpiarFormulario('FormularioFuncionarios');
                });

                // Manejador de evento para limpiar los campos del modal cuando se oculta
                $('#ModalFuncionarios').on('hidden.bs.modal', function () {
                    $('#FormularioFuncionarios').trigger('reset');
                });

                // Función para manejar el error de la solicitud AJAX
                function handleError(errorMessage) {
                    mostrarError(errorMessage);
                }

                function cargarTabla() {
                    $.ajax({
                        type: 'GET',
                        url: '../ConsultaFuncionarios',
                        dataType: 'json',
                        success: function (data) {
                            $('#tablaFuncionarios tbody').empty();

                            if (data.length === 0) {
                                // Si no hay datos, agregar una fila indicando que no se encontraron estudiantes
                                $('#tablaFuncionarios tbody').append('<tr><td colspan="11" class="text-center">No se encontraron funcionarios en la base de datos.</td></tr>');
                            } else {
                                // Iterar sobre los datos recibidos y agregar cada estudiante a la tabla
                                $.each(data, function (index, funcionario) {
                                    var row = '<tr>' +
                                            '<td>' + funcionario.cedula + '</td>' +
                                            '<td>' + funcionario.tipoDocumentoNombre + '</td>' +
                                            '<td>' + funcionario.nombre + '</td>' +
                                            '<td>' + funcionario.apellido + '</td>' +
                                            '<td>' + funcionario.funcionarioNombre + '</td>' +
                                            '<td>' + funcionario.areaTrabajonombre + '</td>' +
                                            '<td>' + funcionario.correo + '</td>' +
                                            '<td>' + funcionario.fechaVencimientoCarnet + '</td>' +
                                            '<td>' + funcionario.rh + '</td>' +
                                            '<td>' + funcionario.estadoCarnetNombre + '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" ' +
                                            'data-bs-target="#ModalEditarFuncionarios" onclick="obtenerDatosFuncionarios(\'' +
                                            funcionario.cedula + '\', \'' + funcionario.tipoDocumentoId + '\', \'' +
                                            funcionario.nombre + '\', \'' + funcionario.apellido + '\', \'' + funcionario.areaTrabajoId + '\',\'' + funcionario.funcionarioId + '\',\'' +
                                            funcionario.correo + '\', \'' + funcionario.fechaVencimientoCarnet + '\', \'' +
                                            funcionario.rh + '\', \'' + funcionario.estadoCarnetid + '\')">Actualizar</button>' +
                                            '</td>' +
                                            '<td>' +
                                            '<button type="button" class="btn btn-outline-danger btn-sm" ' +
                                            'onclick="verReporte5(\'' + funcionario.cedula + '\')">Ver</button>' +
                                            '</td>' +
                                            '</tr>';
                                    $('#tablaFuncionarios tbody').append(row);
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
    <body>
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
                                            <div class="col-lg-10 col-md-10 col-sd-12"><h1 class="letra  pb-3">Informacion del Funcionario</h1></div>
                                            <div class="col-lg-2 col-md-2 col-sd-12"><img class="float-end" src="../img/inicioSesion_sena.jpg" width="70px" height="70px" alt="alt"/></div>
                                        </div>
                                    </div>
                                    <div class="container">
                                        <div class="row">
                                            <div class="col-md-6 col-12">
                                                <div class="input-group mb-2">
                                                    <div class="input-group-text col-md-8 col-8"><b>Nuevo Funcionario</b></div>
                                                    <button id="" type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#ModalFuncionarios"><b>Formulario</b></button>
                                                </div>
                                            </div>
                                            <div class="col-md-6 col-12">
                                                <div class="input-group mb-2">
                                                    <div class="input-group-text col-4"><b>Buscar:</b></div>
                                                    <input type="text" class="form-control" id="filtroEstudiantes">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <section class="intro mb-5">
                                        <div class="bg-image" >
                                            <div class="mask d-flex align-items-center ">
                                                <div class="container tableContenido">
                                                    <div class="row justify-content-center" data-aos="zoom-in"  data-aos-duration="500">
                                                        <div class="col-12 tableContenido"> 
                                                            <div class="card-body p-0 ">
                                                            <%--TABLA INICIO --%>
                                                            <div class="table-responsive table-scroll" data-mdb-perfect-scrollbar="true" style="position: relative; height: 700px">
                                                                <table id="tablaFuncionarios" class="table table-striped table-sm mb-0 text-center ">
                                                                    <thead class="" style="background-color: #018E42;">
                                                                        <tr>
                                                                            <th class="text-white">Cedula</th>
                                                                            <th class="text-white">Tipo de Documento</th>
                                                                            <th class="text-white">Nombres</th>
                                                                            <th class="text-white">Apellidos</th>
                                                                            <th class="text-white">Rol</th>
                                                                            <th class="text-white">Area</th>
                                                                            <th class="text-white">Correo</th>
                                                                            <th class="text-white">Carnet-Vence</th>
                                                                            <th class="text-white">RH</th>
                                                                            <th class="text-white">Estado-Carnet</th>
                                                                            <th class="text-white">Actualizar</th>
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


        <script src="../js/datosFuncionario.js"></script>
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
                $('#filtroFuncionarios').on('input', function () {
                    var valorFiltro = $(this).val().toLowerCase(); // Obtener el valor del campo de búsqueda y convertirlo a minúsculas

                    // Iterar sobre cada fila de la tabla
                    $('#tablaFuncionarios tbody tr').filter(function () {
                        $(this).toggle($(this).text().toLowerCase().indexOf(valorFiltro) > -1); // Mostrar u ocultar la fila según el filtro
                    });
                });
            });
</script>