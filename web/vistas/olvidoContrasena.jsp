<%@page import="java.time.ZoneId"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="entidades.Usuarios"%>
<%@page import="controladores.UsuariosJpaController"%>
<%@page import="java.util.UUID"%>
  <meta charset="UTF-8">
<%
// Obtener la URL base
String urlBase = request.getRequestURL().toString();

// Obtener la cadena de consulta (parámetros)
String queryString = request.getQueryString();

// Combinar la URL base y la cadena de consulta para obtener la URL completa
String urlCompleta = urlBase + (queryString != null ? "?" + queryString : "");

// Almacenar la URL completa en la sesión
HttpSession session1 = request.getSession();
session1.setAttribute("urlAnterior", urlCompleta);

// Recuperar el token de la URL
String tokenFromURL = request.getParameter("token");

// Recuperar la cédula del usuario de la URL
int cedula2 = 0; // Inicializamos la cédula a 0 por defecto
try {
    cedula2 = Integer.parseInt(request.getParameter("cedula"));
} catch (NumberFormatException e) {
    // Manejo de error si la cédula no se puede convertir a entero
    e.printStackTrace();
}

// Almacenar el token en la sesión
session1.setAttribute("sesionToken", tokenFromURL);

// Verificar si el token es nulo o si ha expirado
if (tokenFromURL == null) {
    // Si el token es nulo, redirigir al usuario a la página de inicio
    response.sendRedirect("../index.jsp");
} else {
    // Obtener la fecha de expiración del token en milisegundos desde la época
    long expirationMillis = Long.parseLong(request.getParameter("expiration"));

    // Obtener la fecha y hora actual
    LocalDateTime now = LocalDateTime.now();

    // Convertir la fecha y hora actual a milisegundos
    long nowMillis = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    
    System.out.println("className.methodName()" + nowMillis);

    // Verificar si el token ha expirado
    if (nowMillis > expirationMillis) {
        // Si el token ha expirado, redirigir al usuario a la página de inicio
        response.sendRedirect("../index.jsp");
    } else {
        // El token es válido, continuar con el proceso de cambio de contraseña
%>

<!-- Aquí va el resto de tu HTML -->

<%
    }
}
// Establecer encabezados para evitar el almacenamiento en caché de la página
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setHeader("Expires", "0"); // Proxies
%>


<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Olvido su contraseña</title>
        <link rel="stylesheet" href="../css/estilo.css"/>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
    </head>
    <body>
        <div class="login-page ">
            <div class="container">
                <div class="row ">
                    <div class="col-lg-10 offset-lg-1 ">
                        <div class="bg-white shadow rounded bg-light bg-opacity-75">
                            <div class="row">
                                <div class="col-md-6 pe-0">
                                    <h1 class=" pt-5 text-center">Cambio de Contraseña</h1>
                                    <div class="form-left h-100 py-4 px-5">
                                        <form action="" class="row g-4" method="post" >

                                            <input type="hidden" name="cedula2" value="<%= cedula2%>">
                                            <div class="col-12">
                                                <label><strong>Nueva Contraseña</strong><span class="text-danger">*</span></label>
                                                <div class="input-group">
                                                    <div class="input-group-text"><i class="bi bi-lock-fill"></i></div>
                                                    <input type="password" id="clave1" name="clave1" class="form-control"
                                                           placeholder="Nueva Contraseña" required min="1" maxlength="25">
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <label><strong>Confirmar Contraseña</strong><span class="text-danger">*</span></label>
                                                <div class="input-group">
                                                    <div class="input-group-text"><i class="bi bi-lock-fill"></i></div>
                                                    <input type="password" id="clave2" name="clave2" class="form-control"
                                                           placeholder="Confirmar Contraseña" required min="1" maxlength="25">
                                                </div>
                                            </div>
                                            <div class="col-12 d-flex justify-content-center align-items-center">
                                                <button type="submit"  name="Guardar" class="btn mx-auto mt-2" style="background-color: #6acd56;">
                                                    <strong>Guardar</strong></button>
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
    String boton = request.getParameter("Guardar");
    if (boton != null) {
        try {
            UsuariosJpaController controladorUsuario = new UsuariosJpaController();
            int numeroCedula = Integer.parseInt(request.getParameter("cedula2"));

            // Buscar al usuario por su número de cédula
            Usuarios usuario = controladorUsuario.findUsuarios(numeroCedula);

            if (usuario == null) {
                // Manejar el caso en que el usuario no sea encontrado
            } else {
                String clave1 = request.getParameter("clave1");
                String clave2 = request.getParameter("clave2");

                if (!clave1.equals(clave2)) {
%>        
<script>
            Swal.fire(
                    '¡Oops!',
                    'Las Contraseñas no coinciden',
                    'warning'
                    );
</script>             
<%
} else {
    String contraseñaEncriptada = controladorUsuario.EncryptarClave(clave1);

    usuario.setClaves(contraseñaEncriptada);
    usuario.setEstadoClave(2);

    controladorUsuario.edit(usuario);
%>
<script>
    Swal.fire(
            '¡Éxito!',
            'Contraseña Guardada',
            'success'
            ).then(() => {
        window.location.href = '..index.jsp'; // Redireccionar después de que el usuario cierre la alerta
    });
</script>
<%
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
