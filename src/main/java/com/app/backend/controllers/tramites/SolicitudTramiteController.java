package com.app.backend.controllers.tramites;

import com.app.backend.dtos.tramites.request.CrearSolicitudRequestDTO;
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

    @PostMapping(value = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> crearSolicitud(
            @Valid @RequestPart("solicitud") CrearSolicitudRequestDTO solicitud,
            @RequestPart(value = "archivos", required = false) List<MultipartFile> archivos) {
        solicitudService.crearSolicitudConDocumentos(solicitud, archivos);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Solicitud creada exitosamente"));
    }
}

