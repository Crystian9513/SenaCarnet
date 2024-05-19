<%@page import="entidades.AreaTrabajo"%>
<%@page import="controladores.AreaTrabajoJpaController"%>
<%@page import="entidades.RolFuncionario"%>
<%@page import="controladores.RolFuncionarioJpaController"%>
<%@page import="entidades.EstadoCarnet"%>
<%@page import="controladores.EstadoCarnetJpaController"%>
<%@page import="entidades.Formacion"%>
<%@page import="controladores.FormacionJpaController"%>
<%@page import="entidades.Tipodocumento"%>
<%@page import="controladores.TipodocumentoJpaController"%>
<%@page import="entidades.Sede"%>
<%@page import="java.util.List"%>
<%@page import="controladores.SedeJpaController"%>
<!-- MODALES DE SEDES GUARDAR INICIO -->
<div class="modal fade" id="ModalSedes" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioSede" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Registrar Sede</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoGdSe" name="codigoSe" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreGdSe" name="nombreSe" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarSede" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-danger" id="btnLimpiarModalSedes" onclick="limpiarModal()">Limpiar</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE SEDES GUARDAR FINAL -->

<!-- MODALES DE FORMACIONES GUARDAR INICIO -->
<div class="modal fade" id="ModalFormaciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioFormaciones" class="row g-2 "
                      onsubmit="return validarCamposVacios2();">
                    <h2 class="pt-3 pb-2 text-center">Registrar Formacion</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoForGd" name="codigoGd" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreForGd" name="nombreGd" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Sedes</b></div>
                            <select name="SedesListaGd" id="SedesListaForGd"
                                    class="from-selec col-7"  required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    SedeJpaController se = new SedeJpaController();
                                    List lista = se.findSedeEntities();

                                    for (int i = 0; i < lista.size(); i++) {
                                        Sede de = (Sede) lista.get(i);
                                        out.print("<option value='" + de.getIdSede() + "'>");
                                        out.print(de.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!-- bottones -->
                        <button type="submit" class="btn botones px-4 text-white" id="btnGuardadoFormacion" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-danger" id="btnLimpiarModalFormaciones" onclick="limpiarFormulario()">Limpiar</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE FORMACIONES GUARDAR FINAL -->

<!-- MODALES DE ADMINISTRADOR GUARDAR INICIO -->
<div class="modal fade" id="ModalAdministradores" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioAdministradores" class="row g-2 ">
                    <h2 class="pt-3 pb-2 text-center">Registrar Administrador</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedulaAdmGd" name="cedulaGd" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombres:</b></div>
                            <input type="text" class="form-control" id="nombreAdmGd" name="nombreGd" required min="1" maxlength="40">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidoAdmGd" name="apellidoGd" required min="1" maxlength="40">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Clave:</b></div>
                            <input type="text" class="form-control" id="claveAdmGd" name="claveGd" required min="1" maxlength="20">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group">
                            <div class="input-group-text col-5"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correoAdmGd" name="correoGd" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!-- bottones -->
                        <button type="submit" class="btn botones px-4 text-white" id="btnGuardarAdm" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-danger" id="btnLimpiarModalAdministrador" onclick="limpiarFormulario()">Limpiar</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE ADMINISTRADOR GUARDAR FINAL -->

<!-- MODALES DE COORDINADOR GUARDAR INICIO -->
<div class="modal fade" id="ModalCoordinador" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="formularioCoordinador" class="row g-2 ">
                    <h2 class="pt-3 pb-2 text-center">Registrar Coordinador</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedulaCor" name="cedulaCorGd" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombres:</b></div>
                            <input type="text" class="form-control" id="nombreCor" name="nombreCorGd" required min="1">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidoCor" name="apellidoCorGd" required min="1">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Clave:</b></div>
                            <input type="text" class="form-control" id="claveCor" name="claveCorGd" required min="1" maxlength="20">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group">
                            <div class="input-group-text col-5"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correoCor" name="correoCorGd" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!-- bottones -->
                        <button type="submit" class="btn botones px-4 text-white" id="btnGuardarCd"
                                style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-danger" id="btnLimpiarModalCoordinador" onclick="limpiarFormulario()">Limpiar</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE COORDINADOR GUARDAR FINAL -->

<!-- MODAL ESTUDIANTES GUARDAR INICIO-->
<div class="modal fade" id="ModalEstudiantes" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">

            <div class="modal-body">
                <form id="FormularioEstudiantes" class="row g-2 ">

                    <h2 class="pt-3 pb-2 text-center">Registrar Aprendiz</h2>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedula" name="cedula" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                            <select name="tipoDocumento" id="tipoDocumento"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    TipodocumentoJpaController se2 = new TipodocumentoJpaController();
                                    List lista2 = se2.findTipodocumentoEntities();

                                    for (int i = 0; i < lista2.size(); i++) {
                                        Tipodocumento tipo = (Tipodocumento) lista2.get(i);
                                        out.print("<option value='" + tipo.getIdTipoDocumento() + "'>");
                                        out.print(tipo.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Nombres: </b></div>
                            <input type="text" class="form-control" id="nombres" name="nombres" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">

                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidos" name="apellidos" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Formacion:</b></div>
                            <select name="formacion" id="formacion"
                                    class="from-selec col-6 " required >
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
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
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Sede:</b></div>
                            <select name="sede" id="sede"
                                    class="from-selec col-6" required min="1">
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    SedeJpaController ses = new SedeJpaController();
                                    List lista3 = ses.findSedeEntities();

                                    for (int i = 0; i < lista3.size(); i++) {
                                        Sede de = (Sede) lista3.get(i);
                                        out.print("<option value='" + de.getIdSede() + "'>");
                                        out.print(de.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correo" name="correo" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fotografia:</b></div>
                            <input type="file" class="form-control" id="foto" name="foto" accept="image/*" required>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fecha de Vencimiento del carnet:</b></div>
                            <input type="date" class="form-control" id="vence" name="vence" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>RH:</b></div>
                            <input type="text" class="form-control" id="rh" name="rh" required>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Estado del Carnet:</b></div>
                            <select name="estado" id="estado"
                                    class="from-selec col-6" required min="1">
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    EstadoCarnetJpaController sese = new EstadoCarnetJpaController();
                                    List lista4 = sese.findEstadoCarnetEntities();

                                    for (int i = 0; i < lista4.size(); i++) {
                                        EstadoCarnet des = (EstadoCarnet) lista4.get(i);
                                        out.print("<option value='" + des.getIdestadoCarnet() + "'>");
                                        out.print(des.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!--bottones-->
                        <button type="submit" class="btn botones px-4 text-white" id="btnGuardarEst" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-danger" id="btnLimpiarModalEstudinates" onclick="limpiarFormulario()">Limpiar</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>          
<!-- MODAL ESTUDIANTES GUARDAR FINAL-->

<!-- MODALE LOGO MENUPRINCIAL GUARDAR INICIO -->
<div class="modal fade" id="formulario3Modallogo" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form action="<%=request.getContextPath()%>/LogoServlet" method="post" class="row g-2 "  enctype="multipart/form-data">
                    <h2 class="pt-5 pb-4 text-center">Actualizar</h2>
                    <input type="hidden" class="form-control" id="id" name="id" required min="1" maxlength="45" value="14" readonly>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-6"><b>Logo:</b></div>
                            <input type="file" class="form-control" id="logo" name="logo" accept="image/*" >
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-6"><b>Nombre Completo:</b></div>
                            <input type="text" class="form-control" id="nombre" name="nombre"  maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-6"><b>Firma:</b></div>
                            <input type="file" class="form-control" id="firma" name="firma" accept="image/*" >
                        </div>
                    </div>
                    <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                        <button type="submit" class="btn botones px-4 text-white"
                                value="Guardar" name="action" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALE LOGO MENUPRINCIAL GUARDAR FINAL -->

<!-- MODALE ELIMINAR CARNET GUARDAR INICIO -->
<div class="modal fade" id="ModalEliminarEstudiante" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioEliminadarEstudiante" class="row g-2">
                    <h2 class=" text-center">Eliminar Carnet</h2>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedulaElimi" name="cedulaEE" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                            <select name="tipoDocumentoEE" id="tipoDocumentoElimi"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Nombres: </b></div>
                            <input type="text" class="form-control" id="nombresElimi" name="nombresEE" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">

                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidosElimi" name="apellidosEE" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">

                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Formacion:</b></div>
                            <select name="formacionEE" id="formacionElimi"
                                    class="from-selec col-6 " required >
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Sede:</b></div>
                            <select name="sedeEE" id="sedeElimi"
                                    class="from-selec col-6" required >
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correoElimi" name="correoEE" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 ">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fotografia:</b></div>
                            <input type="file" class="form-control" id="fotoElimi" name="fotoEE" accept="image/*" >
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group ">
                            <div class="input-group-text col-6"><b>Estado del Carnet:</b></div>
                            <select name="estadoEE" id="estadoElimi"
                                    class="from-selec col-6" required >
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center pt-5"><!--bottones-->
                        <button type="submit" class="btn botones text-white px-4" id="btnEliminarEstudiante" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>            
<!-- MODALE ELIMINAR CARNET GUARDAR FINAL -->

<!-- MODALE FUNCIONARIOS GUARDAR INICIO -->
<div class="modal fade" id="ModalFuncionarios" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">

            <div class="modal-body">
                <form id="FormularioFuncionarios" class="row g-2 ">

                    <h2 class="pt-3 pb-2 text-center">Registrar Funcionario</h2>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedulaGuFun" name="cedulaFun" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                            <select name="tipoDocumentoFun" id="tipoDocumentoGuFun"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    TipodocumentoJpaController controlador = new TipodocumentoJpaController();
                                    List listaFunci = controlador.findTipodocumentoEntities();

                                    for (int i = 0; i < listaFunci.size(); i++) {
                                        Tipodocumento tipo = (Tipodocumento) listaFunci.get(i);
                                        out.print("<option value='" + tipo.getIdTipoDocumento() + "'>");
                                        out.print(tipo.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Nombres: </b></div>
                            <input type="text" class="form-control" id="nombresGuFun" name="nombresFun" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidosGuFun" name="apellidosFun" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Tipo de Funcionario:</b></div>
                            <select name="RolFun" id="RolGuFun"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    RolFuncionarioJpaController controladorFunci = new RolFuncionarioJpaController();
                                    List listaRolF = controladorFunci.findRolFuncionarioEntities();

                                    for (int i = 0; i < listaRolF.size(); i++) {
                                        RolFuncionario tipo = (RolFuncionario) listaRolF.get(i);
                                        out.print("<option value='" + tipo.getCodigo() + "'>");
                                        out.print(tipo.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Area de Trabajo:</b></div>
                            <select name="AreaFun" id="AreaGuFun"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    AreaTrabajoJpaController controladorAreaTrabajo = new AreaTrabajoJpaController();
                                    List listaArea = controladorAreaTrabajo.findAreaTrabajoEntities();

                                    for (int i = 0; i < listaArea.size(); i++) {
                                        AreaTrabajo tipo = (AreaTrabajo) listaArea.get(i);
                                        out.print("<option value='" + tipo.getCodigo() + "'>");
                                        out.print(tipo.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correoGuFun" name="correoFun" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fotografia:</b></div>
                            <input type="file" class="form-control" id="fotoGuFun" name="fotoFun" accept="image/*" required>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fecha de Vencimiento:</b></div>
                            <input type="date" class="form-control" id="venceGuFun" name="venceFun" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>RH:</b></div>
                            <input type="text" class="form-control" id="rhGuFun" name="rhFun" required>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Estado del Carnet:</b></div>
                            <select name="estadoFun" id="estadoGuFun"
                                    class="from-selec col-6" required min="1">
                                <option value="" disabled selected hidden>-- Elija --</option>
                                <%
                                    EstadoCarnetJpaController controEstad = new EstadoCarnetJpaController();
                                    List listaEsta = controEstad.findEstadoCarnetEntities();

                                    for (int i = 0; i < listaEsta.size(); i++) {
                                        EstadoCarnet des = (EstadoCarnet) listaEsta.get(i);
                                        out.print("<option value='" + des.getIdestadoCarnet() + "'>");
                                        out.print(des.getNombre());
                                        out.print("</option>");
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!--bottones-->
                        <button type="submit" class="btn botones px-4 text-white" id="btnGuardarFun" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-danger" id="btnLimpiarModalFuncionarios" onclick="limpiarFormulario()">Limpiar</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div> 
<!-- MODALE FUNCIONARIOS GUARDAR FINAL -->

<!-- MODALES DE AREA GUARDAR INICIO -->
<div class="modal fade" id="ModalArea" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content ">
            <div class="modal-body">
                <form id="FormularioArea" class="row g-2 ">
                    <h2 class="pt-3 pb-2 text-center">Registrar Area de Trabajo</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoGuAr" name="codigoAr" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreGuAr" name="nombreAr" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarArea" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-danger" id="btnLimpiarModalAreas" onclick="limpiarFormulario()">Limpiar</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE AREA GUARDAR FINAL -->

<!-- MODALES DE ROL GUARDAR INICIO -->
<div class="modal fade" id="ModalRol" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content ">
            <div class="modal-body">
                <form id="FormularioRol" class="row g-2 ">
                    <h2 class="pt-3 pb-2 text-center">Registrar Rol de Trabajo</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoGuRol" name="codigoRol" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreGuRol" name="nombreRol" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3">
                        <button type="submit" class="btn botones text-white px-4" id="btnGuardarRol" style="background-color: #018E42;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-danger" id="btnLimpiarModalRol" onclick="limpiarFormulario()">Limpiar</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE ROL GUARDAR FINAL -->