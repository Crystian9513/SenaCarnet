//FILTRO INICIO    
const filtroInput = document.getElementById("filtro1");
const filas = document.querySelectorAll("#tablaSede tbody tr");

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

const filtroInput2 = document.getElementById("filtro2");
const filasFormacion = document.querySelectorAll("#tablaFormacion tbody tr"); // Cambio de nombre aquí
filtroInput2.addEventListener("input", function () {
    const filtro = filtroInput2.value.trim().toLowerCase();

    filasFormacion.forEach(function (fila) { // Cambio de nombre aquí también
        const textoFila = fila.textContent.toLowerCase();
        if (textoFila.includes(filtro)) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    });
});
//FILTRO FINAL


// Función para obtener los datos de la sede y mostrarlos en el modal
function obtenerDatosSede(idSede, nombreSede) {
    // Actualiza los campos del modal con los datos de la sede seleccionada
    document.getElementById('codigoModal2').value = idSede;
    document.getElementById('nombreModal2').value = nombreSede;
}


function obtenerDatosFormacion(IdFormacion, Nombre, SedeId) {
    // Actualiza los campos del modal con los datos de la formación seleccionada
    $("#codigoModal5").val(IdFormacion);
    $("#nombreModal5").val(Nombre);
    $("#SedesLista2").val(SedeId);

    // Realiza la solicitud AJAX para obtener los datos adicionales si es necesario
    $.ajax({
        type: "POST",
        url: "../Busquedas/obtenerSede.jsp",
        data: {idsede: SedeId},
        dataType: "html",
        success: function (data) {
            $("#SedesLista2").empty().append(data);
        }
    });
}
z
