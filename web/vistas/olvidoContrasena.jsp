<%@page import="entidades.Usuarios"%>
<%@page import="controladores.UsuariosJpaController"%>
<%@page import="java.util.UUID"%>
<!DOCTYPE html>
<%
    HttpSession session2 = request.getSession(false); // No crea una nueva sesión si no existe
    String token = (String) session2.getAttribute("token");

    // Verificar si la sesión está activa y si el token está presente
    if (token == null) { // Cambiado || por && para redirigir si no hay sesión o no hay token
         response.sendRedirect("index.jsp");
    } else {
        // El token está presente y la sesión está activa, puedes continuar con el flujo normal
        System.out.println("Sesión activa. Token: " + token);
    }
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
                                <form action="<%=request.getContextPath()%>/olvidoContraseñaServlet" class="row g-4">
                                    <input type="hidden" name="token" value="<%= request.getParameter("token") %>">
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
                                        <button type="submit" name="action" value="Guardar" class="btn mx-auto mt-2" style="background-color: #6acd56;">
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
    // Mensajes de depuración
    System.out.println("Token recibido en la página: " + request.getParameter("token"));
%>