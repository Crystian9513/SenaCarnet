<%@page import="entidades.Formacion"%>
<%@page import="java.util.List"%>
<%@page import="controladores.FormacionJpaController"%>
<%@page import="entidades.Usuarios"%>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Menu Principal</title>
        <link rel="stylesheet" href="../css/Menu.css"/>
        <link href="../css/app.css" rel="stylesheet">
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
        <script src="../js/app.js"></script>

        <%--letras --%>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Work+Sans:ital,wght@0,100..900;1,100..900&family=Carlito:wght@400;700&display=swap" rel="stylesheet">

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

        <script>
            function verReporte3() {
                // Obtener el valor seleccionado en el select
                var codigoFormacion = document.getElementById("idFormacion").value;

                // Redirigir al enlace con el ID seleccionado como parámetro
                window.open("<%= request.getContextPath()%>/reportes/reporteFormacion.jsp?idFormacion=" + codigoFormacion, "_blank");
            }
        </script>
    </head>
    <body  style="background-color: #fefafb;">

        <div class="wrapper">

            <jsp:include page="../Componentes/Sidebar.jsp" ></jsp:include>
                <div class="main">
                <jsp:include page="../Componentes/nav.jsp" ></jsp:include>
                    <main class="content">
                    <%--CONTENIDO INICIO --%>
                    <div class="container ">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-10 col-md-10 col-sd-12"> <h1 class="letra pb-3">Bienvenido:<%if (usuario != null) {
                                        out.print(usuario.getNombres() );
                                    }%> </h1></div>
                                <div class="col-lg-2 col-md-2 col-sd-12"><img class="float-end" src="../img/inicioSesion_sena.jpg" width="70px" height="70px" alt="alt"/></div>
                            </div>
                        </div>
                        <div class="row text-center">
                            <div class="col-lg-3 col-sd-12 d-flex align-items-center justify-content-center ">
                                <div class="card mb-5" data-aos="flip-left"
                                     data-aos-easing="ease-out-cubic"
                                     data-aos-duration="2000">
                                    <div class="card-details">
                                        <div><img src="../img/menu_estudiantes"  width="210px" height="200px" alt="alt"/></div>
                                        <h3>Aprendiz</h3>
                                    </div>
                                    <a href="estudiantes.jsp"><button class="card-button" >ENTRAR</button></a>
                                </div>
                            </div>
                            <div class="col-lg-3 col-sd-12 d-flex align-items-center justify-content-center ">
                                <div class="card mb-5" data-aos="flip-left"
                                     data-aos-easing="ease-out-cubic"
                                     data-aos-duration="2000">
                                    <div class="card-details">
                                        <div><img src="../img/menu_administrador.jpg" width="210px" height="200px" alt="alt"/></div>
                                        <h3>Administrador</h3>
                                    </div>
                                    <a href="administrador.jsp"> <button class="card-button">ENTRAR</button></a>
                                </div>
                            </div>
                            <div class="col-lg-3  col-sd-12 d-flex align-items-center justify-content-center">
                                <div class="card mb-5" data-aos="flip-left"
                                     data-aos-easing="ease-out-cubic"
                                     data-aos-duration="2000">
                                    <div class="card-details">
                                        <div><img src="../img/menu-coordinador.jpg" width="210px" height="200px" alt="alt"/></div>
                                        <h3>Coordinador</h3>
                                    </div>
                                    <a href="coordinadorDatos.jsp"><button class="card-button">ENTRAR</button></a>
                                </div>
                            </div>
                            <div class="col-lg-3  col-sd-12 d-flex align-items-center justify-content-center">
                                <div class="card mb-5" data-aos="flip-left"
                                     data-aos-easing="ease-out-cubic"
                                     data-aos-duration="2000">
                                    <div class="card-details">
                                        <div><img src="../img/menu_sedes.jpeg" width="210px" height="200px" alt="alt"/></div>
                                        <h3>Sede-Formacion</h3>
                                    </div>
                                    <a href="sedesFormaciones.jsp"><button class="card-button">ENTRAR</button></a>
                                </div>
                            </div>
                        </div>  
                    </div>
                    <%--CONTENIDO FINAL --%>
                    <h2 class="letra text-center">Reporte de Carnet y Cambio de Logo</h2>
                    <div class="container mb-5">  
                        <div class="row">
                            <div class="col-lg-6 col-sd-12 ">
                                <h4 class="text-center">Bienvenido..!!</h4> <p>Aquí puedes buscar y visualizar los reportes de los carnets generados para las diferentes formaciones. Utiliza el menú desplegable para seleccionar la formación de tu interés y ver los carnets asociados a esa formación.
                                    Una vez seleccionada la formación, podrás descargar los carnets necesarios para su uso. 
                                </p>
                                <div class="card" data-aos="flip-left"
                                     data-aos-easing="ease-out-cubic"
                                     data-aos-duration="2000">
                                    <div class="card-details">
                                        <div><img src="../img/menu_carnet.webp" width="300px" height="150px" alt="alt"/></div>
                                        <h3 class="text-center">Carnet por Formacion</h3>
                                    </div>
                                    <div class="col-12">
                                        <div class="input-group mb-3 ">
                                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                                            <select name="idFormacion" id="idFormacion"
                                                    class="from-selec col-7"  >

                                                < <%
                                                    FormacionJpaController controF = new FormacionJpaController();
                                                    List listaC = controF.findFormacionEntities();

                                                    for (int i = 0; i < listaC.size(); i++) {
                                                        Formacion form = (Formacion) listaC.get(i);
                                                        out.print("<option value='" + form.getIdFormacion() + "'>");
                                                        out.print(form.getNombre());
                                                        out.print("</option>");
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <a href="" onclick="verReporte3()"><button class="card-button">ENTRAR</button></a>

                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-sd-12 ">
                                <h4 class="text-center">Actualizar Datos del Logo</h4>
                                <p>Para acceder al formulario de actualización, por favor haz clic en el botón </p> <b>"Formulario"</b>.
                                <p>En este formulario, puedes actualizar la imagen del logo, la imagen de la firma y el nombre del director. Por favor, selecciona los archivos de imagen deseados y proporciona el nuevo nombre del director.</p> 
                                <strong>Instrucciones:</strong>
                                <ul>
                                    <li>Imagen del Logo: Selecciona la nueva imagen del logo.</li>
                                    <li>Imagen de la Firma: Selecciona la nueva imagen de la firma.</li>
                                    <li>Nombre del Director: Ingresa el nuevo nombre del director en el campo correspondiente.</li>
                                </ul>
                                <p>
                                    Una vez completado, haz clic en el botón "Actualizar" para guardar los cambios.
                                </p>
                                <div class="input-group mb-2">
                                    <div class="input-group-text col-md-7 col-sd-12"><b>Actualizar Datos del Logo</b></div>
                                    <button id="editarBtnFormaciones" type="button" class="btn text-white" style="background-color: #018E42;" data-bs-toggle="modal" data-bs-target="#formulario3Modallogo"><b>Formulario</b></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
                <jsp:include page="../Componentes/footer2.jsp" ></jsp:include>

                </div>
            </div>

        <jsp:include page="../Componentes/modales.jsp" ></jsp:include>


            <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
            <script>AOS.init();</script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
            crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        </body>
    </html>

<%
    String mensaje = request.getParameter("respuesta");

    if (mensaje != null) {

        switch (mensaje) {
            case "guardado":
%>
<script>
                                            Swal.fire(
                                                    '¡Exito!',
                                                    '¡Guardado!',
                                                    'success'
                                                    );
</script>
<%
        break;
    case "Erroraleditar":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡no se encuentra el registro!',
            'warning'
            );
</script>
<%break;
            default:

        }
    }

%>