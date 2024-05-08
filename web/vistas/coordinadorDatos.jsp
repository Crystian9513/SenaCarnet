<%@page import="entidades.Coordinador"%>
<%@page import="controladores.CoordinadorJpaController"%>
<%@page import="entidades.Usuarios"%>
<%@page import="entidades.Usuarios"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Administrador"%>
<%@page import="controladores.AdministradorJpaController"%>
<!DOCTYPE html>
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
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Administrador</title>
        <link rel="stylesheet" href="../css/tabla.css"/>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href= "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" >
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>


        
    </head>
    <body  style="background-color: #fefafb;">

        <%--MENU INICIO --%>
        <jsp:include page="../Componentes/menu.jsp" ></jsp:include>
        <%--MENU FINAL --%>

        <%--CONTENIDO INICIO --%>
        <div class="container">
            <div class="row">
                <div class="col-12">

                    <h1 class="letra text-center pt-3 pb-3">Informacion de Coordinador</h1>

                    <div class="container">
                        <div class="row">
                            <div class="col-md-6 col-sd-12">
                                <form action="<%=request.getContextPath()%>" method="post" class="pt-2">
                                    <div class="input-group mb-2">
                                        <div class="input-group-text col-md-8 col-sd-12"><b>Nuevo coordinador</b></div>
                                        <button id="" type="button" class="btn" style="background-color: #6acd56;" data-bs-toggle="modal" data-bs-target="#formularioModalCoordinador"><b>Formulario</b></button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6 col-sd-12">
                                <div class="input-group mb-2">
                                    <div class="input-group-text col-4"><b>Buscar:</b></div>
                                    <input type="text" class="form-control" id="filtro1">
                                </div>
                            </div>
                        </div>
                    </div>
                    <section class="intro mb-5">
                        <div class="bg-image" >
                            <div class="mask d-flex align-items-center h-100">
                                <div class="container tableContenido">
                                    <div class="row justify-content-center" data-aos="zoom-in"  data-aos-duration="500">
                                        <div class="col-12 tableContenido"> 
                                            <div class="card-body p-0 ">
                                                <%--TABLA INICIO --%>
                                                <div class="table-responsive table-scroll table-sm" data-mdb-perfect-scrollbar="true" style="position: relative; height: 180px">
                                                    <table id="tablaAdministradores" class="table table-striped table-sm  mb-0 text-center ">
                                                        <thead class="" style="background-color: #263642;">
                                                            <tr class="text-light">
                                                                <th scope="col">Cedula</th>
                                                                <th scope="col">Nombres</th>
                                                                <th scope="col">Apellidos</th>
                                                                <th scope="col">Correo</th>
                                                                <th scope="col">Opcines</th>

                                                            </tr>
                                                        </thead>
                                                        <tbody>

                                                            <%
                                                                CoordinadorJpaController controlador = new CoordinadorJpaController();
                                                                List<Coordinador> coord = controlador.findCoordinadorEntities();

                                                                if (coord.isEmpty()) {


                                                            %>

                                                            <tr>
                                                                <td colspan="5" class="text-center">No se encontraron Administradores en la base de datos.</td>
                                                            </tr>
                                                            <%                                                                } else {

                                                                for (Coordinador adm : coord) {


                                                            %>
                                                            <tr>
                                                                <td> <%= adm.getCedula()%> </td>
                                                                <td> <%= adm.getNombre()%></td>
                                                                <td> <%= adm.getApellido()%> </td>
                                                                <td> <%= adm.getCorreo()%> </td>
                                                                <td> 
                                                                    <button type="button" class="btn btn-outline-danger btn-sm" data-bs-toggle="modal" 
                                                                            data-bs-target="#formularioModal2" onclick="obtenerDatosCoordinador('<%= adm.getCedula()%>',
                                                                                            '<%= adm.getNombre()%>',
                                                                                            '<%= adm.getApellido()%>',
                                                                                            '<%= adm.getCorreo()%>')" >
                                                                        Opciones
                                                                    </button>
                                                                </td>
                                                            </tr>
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

        <jsp:include page="../Componentes/footer.jsp" ></jsp:include>
        <jsp:include page="../Componentes/modales.jsp" ></jsp:include>

            <!-- MODALES DE COORDINADOR EDITADO INICIO -->
            <div class="modal fade" id="formularioModal2" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <form action="<%=request.getContextPath()%>/CoordinadorDatosServlet" method="post" class="row g-2 " >
                            <h2 class="pt-5 pb-4 text-center">Coordinador</h2>

                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Cedula:</b></div>

                                    <input type="number" class="form-control" id="cedula2" name="cedula2" required min="1" max="2147483647" readonly>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Nombres:</b></div>

                                    <input type="text" class="form-control" id="nombre2" name="nombre2" required min="1">
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="input-group">
                                    <div class="input-group-text col-5"><b>Apellidos:</b></div>

                                    <input type="text" class="form-control" id="apellido2" name="apellido2" required min="1">
                                </div>
                            </div>  
                            <div class="col-12">
                                <div class="input-group input-group">
                                    <div class="input-group-text col-5"><b>Correo: </b></div>
                                    <input type="email" class="form-control" id="correo2" name="correo2" required min="1" maxlength="45">
                                </div>
                            </div>
                            <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                                <button type="submit" class="btn botones  px-4"
                                        name="action" value="Editar" style="background-color: #6acd56;"><b>Actualizar</b></button>
                                <button type="submit" class="btn " name="action" value="Eliminar" style="background-color: #6acd56;"><b>Eliminar</b></button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODALES DE COORDINADOR EDITADO FINAL -->
        
        <script src="../js/coordinadorDatos.js"></script>
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
            case "existe":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡La cedula ya existe en la base de datos!',
            'warning'
            );
</script>
<%
        break;
    case "guardado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El coordinador ha sido guardado!',
            'success'
            );
</script>
<%
        break;
    case "errorAlguardar":
%>
<script>
    Swal.fire(
            '¡Oops!',
            '¡Error al guardar!',
            'warning'
            );
</script>
<%
        break;
    case "eliminado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El coordinador ha sido eliminado!',
            'success'
            );
</script>
<%
        break;
    case "editado":
%>
<script>
    Swal.fire(
            '¡Éxito!',
            '¡El coordinador ha sido actualizado!',
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
            '¡Error al Actualizar!',
            'warning'
            );
</script>
<%break;
            default:
                throw new AssertionError();
        }
    }

%>

