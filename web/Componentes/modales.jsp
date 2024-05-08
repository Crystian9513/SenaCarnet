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
<div class="modal fade" id="formularioModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form z class="row g-2 ">
                    <h2 class="pt-5 pb-4 text-center">Registrar Sede</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoModal" name="codigo" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreModal" name="nombre" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                        <button type="submit" class="btn botones  px-4"
                                value="Guardar" name="action" style="background-color: #6acd56;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE SEDES GUARDAR FINAL -->

<!-- MODALES DE FORMACIONES GUARDAR INICIO -->
<div class="modal fade" id="formulario3Modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form action="<%=request.getContextPath()%>/FormacionesServlet" method="post" class="row g-2 "
                      onsubmit="return validarCamposVacios2();">
                    <h2 class="pt-5 pb-4 text-center">Registrar Formacion</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoModal3" name="codigo2" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreModal3" name="nombre2" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Sedes</b></div>
                            <select name="SedesLista" id="SedesLista"
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
                    <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                        <button type="submit" class="btn botones px-4 "  style="background-color: #6acd56;"
                                name="action" value="Guardar" ><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE FORMACIONES GUARDAR FINAL -->

<!-- MODALES DE ADMINISTRADOR GUARDAR INICIO -->
<div class="modal fade" id="formularioModalAdmin" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form action="<%=request.getContextPath()%>/administradoresServlet" method="post" class="row g-2 "
                      >
                    <h2 class="pt-5 pb-4 text-center">Registrar Administrador</h2>

                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Cedula:</b></div>

                            <input type="number" class="form-control" id="cedula" name="cedula" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombres:</b></div>

                            <input type="text" class="form-control" id="nombre" name="nombre" required min="1" maxlength="40">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Apellidos:</b></div>

                            <input type="text" class="form-control" id="apellido" name="apellido" required min="1" maxlength="40">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Clave:</b></div>

                            <input type="text" class="form-control" id="clave" name="clave" required min="1" maxlength="20">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group">
                            <div class="input-group-text col-5"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correo" name="correo" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                        <button type="submit" class="btn botones  px-4"
                                value="Guardar" name="action" style="background-color: #6acd56;"><b>Guardar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE ADMINISTRADOR GUARDAR FINAL -->

<!-- MODALES DE COORDINADOR GUARDAR INICIO -->
<div class="modal fade" id="formularioModalCoordinador" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form action="<%=request.getContextPath()%>/CoordinadorDatosServlet" method="post" class="row g-2 "
                      >
                    <h2 class="pt-5 pb-4 text-center">Registrar Coordinador</h2>

                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Cedula:</b></div>

                            <input type="number" class="form-control" id="cedula" name="cedula" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombres:</b></div>

                            <input type="text" class="form-control" id="nombre" name="nombre" required min="1">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Apellidos:</b></div>

                            <input type="text" class="form-control" id="apellido" name="apellido" required min="1">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Clave:</b></div>

                            <input type="text" class="form-control" id="clave" name="clave" required min="1" maxlength="20">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group">
                            <div class="input-group-text col-5"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correo" name="correo" required min="1" maxlength="45">
                        </div>
                    </div>
            </div>
            <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                <button type="submit" class="btn botones  px-4"
                        value="Guardar" name="action" style="background-color: #6acd56;"><b>Guardar</b></button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
            </div>
            </form>
        </div>
    </div>
</div>
</div>
<!-- MODALES DE COORDINADOR GUARDAR FINAL -->

<!-- MODAL ESTUDIANTES GUARDAR INICIO-->
<div class="modal fade" id="formularioModalEstudiante" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

            <div class="modal-body">
                <form action="<%=request.getContextPath()%>/estudiantesServlet" method="post" class="row g-2 " enctype="multipart/form-data">

                    <h2 class="pt-5 pb-4 text-center">Registrar Aprendiz</h2>
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
                    <div class="col-12 text-center py-5 pt-5"><!--bottones-->
                        <button type="submit" class="btn botones px-4 " style="background-color: #6acd56;"
                                name="action" value="Guardar"><b>Guardar</b></button>
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
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Logo:</b></div>
                            <input type="file" class="form-control" id="logo" name="logo" accept="image/*" >
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Nombre Completo:</b></div>
                            <input type="text" class="form-control" id="nombre" name="nombre"  maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Firma:</b></div>
                            <input type="file" class="form-control" id="firma" name="firma" accept="image/*" >
                        </div>
                    </div>
                    <div class="col-12 text-center py-5 pt-5"><!-- bottones -->
                        <button type="submit" class="btn botones  px-4"
                                value="Guardar" name="action" style="background-color: #6acd56;"><b>Actualizar</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" >Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALE LOGO MENUPRINCIAL GUARDAR FINAL -->