package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.request.AccionPasoRequestDTO;
import com.app.backend.dtos.tramites.request.CrearSolicitudRequestDTO;
import com.app.backend.dtos.tramites.response.SolicitudCreadaResponseDTO;
import com.app.backend.dtos.tramites.response.SolicitudDetalleResponseDTO;
import com.app.backend.services.tramites.SolicitudService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SolicitudTramiteController {

    private final SolicitudService solicitudService;

    @PostMapping(value = {"", "/crear"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SolicitudCreadaResponseDTO> crearSolicitud(
            @Valid @RequestPart("solicitud") CrearSolicitudRequestDTO solicitud,
            @RequestPart(value = "archivos", required = false) List<MultipartFile> archivos) {
        solicitudService.crearSolicitudConDocumentos(solicitud, archivos);
        return ResponseEntity.status(HttpStatus.CREATED).body(SolicitudCreadaResponseDTO.builder()
                .mensaje("Solicitud creada exitosamente")
                .idPlantilla(solicitud.getIdPlantilla())
                .cantidadArchivos(archivos != null ? archivos.size() : 0)
                .build());
    }

    @GetMapping("/mis-solicitudes")
    public ResponseEntity<List<SolicitudDetalleResponseDTO>> listarMisSolicitudes() {
        return ResponseEntity.ok(solicitudService.listarSolicitudesUsuarioAutenticado());
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<SolicitudDetalleResponseDTO> obtenerDetalle(@PathVariable Integer id) {
        return ResponseEntity.ok(solicitudService.obtenerDetalleSolicitud(id));
    }

    @GetMapping("/por-rol/{nombreRol}")
    public ResponseEntity<List<SolicitudDetalleResponseDTO>> listarPorRol(@PathVariable String nombreRol) {
        return ResponseEntity.ok(solicitudService.listarSolicitudesPorRol(nombreRol));
    }

    @PostMapping("/aprobar")
    public ResponseEntity<Map<String, String>> aprobar(@Valid @RequestBody AccionPasoRequestDTO dto) {
        solicitudService.aprobarPasoActual(dto);
        return ResponseEntity.ok(Map.of("mensaje", "Paso aprobado exitosamente"));
    }

    @PostMapping("/rechazar")
    public ResponseEntity<Map<String, String>> rechazar(@Valid @RequestBody AccionPasoRequestDTO dto) {
        solicitudService.rechazarSolicitud(dto);
        return ResponseEntity.ok(Map.of("mensaje", "Solicitud rechazada exitosamente"));
    }
}
