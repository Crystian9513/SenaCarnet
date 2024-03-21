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
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

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

        <%--MENU INICIO --%>
        <nav class="navbar text-l navbar-expand-lg " style="background-color: #6acd56;">
            <div class="container">
                <div class="row">
                    <div class="col-md-2">
                        
                            <img class="" src="../img/inicioSesion_sena.jpg" alt="" height="80px" width="80px">
                       
                    </div>

                    <div class="col-md-2 text-center">
                        <h2 class="mt-3 letras"> SENA </h2>
                    </div>
                    <div class="col-md-8">
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false"
                                aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse mt-2" id="navbarNavDropdown">
                            <ul class="navbar-nav ms-auto navbar-brand">

                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        Aprendiz
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="estudiantes.jsp">Ingresar</a></li>
                                        <li><a class="dropdown-item" href="#">Carnet Eliminado</a></li>
                                    </ul>
                                </li>
                                <li class="nav-item ">
                                    <a class="nav-link" href="administrador.jsp">Administrador</a>
                                </li>
                                <li class="nav-item ">
                                    <a class="nav-link" href="coordinadorDatos.jsp">Coordinador</a>
                                </li>
                                <li class="nav-item ">
                                    <a class="nav-link" href="sedesFormaciones.jsp">Sede-Formacion</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Menu Principal</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="cerrarSesionAdministrador.jsp">Salir</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
        <%--MENU FINAL --%>

        <%--CONTENIDO INICIO --%>
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <h1 class="letra text-center pt-3 ">Carnet Eliminados</h1>
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
                                                <div class="table-responsive table-scroll table-sm" data-mdb-perfect-scrollbar="true" style="position: relative; height: 210px">
                                                    <table id="tablaCarnet" class="table table-striped table-sm  mb-0 text-center ">
                                                        <thead class="" style="background-color: #263642;">
                                                            <tr class="text-light">
                                                                <th scope="col">Cedula</th>
                                                                 <th scope="col">Formacion</th>
                                                                <th scope="col">Nombres</th>
                                                                <th scope="col">Apellidos</th>
                                                                <th scope="col">Fecha de Eliminacion</th>
                                                               

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
                                                                <td> <%= adm.getFormacion() %></td>
                                                                <td> <%= adm.getNombres() %> </td>
                                                                <td> <%= adm.getApellidos() %> </td>
                                                                <td> <%= fechaeEliminado %> </td>
                                                                
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

        <footer class="py-3 mt-2 text-center" style="background-color: #6acd56;">
            <div class="row">
                <div class="col-lg-4 col-md-6 col-sd-6 pt-3">
                    <img src="../img/icon_facebook.png" alt="alt"/>
                    <a  href="http://www.facebook.com">Facebook</a><br>
                    <img src="../img/icon_instagram.png" alt="alt"/>
                    <a href="http://www.instagram.com">Instagram</a><br>
                    <img src="../img/icon_github.png" alt="alt"/>
                    <a href="https://github.com/Crystian9513">Github</a>
                </div>
                <div class="col-lg-8 col-md-6 col-sd-6">

                    <h5 class="pt-2">Copyright <%= java.time.LocalDate.now().getYear()%>
                        Crystian Jesus Peralta Arias y Sebastian Navaja. <br>
                        Desarrollador Web.
                    </h5>
                    <h6>Telefono: +57 300 7836674 </h6>
                    <h6>Correo: crystian_9513@hotmail.com</h6>


                </div>
            </div>
        </footer>

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
