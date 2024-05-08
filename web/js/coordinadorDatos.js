const filtroInput = document.getElementById("filtro1");
const filas = document.querySelectorAll("#tablaAdministradores tbody tr");
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

function obtenerDatosCoordinador(Cedula, Nombre, Apellido, Correo) {
    $('#cedula2').val(Cedula);
    $('#nombre2').val(Nombre);
    $('#apellido2').val(Apellido);
    $('#correo2').val(Correo);

}
