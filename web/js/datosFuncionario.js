// FUNCION OBTENER DATOS FUNCIONARIOS INICIO
function obtenerDatosFuncionarios(cedula, tipoDocumento, nombres, apellidos,  Area,roles, correo, fechaVencimiento, rh, estadoCarnetId) {
    // Establecer los valores de los campos del formulario en el modal
    $('#cedulaEdFun').val(cedula);
    $('#tipoDocumentoEdFun').val(tipoDocumento);
    $('#nombresEdFun').val(nombres);
    $('#apellidosEdFun').val(apellidos);
    $('#AreaEdFun').val(Area);
    $('#RolEdFun').val(roles);
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
