<%@page import="entidades.Usuarios"%>
<%@page import="entidades.Estudiantes"%>

<%
    HttpSession sesion = request.getSession();
    Usuarios usuario = (Usuarios) sesion.getAttribute("estudiante");

    // Verificar si la sesión está activa y si el estudiante está en sesión
    if (sesion != null && usuario != null) {

    } else {
        // Si la sesión no está activa o el estudiante no está en sesión, redirigir al inicio
        response.sendRedirect("../index.jsp");
    }
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies
%>
<!-- Cambio crystian  -->
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Usuario</title>
        <link rel="stylesheet" href="../css/Menu.css"/>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
         <%--letras --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&family=Carlito:wght@400;700&display=swap" rel="stylesheet">


        <style>
            .fondo2 {
                width: 100%;
                height: 100vh;
                display: inline-block;
                display: flex;
                background-image: url("../img/fondo-sesion.jpg");
                background-repeat: no-repeat;
                background-size: cover;
            }
        </style>

    </head>
    <body> 

        <div class="fondo2">
            <a href="cerrarSesionUsuario.jsp">
                <button type="button" class="btn m-2 text-white" style="background-color: #579B34;">
                    <b>Salir</b>
                </button>
            </a>
            <div class="container d-flex justify-content-center align-items-center">
                <div class="row justify-content-center">
                    <div class="col-lg-8">
                        <div class="bg-white shadow rounded bg-light bg-opacity-75 p-4">
                            <div class="row">
                                <div class="col-md-12 d-flex flex-column align-items-center">

                                    <h1 class="letra text-center ">
                                        Bienvenido:<%if (usuario != null) {
                                                out.print(usuario.getNombres());
                                            }%>
                                    </h1>
                                    <h5>En esta página, encontrarás la opción de descargar tu carnet en formato PDF con solo pulsar un botón. </h5>
                                    <div class="col-12 d-flex flex-column align-items-center pt-3">
                                        <div class="card" data-aos="flip-left"
                                             data-aos-easing="ease-out-cubic"
                                             data-aos-duration="2000">
                                            <div>
                                                <img src="../img/menu_carnet.webp" alt="alt" width="100%" height="180px" />
                                                <h3 class="mt-3 mb-3 text-center">Carnet</h3>
                                            </div>
                                            <div class="text-center">
                                                <button type="button" class="btn text-white" onclick="verReporte()" data-cedula="${not empty estudiante ? estudiante.cedula : ''}" style="background-color: #579B34;">
                                                    <b>Descargar</b>
                                                </button>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../Componentes/footer.jsp" ></jsp:include>

            <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
            <script>AOS.init();</script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
            crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        </body>
    </html>

    <script>
                                                    // Script para descargar el carnet
                                                    function verReporte() {
                                                        var cedula = document.querySelector("[data-cedula]").getAttribute("data-cedula");
                                                        console.log("Valor de cedula:", cedula); // Debugging
                                                        if (cedula !== '') {
                                                            window.open("<%= request.getContextPath()%>/reportes/reporteCarnetCompleto.jsp?cedula=" + cedula, "_blank");
                                                        } else {
                                                            // Manejar el caso donde el estudiante no está en sesión
                                                            alert("No se puede generar el carnet. Por favor, inicie sesión.");
                                                        }
                                                    }
</script>



