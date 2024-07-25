<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entidades.InformacionCarnet"%>
<%@page import="controladores.InformacionCarnetJpaController"%>
<%@page import="entidades.Formacion"%>
<%@page import="java.util.List"%>
<%@page import="controladores.FormacionJpaController"%>
<%@page import="entidades.Usuarios"%>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title> Carnet Eliminado</title>
        <link rel="stylesheet" href="../css/Menu.css"/>
        <link rel="stylesheet" href="../css/tabla.css"/>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="../css/app.css" rel="stylesheet">
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
                                        <div class="col-lg-10 col-md-10 col-sd-12"><h1 class="letra">Carnet Eliminados</h1></div>
                                        <div class="col-lg-2 col-md-2 col-sd-12"><img class="float-end" src="../img/inicioSesion_sena.jpg" width="70px" height="70px" alt="alt"/></div>
                                    </div>
                                </div>
                                <div class="container">
                                    <div class="row">
                                        <div class="col-md-12 col-sd-12">
                                            <form action="<%=request.getContextPath()%>" method="post" class="pt-1">
                                                <div class="input-group mb-2">
                                                    <div class="input-group-text col-4"><b>Buscar:</b></div>
                                                    <input type="text" class="form-control" id="filtro1">
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>

                                <section class="intro mb-2">
                                    <div class="bg-image" >
                                        <div class="mask d-flex align-items-center pt-1">
                                            <div class="container">
                                                <div class="row justify-content-center" data-aos="zoom-in"  data-aos-duration="500">
                                                    <div class="col-12"> 
                                                        <div class="card-body p-0 ">
                                                            <%--TABLA INICIO --%>
                                                            <div class="table-responsive table-scroll table-sm" data-mdb-perfect-scrollbar="true" style="position: relative; height: 310px">
                                                                <table id="tablaCarnet" class="table table-striped table-sm  mb-0 text-center ">
                                                                    <thead class="" style="background-color: #018E42;">
                                                                        <tr class="text-light">
                                                                            <th class="text-white">Cedula</th>
                                                                            <th class="text-white">Formacion</th>
                                                                            <th class="text-white">Nombres</th>
                                                                            <th class="text-white">Apellidos</th>
                                                                            <th class="text-white">Fecha de Eliminacion</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <%
                                                                            InformacionCarnetJpaController controlador = new InformacionCarnetJpaController();
                                                                            List<InformacionCarnet> info = controlador.findInformacionCarnetEntities();

                                                                            if (info.isEmpty()) {


                                                                        %>

                                                                        <tr>
                                                                            <td colspan="5" class="text-center">No se encontraron carnet eliminados en la base de datos.</td>
                                                                        </tr>
                                                                        <%                                                                } else {

                                                                            for (InformacionCarnet adm : info) {
                                                                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                                                String fechaeEliminado = adm.getFechaEliminacion() != null ? dateFormat.format(adm.getFechaEliminacion()) : "Sin fecha";

                                                                        %>
                                                                        <tr>
                                                                            <td> <%= adm.getCedula()%> </td>
                                                                            <td> <%= adm.getFormacion()%></td>
                                                                            <td> <%= adm.getNombres()%> </td>
                                                                            <td> <%= adm.getApellidos()%> </td>
                                                                            <td> <%= fechaeEliminado%> </td>

                                                                            <%
                                                                                    }

                                                                                }
                                                                            %>
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


        <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
        <script>AOS.init();</script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    </body>
</html>
<script>
            const filtroInput = document.getElementById("filtro1");
            const filas = document.querySelectorAll("#tablaCarnet tbody tr");
            filtroInput.addEventListener("input", function () {
                const filtro = filtroInput.value.trim().toLowerCase();
                filas.forEach(function (fila) {
                    const textoFila = fila.textContent.toLowerCase();
                    if (textoFila.includes(filtro)) {
                        fila.style.display = "";
                    } else {
                        fila.style.display = "none";
                    }
                });
            });</script>
