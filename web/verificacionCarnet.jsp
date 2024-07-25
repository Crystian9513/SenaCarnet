<%@page import="entidades.Funcionarios"%>
<%@page import="controladores.FuncionariosJpaController"%>
<%@page import="entidades.EstadoCarnet"%>
<%@page import="entidades.Estudiantes"%>
<%@page import="controladores.EstudiantesJpaController"%>
<%@page import="entidades.Usuarios"%>
<%@page import="controladores.UsuariosJpaController"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Verificacion de carnet</title>
        <link rel="stylesheet" href="css/estilo.css"/>
        <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.2/font/bootstrap-icons.min.css">

    </head>
    <body>
        <div class="login-page">
            <div class="container  d-flex justify-content-center align-items-center">
                <div class="row ">
                    <div class="col-lg-10 offset-lg-1 ">
                        <div class="bg-white shadow rounded bg-light bg-opacity-75">
                            <div class="row">
                                <div class="col-md-6 pe-0 ">
                                    <h1 class="letra pt-5 text-center">Verificar Carnet</h1>
                                    <div class="form-left h-100 px-5">
                                        <form action="" class="row g-4">
                                            <div class="col-12 mt-5">
                                                <label class="pb-2"><strong>Codigo-Unico</strong><span class="text-danger">*</span></label>
                                                <div class="input-group">
                                                    <div class="input-group-text"><i class="bi bi-person-fill"></i></div>
                                                    <input type="text" id="codigo" name="codigo" class="form-control"
                                                           placeholder="Codigo-Unico" required min="1" >
                                                </div>
                                            </div>
                                            <div class="col-12 d-flex justify-content-center align-items-center">
                                                <button type="submit" name="verificar" class="btn mx-auto mt-2 text-white" style="background-color: #579B34;"
                                                        >
                                                    <strong>Verificar</strong></button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <a  href="index.jsp"><img data-aos="flip-left"
                                                              data-aos-easing="ease-out-cubic"
                                                              data-aos-duration="2000" src="img/sesion.jpg" class="img-fluid" /></a>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        
          <footer class="footer justify-content-end" style="background-color: #579B34; ">
            <div class="container-fluid">
                <!--Row Principal-->
                <div class="row">
                    <div class="col-12 text-center mt-3">
                        <p class="text-small text-white">
                            Servicio Nacional de Aprendizaje SENA - Regional Sucre - Sede la Gallera 
                            L�nea gratuita de atenci�n al Egresado Regional Sucre Sincelejo 3...... <br>
                            Horario de atenci�n: lunes a viernes de 8:00 am a 5:30 pm 
                            Correo electr�nico: para solicitudes: Correo@sena.edu.co<br>
                            &copy;  <a class="text-white" href="https://www.linkedin.com/in/crystian-jesus-peralta-arias-495632213/"
                                       target="_blank"><strong>Crystian Peralta</strong></a> - <a class="text-white"
                                       href="https://www.linkedin.com/in/sebastian-hurtado-72a564267/" target="_blank"><strong> Sebastian Navaja</strong></a> 
                            - <a class="text-white"
                                 href="" target="_blank"><strong> Steven Pajaro
                                </strong></a> 
                        </p>    
                    </div>
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

<%
    String boton = request.getParameter("verificar");

    if (boton != null) {
        String codigoUnico = request.getParameter("codigo");

        // Verificar en la tabla Estudiantes
        EstudiantesJpaController estudianteController = new EstudiantesJpaController();
        Object[] resultadoEstudiante = estudianteController.findEstudianteYEstadoCarnetPorIdentificadorUnico(codigoUnico);

        if (resultadoEstudiante != null) {
            EstadoCarnet estadoCarnet = (EstadoCarnet) resultadoEstudiante[1];
            if (estadoCarnet.getIdestadoCarnet() == 31) { // Verifica si el estado es "ELIMINADO"
                response.sendRedirect("verificacionCarnet.jsp?respuesta=carnetEliminado");
            } else {
                response.sendRedirect("verificacionCarnet.jsp?respuesta=carnetActivo");
            }
        } else {
            // Verificar en la tabla Funcionarios
            FuncionariosJpaController funcionarioController = new FuncionariosJpaController();
            Object[] resultadoFuncionario = funcionarioController.findFuncionarioYEstadoCarnetPorIdentificadorUnico(codigoUnico);

            if (resultadoFuncionario != null) {
                EstadoCarnet estadoCarnet = (EstadoCarnet) resultadoFuncionario[1];
                if (estadoCarnet.getIdestadoCarnet() == 31) { // Verifica si el estado es "ELIMINADO"
                    response.sendRedirect("verificacionCarnet.jsp?respuesta=carnetEliminado");
                } else {
                    response.sendRedirect("verificacionCarnet.jsp?respuesta=carnetActivo");
                }
            } else {
                response.sendRedirect("verificacionCarnet.jsp?respuesta=codigoInvalido");
            }
        }
    }
%>


<%
    String mensaje2 = request.getParameter("respuesta");

    if (mensaje2
            != null) {
        switch (mensaje2) {
            case "codigoInvalido":
%>
<script>
            Swal.fire("El codigo no existe en la base de datos!");
</script>
<%
        break;
    case "carnetEliminado":
%>
<script>
    Swal.fire("El carnet a sido Eliminado!");
</script>
<%
        break;
    case "carnetActivo":
%>
<script>
    Swal.fire("El carnet esta Activo!");
</script>
<%
                break;
            default:

        }
    }
%>