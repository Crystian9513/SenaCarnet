<%@page import="entidades.Estudiantes"%>
<%@page import="controladores.EstudiantesJpaController"%>
<%@page import="entidades.Usuarios"%>
<%@page import="controladores.UsuariosJpaController"%>
<%
    HttpSession sesion = request.getSession();

    Usuarios usuarioEstudiante = (Usuarios) sesion.getAttribute("estudiante");
    Usuarios usuarioFuncionario = (Usuarios) sesion.getAttribute("funcionario");
    Usuarios usuario = (usuarioEstudiante != null) ? usuarioEstudiante : usuarioFuncionario;

    // Verificar si la sesi�n est� activa y si el usuario (estudiante o funcionario) est� en sesi�n
    if (sesion != null && usuario != null) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setHeader("Expires", "0"); // Proxies
    } else {
        // Si la sesi�n no est� activa o el usuario no est� en sesi�n, redirigir al inicio
        response.sendRedirect("../index.jsp");
    }
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cambio de contrase�a</title>
        <link rel="stylesheet" href="../css/estilo.css"/>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
         <%--letras --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&family=Carlito:wght@400;700&display=swap" rel="stylesheet">


    </head>
    <body>
        <div class="login-page ">
            <div class="container  ">
                <div class="row ">
                    <div class="col-lg-10 offset-lg-1 ">
                        <div class="bg-white shadow rounded bg-light bg-opacity-75">
                            <div class="row">
                                <div class="col-md-6 pe-0">
                                    <h1 class="pt-5 text-center">Nueva Contrase�a</h1>
                                    <div class="form-left h-100 py-2 px-5">
                                        <form action="<%=request.getContextPath()%>/CambioContrasenaPrimeraVezServlet" class="row g-4">
                                            <div class="col-12">
                                                <div class="input-group">
                                                    <!-- Aqu� se establece el valor por defecto obtenido de la sesi�n -->
                                                    <input type="hidden" id="cedula" name="cedula" class="form-control"
                                                           placeholder="Numero de Cedula" required min="1" maxlength="25"
                                                           value="<%= usuario != null ? usuario.getCedula() : ""%>">
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <label><strong>Contrase�a Nueva</strong><span class="text-danger">*</span></label>
                                                <div class="input-group">
                                                    <div class="input-group-text"><i class="bi bi-lock-fill"></i></div>
                                                    <input type="password" id="clave1" name="clave1" class="form-control"
                                                           placeholder="Contrase�a Nueva" required min="1" maxlength="25">
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <label><strong>Confirme su Contrase�a</strong><span class="text-danger">*</span></label>
                                                <div class="input-group">
                                                    <div class="input-group-text"><i class="bi bi-lock-fill"></i></div>
                                                    <input type="password" id="clave2" name="clave2" class="form-control"
                                                           placeholder="Confirme su Contrase�a" required min="1" maxlength="25">
                                                </div>
                                            </div>
                                            <div class="col-12 d-flex justify-content-center align-items-center">
                                                <button type="submit" name="action" value="Guardar" class="btn mx-auto mt-2 text-white" style="background-color: #018E42;">
                                                    <strong>Guardar</strong>
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <img data-aos="flip-left"
                                         data-aos-easing="ease-out-cubic"
                                         data-aos-duration="2000" src="../img/sesion.jpg" class="img-fluid" />
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>                                       
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </body>
</html>



<%
    String mensaje2 = request.getParameter("respuesta");

    if (mensaje2
            != null) {
        switch (mensaje2) {
            case "noCoinciden":
%>
<script>
            Swal.fire(
                    '�Oops!',
                    '�Las contrase�as tienen que ser iguales!',
                    'warning'
                    );
</script>
<%
        break;
    case "Guardada":
%>
<script>
    Swal.fire({
        title: 'Contrase�a actualizada',
        icon: 'success',
        timer: 2300,
        showConfirmButton: false
    }).then(function () {
        window.location.href = '../index.jsp';
    });
</script>
<%
                break;
            default:

        }
    }
%>

<script>
    // Funci�n para verificar si la alerta ya se ha mostrado
    function mostrarAlertaUnaVez() {
        // Verificar si ya se ha mostrado la alerta
        if (!document.cookie.includes("alertaMostrada")) {
            // Mostrar la alerta
            Swal.fire({
                title: 'Actualice su contrase�a',
                icon: 'warning',
                timer: 2000,
                showConfirmButton: false
            });

            // Establecer una cookie para indicar que la alerta ya se mostr�
            document.cookie = "alertaMostrada=true; expires=Fri, 31 Dec 9999 23:59:59 GMT; path=/";
        }
    }

    // Llamar a la funci�n al cargar la p�gina
    window.onload = mostrarAlertaUnaVez;
</script>
