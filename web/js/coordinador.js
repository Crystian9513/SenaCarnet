// Función para obtener los datos de la sede y mostrarlos en el modal
function obtenerDatosEstudiantes(Cedula, TipoDocumentoFk, Nombres, Apellidos, FormacionFk, SedeFk, Correo, VenceCarnet, EstadoCarnetIdestadoCarnet)
{
    $('#cedula2').val(Cedula);
    $('#tipoDocumento2').val(TipoDocumentoFk);
    $('#nombres2').val(Nombres);
    $('#apellidos2').val(Apellidos);
    $('#formacion2').val(FormacionFk);
    $('#sede2').val(SedeFk);
    $('#correo2').val(Correo);
    $('#vence2').val(VenceCarnet);
    $('#estado2').val(EstadoCarnetIdestadoCarnet);

    $('#cedula2').closest('.input-group').hide();
    $('#tipoDocumento2').closest('.input-group').hide();
    $('#nombres2').closest('.input-group').hide();
    $('#apellidos2').closest('.input-group').hide();
    $('#formacion2').closest('.input-group').hide();
    $('#sede2').closest('.input-group').hide();
    $('#correo2').closest('.input-group').hide();
    $('#vence2').closest('.input-group').hide();
    $('#estado2').closest('.input-group').show();
    $('#foto2').closest('.col-12').hide();
    $('#foto2').closest('.input-group').show();

    // Realiza las solicitudes AJAX para obtener datos adicionales si es necesario
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerTipoDocumento.jsp",
        data: {tipoDocumento: TipoDocumentoFk},
        dataType: "html",
        success: function (data) {
            $("#tipoDocumento2").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerSede.jsp",
        data: {idsede: SedeFk},
        dataType: "html",
        success: function (data) {
            $("#sede2").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerFormaciones.jsp",
        data: {formacionesTipos: FormacionFk},
        dataType: "html",
        success: function (data) {
            $("#formacion2").empty().append(data);
        }
    });
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerEstadoCarnet.jsp",
        data: {estadoCarnetTipo: EstadoCarnetIdestadoCarnet},
        dataType: "html",
        success: function (data) {
            $("#estado2").empty().append(data);
        }
    });
}