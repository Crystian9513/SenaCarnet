const filtroInput = document.getElementById("filtro1");
const filas = document.querySelectorAll("#tablaEstudiantes tbody tr");
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
});
//FILTRO FINAL

// FUNCION OBTENER DATOS ESTUDIANTES INICIO
function obtenerDatosEstudiantes(Cedula, TipoDocumentoFk, Nombres, Apellidos, FormacionFk, SedeFk, Correo, VenceCarnet, RH, EstadoCarnetIdestadoCarnet)
{
    $('#cedula2').val(Cedula);
    $('#tipoDocumento2').val(TipoDocumentoFk);
    $('#nombres2').val(Nombres);
    $('#apellidos2').val(Apellidos);
    $('#formacion2').val(FormacionFk);
    $('#sede2').val(SedeFk);
    $('#correo2').val(Correo);
    $('#vence2').val(VenceCarnet);
    $('#rh2').val(RH);
    $('#estado2').val(EstadoCarnetIdestadoCarnet);


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
// FUNCION OBTENER DATOS ESTUDIANTES FINAL
// FUNCION OBTENER DATOS ESTUDIANTES 2 INICIO
function obtenerDatosEstudiantes2(Cedula, TipoDocumentoFk, Nombres, Apellidos, FormacionFk, SedeFk, Correo, VenceCarnet, RH, EstadoCarnetIdestadoCarnet)
{
    $('#cedula20').val(Cedula);
    $('#tipoDocumento20').val(TipoDocumentoFk);
    $('#nombres20').val(Nombres);
    $('#apellidos20').val(Apellidos);
    $('#formacion20').val(FormacionFk);
    $('#sede20').val(SedeFk);
    $('#correo20').val(Correo);
    $('#vence20').val(VenceCarnet);
    $('#rh20').val(RH);
    $('#estado20').val(EstadoCarnetIdestadoCarnet);

    $('#cedula20').closest('.input-group').hide();
    $('#tipoDocumento20').closest('.input-group').hide();
    $('#nombres20').closest('.input-group').hide();
    $('#apellidos20').closest('.input-group').hide();
    $('#sede20').closest('.input-group').hide();
    $('#correo20').closest('.input-group').hide();

    // Realiza las solicitudes AJAX para obtener datos adicionales si es necesario
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerTipoDocumento.jsp",
        data: {tipoDocumento: TipoDocumentoFk},
        dataType: "html",
        success: function (data) {
            $("#tipoDocumento20").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerSede.jsp",
        data: {idsede: SedeFk},
        dataType: "html",
        success: function (data) {
            $("#sede20").empty().append(data);
        }
    });

    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerFormaciones.jsp",
        data: {formacionesTipos: FormacionFk},
        dataType: "html",
        success: function (data) {
            $("#formacion20").empty().append(data);
        }
    });
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerEstadoCarnet.jsp",
        data: {estadoCarnetTipo: EstadoCarnetIdestadoCarnet},
        dataType: "html",
        success: function (data) {
            $("#estado20").empty().append(data);
        }
    });
}
// FUNCION OBTENER DATOS ESTUDIANTES 2 FINAL

