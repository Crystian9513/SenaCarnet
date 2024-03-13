<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Estudiantes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/tabla.css"/>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <style>
        #videoContainer {
            display: flex;
            flex-direction: row;
            align-items: flex-start;
            width: 100%;
            max-width: 300px;
        }

        #videoElement, #canvasElement, #fotoTomada {
            width: 100%;
            height: auto;
        }

        #canvasElement, #fotoTomada {
            display: none;
        }
    </style>
   
</head>
<body style="background-color: #fefafb;">
    <!-- MENU INICIO -->
    <nav class="navbar text-l navbar-expand-lg "  style="background-color: #6acd56;">
        <div class="container">
            <!-- Tu código del menú aquí -->
        </div>
    </nav>
    <!-- MENU FINAL -->

    <!-- Modal -->
    <div class="modal fade" id="cameraModal" tabindex="-1" aria-labelledby="cameraModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="cameraModalLabel">Funcionamiento de la cámara</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="videoContainer">
                        <video id="videoElement" autoplay></video>
                        <canvas id="canvasElement"></canvas>
                        <img id="fotoTomada" src="" alt="Foto Tomada">
                        <input type="hidden" id="fotoBase64" name="fotoBase64">
                    </div>
                    <!-- Botones para abrir y cerrar la cámara -->
                    <button id="abrirCamara" class="btn btn-primary">Abrir Cámara</button>
                    <button id="cerrarCamara" class="btn btn-secondary" style="display:none;">Cerrar Cámara</button>
                    <button id="tomarFoto" class="btn btn-success">Tomar Foto</button>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Botón para abrir el modal -->
    <button id="openModalButton" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#cameraModal">Abrir Modal</button>

    <!-- Script de Bootstrap y tu script personal -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
    // Agrega el evento para tomar la foto
    document.getElementById('tomarFoto').addEventListener('click', function () {
        var video = document.getElementById('videoElement');
        var canvas = document.getElementById('canvasElement');
        var context = canvas.getContext('2d');

        // Ajusta el tamaño del canvas al tamaño del video
        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;

        context.drawImage(video, 0, 0, canvas.width, canvas.height);
        var fotoTomada = canvas.toDataURL('image/png');

        // Almacena la foto temporalmente en el campo oculto
        document.getElementById('fotoBase64').value = fotoTomada;

        // Muestra la foto tomada si lo deseas
        var fotoTomadaElement = document.getElementById('fotoTomada');
        fotoTomadaElement.src = fotoTomada;
        fotoTomadaElement.style.display = 'inline-block';
        canvas.style.display = 'none'; // Oculta el canvas después de tomar la foto
        document.getElementById('foto').value = fotoTomada;
    });

    // Agrega el evento para enviar la foto a la base de datos
    document.getElementById('enviarFoto').addEventListener('click', function () {
        // Obtiene la foto almacenada temporalmente
        var fotoBase64 = document.getElementById('fotoBase64').value;

        // Realiza una solicitud AJAX al servidor para enviar la foto
        // Aquí debes implementar el código para enviar la foto al servidor
        // Puedes usar jQuery para facilitar esta tarea
        $.ajax({
            url: 'guardar_foto.php',
            type: 'POST',
            data: { fotoBase64: fotoBase64 },
            success: function(response) {
                // Maneja la respuesta del servidor si es necesario
                console.log('Foto guardada correctamente en la base de datos');
            },
            error: function(xhr, status, error) {
                // Maneja cualquier error que ocurra durante la solicitud AJAX
                console.error('Error al guardar la foto en la base de datos:', error);
            }
        });
    });
</script>
</body>
</html>
