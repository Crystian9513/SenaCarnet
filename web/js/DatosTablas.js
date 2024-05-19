
function obtenerDatosAdministradores(Cedula, Nombre, Apellido, Correo) {
    $('#cedulaAdmEd').val(Cedula);
    $('#nombreAdmEd').val(Nombre);
    $('#apellidoAdmEd').val(Apellido);
    $('#correoAdmEd').val(Correo);
}


function obtenerDatosEliminarcarnet(Cedula, TipoDocumentoFk, Nombres, Apellidos, FormacionFk, SedeFk, Correo, VenceCarnet, EstadoCarnetIdestadoCarnet)
{
    $('#cedulaElimi').val(Cedula);
    $('#tipoDocumentoElimi').val(TipoDocumentoFk);
    $('#nombresElimi').val(Nombres);
    $('#apellidosElimi').val(Apellidos);
    $('#formacionElimi').val(FormacionFk);
    $('#sedeElimi').val(SedeFk);
    $('#correoElimi').val(Correo);
    $('#venceElimi').val(VenceCarnet);
    $('#estadoElimi').val(EstadoCarnetIdestadoCarnet);

    $('#cedulaElimi').closest('.input-group').hide();
    $('#tipoDocumentoElimi').closest('.input-group').hide();
    $('#nombresElimi').closest('.input-group').hide();
    $('#apellidosElimi').closest('.input-group').hide();
    $('#formacionElimi').closest('.input-group').hide();
    $('#sedeElimi').closest('.input-group').hide();
    $('#correoElimi').closest('.input-group').hide();
    $('#fotoElimi').closest('.col-12').hide();
    $('#fotoElimi').closest('.input-group').show();


    // Realiza las solicitudes AJAX para obtener datos adicionales si es necesario
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerTipoDocumento.jsp",
        data: {tipoDocumento: TipoDocumentoFk},
        dataType: "html",
        success: function (data) {
            $("#tipoDocumentoElimi").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerSede.jsp",
        data: {idsede: SedeFk},
        dataType: "html",
        success: function (data) {
            $("#sedeElimi").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerFormaciones.jsp",
        data: {formacionesTipos: FormacionFk},
        dataType: "html",
        success: function (data) {
            $("#formacionElimi").empty().append(data);
        }
    });
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerEstadoCarnet.jsp",
        data: {estadoCarnetTipo: EstadoCarnetIdestadoCarnet},
        dataType: "html",
        success: function (data) {
            $("#estadoElimi").empty().append(data);
        }
    });
}


function obtenerDatosCoordinador(Cedula, Nombre, Apellido, Correo) {
    $('#cedulaCordinador').val(Cedula);
    $('#nombreCordinador').val(Nombre);
    $('#apellidoCordinador').val(Apellido);
    $('#correoCordinador').val(Correo);

}


// FUNCION OBTENER DATOS ESTUDIANTES INICIO
function obtenerDatosEstudiantes(cedula, tipoDocumento, nombres, apellidos, formacionId, sedeId, correo, fechaVencimiento, rh, estadoCarnetId) {
    // Establecer los valores de los campos del formulario en el modal
    $('#cedulaEstudiante').val(cedula);
    $('#tipoDocumentoEstudiante').val(tipoDocumento);
    $('#nombresEstudiante').val(nombres);
    $('#apellidosEstudiante').val(apellidos);
    $('#formacionEstudiante').val(formacionId);
    $('#sedeEstudiante').val(sedeId);
    $('#correoEstudiante').val(correo);
    $('#venceEstudiante').val(fechaVencimiento);
    $('#rhEstudiante').val(rh);
    $('#estadoEstudiante').val(estadoCarnetId);

    // Realizar las solicitudes AJAX para obtener datos adicionales si es necesario
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerTipoDocumento.jsp",
        data: {tipoDocumento: tipoDocumento},
        dataType: "html",
        success: function (data) {
            $("#tipoDocumentoEstudiante").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerSede.jsp",
        data: {idsede: sedeId},
        dataType: "html",
        success: function (data) {
            $("#sedeEstudiante").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerFormaciones.jsp",
        data: {formacionesTipos: formacionId},
        dataType: "html",
        success: function (data) {
            $("#formacionEstudiante").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerEstadoCarnet.jsp",
        data: {estadoCarnetTipo: estadoCarnetId},
        dataType: "html",
        success: function (data) {
            $("#estadoEstudiante").empty().append(data);
        }
    });
}

// FUNCION OBTENER DATOS ESTUDIANTES FINAL
// FUNCION OBTENER DATOS ESTUDIANTES 2 INICIO
function obtenerDatosEstudiantes2(Cedula, TipoDocumentoFk, Nombres, Apellidos, FormacionFk, SedeFk, Correo, VenceCarnet, RH, EstadoCarnetIdestadoCarnet)
{
    $('#cedulaNF').val(Cedula);
    $('#tipoDocumentoNF').val(TipoDocumentoFk);
    $('#nombresNF').val(Nombres);
    $('#apellidosNF').val(Apellidos);
    $('#formacionNF').val(FormacionFk);
    $('#sedeNF').val(SedeFk);
    $('#correoNF').val(Correo);
    $('#venceNF').val(VenceCarnet);
    $('#rhNF').val(RH);
    $('#estadoNF').val(EstadoCarnetIdestadoCarnet);

    $('#cedulaNF').closest('.input-group').hide();
    $('#tipoDocumentoNF').closest('.input-group').hide();
    $('#nombresNF').closest('.input-group').hide();
    $('#apellidosNF').closest('.input-group').hide();
    $('#sedeNF').closest('.input-group').hide();
    $('#correoNF').closest('.input-group').hide();

    // Realiza las solicitudes AJAX para obtener datos adicionales si es necesario
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerTipoDocumento.jsp",
        data: {tipoDocumento: TipoDocumentoFk},
        dataType: "html",
        success: function (data) {
            $("#tipoDocumentoNF").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerSede.jsp",
        data: {idsede: SedeFk},
        dataType: "html",
        success: function (data) {
            $("#sedeNF").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerFormaciones.jsp",
        data: {formacionesTipos: FormacionFk},
        dataType: "html",
        success: function (data) {
            $("#formacionNF").empty().append(data);
        }
    });
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerEstadoCarnet.jsp",
        data: {estadoCarnetTipo: EstadoCarnetIdestadoCarnet},
        dataType: "html",
        success: function (data) {
            $("#estadoNF").empty().append(data);
        }
    });
}
// FUNCION OBTENER DATOS ESTUDIANTES 2 FINAL


// Función para obtener los datos de la sede y mostrarlos en el modal
function obtenerDatosSede(codigo, nombre) {
    $('#codigoOp').val(codigo);
    $('#nombreOp').val(nombre);
}


function obtenerDatosFormacion(IdFormacion, Nombre, SedeId) {
    // Actualiza los campos del modal con los datos de la formación seleccionada
    $("#codigoOpA").val(IdFormacion);
    $("#nombreOpA").val(Nombre);
    $("#SedesListaOpA").val(SedeId);

    // Realiza la solicitud AJAX para obtener los datos adicionales si es necesario
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerSede.jsp",
        data: {idsede: SedeId},
        dataType: "html",
        success: function (data) {
            $("#SedesListaOpA").empty().append(data);
        }
    });
}

// FUNCION OBTENER DATOS FUNCIONARIOS INICIO
function obtenerDatosFuncionarios(cedula, tipoDocumento, nombres, apellidos, roles,Area, correo, fechaVencimiento, rh, estadoCarnetId) {
    // Establecer los valores de los campos del formulario en el modal
    $('#cedulaEdFun').val(cedula);
    $('#tipoDocumentoEdFun').val(tipoDocumento);
    $('#nombresEdFun').val(nombres);
    $('#apellidosEdFun').val(apellidos);
    $('#RolEdFun').val(roles);
    $('#AreaEdFun').val(Area);
    $('#correoEdFun').val(correo);
    $('#venceEdFun').val(fechaVencimiento);
    $('#rhEdFun').val(rh);
    $('#estadoEdFun').val(estadoCarnetId);

    // Realizar las solicitudes AJAX para obtener datos adicionales si es necesario
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerTipoDocumento.jsp",
        data: {tipoDocumento: tipoDocumento},
        dataType: "html",
        success: function (data) {
            $("#tipoDocumentoEdFun").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerRolFuncionario.jsp",
        data: {rolFuncionarioa: roles},
        dataType: "html",
        success: function (data) {
            $("#RolEdFun").empty().append(data);
        }
    });
    
     $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerAreaTrabajo.jsp",
        data: {areaTrabajo: Area},
        dataType: "html",
        success: function (data) {
            $("#AreaEdFun").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerEstadoCarnet.jsp",
        data: {estadoCarnetTipo: estadoCarnetId},
        dataType: "html",
        success: function (data) {
            $("#estadoEdFun").empty().append(data);
        }
    });
}

// Función para obtener los datos de la sede y mostrarlos en el modal
function obtenerDatosAreas(codigoAre, nombreAre) {
    $('#codigoOpArEForm').val(codigoAre);
    $('#nombreOpArEForm').val(nombreAre);
}


// Función para obtener los datos de la sede y mostrarlos en el modal
function obtenerDatosRoles(codigo, nombre) {
    $('#codigoEdRol').val(codigo);
    $('#nombreEdRol').val(nombre);
}