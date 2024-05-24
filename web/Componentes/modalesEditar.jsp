 
<div class="modal fade" id="ModalSedeOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioSedeOpciones" class="row g-2 " >
                    <h2 class="pt-3 pb-2 text-center">Sede</h2>
                    <div class="col-12">
                        <div class="input-group ">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoOp" name="codigoEl" required min="1" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group ">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>

                            <input type="text" class="form-control" id="nombreOp" name="nombreEl" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3 "><!-- bottones -->
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarSede" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarSede" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarSede" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE SEDES EDITAR FINAL -->

<!-- MODALES DE FORMACIONES EDITAR INICIO -->
<div class="modal fade" id="ModalFormacionOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioFormacionesopciones" class="row g-2 "
                      >
                    <h2 class="pt-3 pb-2 text-center">Formacion</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoOpA" name="codigoOpEl" required min="1" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreOpA" name="nombreOpEl" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Sedes</b></div>
                            <select name="SedesListaOpEl" id="SedesListaOpA"
                                    class="from-selec col-7"  required>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!-- bottones -->
                        <button type="submit" class="btn botones px-4 text-white" id="btnActualizarFormacion" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarFormacion"><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarFormacion" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE FORMACIONES EDITAR FINAL -->

<!-- MODALES DE ADMINISTRADOR EDITAR INICIO -->
<div class="modal fade" id="ModalAdministradorOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioAdministradorOpciones" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Administrador</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedulaAdmEd" name="cedulaEd" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombres:</b></div>
                            <input type="text" class="form-control" id="nombreAdmEd" name="nombreEd" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidoAdmEd" name="apellidoEd" required min="1" maxlength="45">
                        </div>
                    </div>  
                    <div class="col-12">
                        <div class="input-group input-group">
                            <div class="input-group-text col-5"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correoAdmEd" name="correoEd" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!-- bottones -->
                        <button type="submit" class="btn botones text-white px-4" id="btnActualizarAdm" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarAdm" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarAdministrador" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE ADMINISTRADOR EDITAR FINAL -->

<!-- MODALES DE COORDINADOR EDITAR INICIO -->
<div class="modal fade" id="ModalEditarCoordinador" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="formularioCoordinadorOpciones" class="row g-2 " >
                    <h2 class="pt-3 pb-2 text-center">Coordinador</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedulaCordinador" name="cedulaCd" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombres:</b></div>
                            <input type="text" class="form-control" id="nombreCordinador" name="nombreCd" required min="1">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidoCordinador" name="apellidoCd" required min="1">
                        </div>
                    </div>  
                    <div class="col-12">
                        <div class="input-group input-group">
                            <div class="input-group-text col-5"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correoCordinador" name="correoCd" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!-- bottones -->
                        <button type="submit" class="btn botones text-white px-4" id="btnActualizarCd"
                                style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarCd"><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarCoordinador" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE COORDINADOR EDITAR FINAL -->

<!-- MODALES DE ESTUDIANTES EDITAR INICIO -->
<div class="modal fade" id="ModalEstudiantesActualizar" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioEstudiantesActualizar" class="row g-2 ">
                    <h2 class="pt-3 pb-2 text-center">Aprendiz</h2>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedulaEstudiante" name="cedula2" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                            <select name="tipoDocumento2" id="tipoDocumentoEstudiante"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Nombres: </b></div>
                            <input type="text" class="form-control" id="nombresEstudiante" name="nombres2" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidosEstudiante" name="apellidos2" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Formacion:</b></div>
                            <select name="formacion2" id="formacionEstudiante"
                                    class="from-selec col-6 " required min="1">
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Sede:</b></div>
                            <select name="sede2" id="sedeEstudiante"
                                    class="from-selec col-6" required min="1">
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correoEstudiante" name="correo2" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fotografia:</b></div>
                            <input type="file" class="form-control" id="fotoEstudiante" name="foto2" accept="image/*" >
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fecha de Vencimiento del carnet:</b></div>
                            <input type="date" class="form-control" id="venceEstudiante" name="vence2" required min="1">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>RH:</b></div>
                            <input type="text" class="form-control" id="rhEstudiante" name="rh2" required>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Estado del Carnet:</b></div>
                            <select name="estado2" id="estadoEstudiante"
                                    class="from-selec col-6" required min="1" >
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!--bottones-->
                        <button type="submit" class="btn botones px-4 text-white" id="btnActualizarEst" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarEst" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarEstudinate" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="ModalNuevaFormacion" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioNuevaFormacion" class="row g-2 ">
                    <h2 class="pt-3 text-center">Aprendiz</h2>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedulaNF" name="cedula20" required min="1" max="2147483647" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                            <select name="tipoDocumento20" id="tipoDocumentoNF"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Nombres: </b></div>
                            <input type="" class="form-control" id="nombresNF" name="nombres20" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidosNF" name="apellidos20" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Sede:</b></div>
                            <select id="sedeNF" name="sede20" 
                                    class="from-selec col-6" required min="1">
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Correo: </b></div>
                            <input type="email" class="form-control" id="correoNF" name="correo20" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Formacion:</b></div>
                            <select name="formacion20" id="formacionNF"
                                    class="from-selec col-6 " required min="1">
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fotografia:</b></div>
                            <input type="file" class="form-control" id="fotoNF" name="foto20" accept="image/*" >
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fecha de Vencimiento del carnet:</b></div>
                            <input type="date" class="form-control" id="venceNF" name="vence20" >
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>RH:</b></div>
                            <input type="text" class="form-control" id="rhNF" name="rh20" required>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Estado del Carnet:</b></div>
                            <select name="estado20" id="estadoNF"
                                    class="from-selec col-6" required min="1" >
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!--bottones-->
                        <button type="submit" class="btn botones px-4 text-white" id="btnNuevaFormacion" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <!--   <button type="submit" class="btn " name="action" value="Eliminar" style="background-color: #6acd56;"><b>Eliminar</b></button>-->  
                        <button type="button" class="btn btn-secondary" id="btnCerrarNuevaFormacion" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>   
<!-- MODALES DE ESTUDIANTES EDITAR FINAL -->

<!-- MODALES DE FUNCIONARIOS EDITAR INICIO -->
<div class="modal fade" id="ModalEditarFuncionarios" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioEditarFuncionarios" class="row g-2 ">
                    <h2 class="pt-3 pb-2 text-center">Registrar Funcionario</h2>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Cedula:</b></div>
                            <input type="number" class="form-control" id="cedulaEdFun" name="cedulaFunEl" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Tipo de Documento:</b></div>
                            <select name="tipoDocumentoFunEl" id="tipoDocumentoEdFun"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Nombres: </b></div>
                            <input type="text" class="form-control" id="nombresEdFun" name="nombresFunEl" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Apellidos:</b></div>
                            <input type="text" class="form-control" id="apellidosEdFun" name="apellidosFunEl" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Rol:</b></div>
                            <select name="RolFunEl" id="RolEdFun"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                     <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Area de Trabajo:</b></div>
                            <select name="AreaFunEl" id="AreaEdFun"
                                    class="from-selec-sm col-6" required>
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Correo:</b></div>
                            <input type="email" class="form-control" id="correoEdFun" name="correoFunEl" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fotografia:</b></div>
                            <input type="file" class="form-control" id="fotoEdFun" name="fotoFunEl" accept="image/*" required>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Fecha de Vencimiento:</b></div>
                            <input type="date" class="form-control" id="venceEdFun" name="venceFunEl" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>RH:</b></div>
                            <input type="text" class="form-control" id="rhEdFun" name="rhFunEl" required>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text col-6"><b>Estado del Carnet:</b></div>
                            <select name="estadoFunEl" id="estadoEdFun"
                                    class="from-selec col-6" required min="1">
                                <option value="" disabled selected hidden>-- Elija --</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3"><!--bottones-->
                        <button type="submit" class="btn botones px-4 text-white" id="btnActualizarFun" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarFun" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarfuncionario"  data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div> 
<!-- MODALES DE FUNCIONARIOS EDITAR FINAL -->

<!-- MODALES DE AREA EDITAR INICIO -->
<div class="modal fade" id="ModalAreasOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="FormularioAreaOpciones" class="row g-2 " >
                    <h2 class="pt-3 pb-2 text-center">Registrar Area de Trabajo</h2>
                    <div class="col-12">
                        <div class="input-group ">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoOpArEForm" name="codigoElAr" required min="1" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group ">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreOpArEForm" name="nombreElAr" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3 "><!-- bottones -->
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarArea" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarArea" ><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarAreas" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE AREA EDITAR FINAL -->

<!-- MODALES DE ROL EDITAR INICIO -->
<div class="modal fade" id="ModalRolOpciones" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content ">
            <div class="modal-body">
                <form id="FormularioRolOpciones" class="row g-2 ">
                    <h2 class="pt-3 pb-2 text-center">Registrar Rol de Trabajo</h2>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Codigo:</b></div>
                            <input type="number" class="form-control" id="codigoEdRol" name="codigoElRol" required min="1" max="2147483647">
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreEdRol" name="nombreElRol" required min="1" maxlength="45">
                        </div>
                    </div>
                      <div class="col-12 text-center py-3 pt-3 "><!-- bottones -->
                        <button type="submit" class="btn botones text-white px-4" id="btnEditarRol" style="background-color: #018E42;"><b>Actualizar</b></button>
                        <button type="submit" class="btn text-white bg-danger" id="btnEliminarRol"><b>Eliminar</b></button>
                        <button type="button" class="btn btn-secondary" id="btnCerrarRol" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE ROL EDITAR FINAL -->

<!-- MODALES DE RESTABLECIOMIENTO DE CONTRASEÑA INICIO -->
<div class="modal fade" id="ModalRestablecimientoContrasena" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form action="<%=request.getContextPath()%>/CambioContrasenaPrimeraVezServlet" class="row g-2">
                    <h2 class="pt-3 pb-2 text-center">Restablecimiento de Contraseña</h2>
                    <div class="col-12">
                        <div class="input-group ">
                            <div class="input-group-text col-5"><b>Numero de Cedula:</b></div>
                            <input type="number" class="form-control" id="codigoOp" name="codigoEl" required min="1" readonly>
                        </div>
                    </div>
                    <div class="col-12">
                        <div class="input-group ">
                            <div class="input-group-text col-5"><b>Nombre:</b></div>
                            <input type="text" class="form-control" id="nombreOp" name="nombreEl" required min="1" maxlength="45">
                        </div>
                    </div>
                    <div class="col-12 text-center py-3 pt-3 "><!-- bottones -->
                        <button type="submit" class="btn botones text-white px-4" value="BuscarCedula" name="action" style="background-color: #018E42;"><b>Buscar</b></button>
                        <button type="submit" class="btn text-white" value="Restablecer" name="action" style="background-color: #018E42;"><b>Restablecer</b></button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- MODALES DE RESTABLECIOMIENTO DE CONTRASEÑA FINAL -->