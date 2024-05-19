<nav id="sidebar" class="sidebar js-sidebar ">
    <div class="sidebar-content js-simplebar ">
        <a class="sidebar-brand mt-2" href="#">
            <span class="align-middle ">Gestion De Aprendices</span>
        </a>
        <ul class="sidebar-nav">
            <li class="sidebar-header">
                Menu
            </li>
            <li class="sidebar-item active">
                <a class="sidebar-link" href="../vistas/menuPrincipal.jsp">
                    <i class="align-middle" data-feather="sliders"></i> <span class="align-middle">Menu Principal</span>
                </a>
            </li>
            <li class="sidebar-header">
                Gestion de Informacion
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../vistas/sedesFormaciones.jsp" target="_blank">
                    <i class="align-middle" data-feather="home" ></i> <span class="align-middle">Sede y Formacion</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../vistas/coordinadorDatos.jsp" target="_blank">
                    <i class="align-middle" data-feather="user"></i> <span class="align-middle">Coordinador</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../vistas/administrador.jsp" target="_blank">
                    <i class="align-middle" data-feather="user-plus"></i> <span class="align-middle">Administrador</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../vistas/estudiantes.jsp" target="_blank">
                    <i class="align-middle" data-feather="users"></i> <span class="align-middle">Aprendiz</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../vistas/areaRol.jsp" target="_blank">
                    <i class="align-middle" data-feather="book" ></i> <span class="align-middle">Area y Roles de Trabajo</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../vistas/funcionarios.jsp" target="_blank">
                    <i class="align-middle" data-feather="user" ></i> <span class="align-middle">Funcionario</span>
                </a>
            </li>
            <li class="sidebar-header">
                Funciones Especiales
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../vistas/carnetEliminado.jsp" target="_blank">
                    <i class="align-middle" data-feather="search"></i> <span class="align-middle">Carnet Eliminados</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" data-bs-toggle="modal" data-bs-target="#ModalRestablecimientoContrasena">
                    <i class="align-middle" data-feather="lock"></i> <span class="align-middle">Restablecer Contraseña</span>
                </a>
            </li>
            <li class="sidebar-item">
                <a class="sidebar-link" href="../vistas/cerrarSesionAdministrador.jsp">
                    <i class="align-middle" data-feather="log-in"></i> <span class="align-middle">Salir</span>
                </a>
            </li>
        </ul>
    </div>
</nav>

<div class="modal" id="ModalRestablecimientoContrasena" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioRestablecerContrasena" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Restablecimiento de Contraseña</h2>
                    <div class="col-12">
                        <div class="input-group ">
                            <div class="input-group-text col-5"><b>Numero de Cedula:</b></div>
                            <input type="number" class="form-control" name="CedulaRestablecer" required min="1" maxlength="2147483647">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3 "><!-- bottones -->
                        <button type="submit" class="btn botones text-white px-4" id="btnBusqueda" style="background-color: #018E42;"><b>Buscar</b></button>
                        <button type="submit" class="btn text-white" id="btnRestablecer" style="background-color: #018E42;"><b>Restablecer</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<script>
    $(document).ready(function () {
        // Manejador de evento para el botón de guardar en el formulario
        $('#btnBusqueda').click(function (event) {
            event.preventDefault();

            var formData = $('#FormularioRestablecerContrasena').serialize();
            formData += '&accion=buscar';

            enviarPeticion(formData, handleSuccessGuardar, handleError);
        });

        // Manejador de evento para el botón de actualizar en el modal de edición
        $('#btnRestablecer').click(function (event) {
            event.preventDefault();

            var formData = $('#FormularioRestablecerContrasena').serialize();
            formData += '&accion=restablecer';

            enviarPeticion(formData, handleSuccessActualizar, handleError);
        });

        // Función para enviar la petición AJAX común
        function enviarPeticion(formData, successCallback, errorCallback) {
            $.ajax({
                type: 'POST',
                url: '../RestablecimientoContrasenaServlet',
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

            } else {
                mostrarError(response.mensaje);
            }
        }

        // Función para manejar la respuesta exitosa de la solicitud de actualizar
        function handleSuccessActualizar(response) {
            if (response.estado === "exito") {
                mostrarExito(response.mensaje);
            } else {
                mostrarError(response.mensaje);
            }
        }

        $('#btnLimpiarModalAdministrador').click(function () {
            limpiarFormulario('FormularioAdministradores');
        });

        // Función para manejar el error de la solicitud AJAX
        function handleError(errorMessage) {
            mostrarError(errorMessage);
        }

    });

    // Función para mostrar una alerta de éxito y cerrar el modal
    function mostrarExito(mensaje) {
        const Toast = Swal.mixin({
            toast: true,
            position: "center",
            showConfirmButton: false,
            timer: 1500,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer);
                toast.addEventListener('mouseleave', Swal.resumeTimer);
            }
        });

        Toast.fire({
            icon: "success",
            title: mensaje
        });
    }

// Función para mostrar una alerta de error
    function mostrarError(mensaje) {
        const Toast = Swal.mixin({
            toast: true,
            position: "center",
            showConfirmButton: false,
            timer: 1500,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.stopTimer);
                toast.addEventListener('mouseleave', Swal.resumeTimer);
            }
        });

        Toast.fire({
            icon: "error",
            title: mensaje
        });
    }
</script>