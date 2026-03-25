package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.documentos.DocumentoAdjuntoRequestDTO;
import com.app.backend.dtos.tramites.request.CrearSolicitudRequestDTO;
import com.app.backend.dtos.tramites.response.SolicitudResponseDTO;
import com.app.backend.dtos.tramites.response.SolicitudesTramitesVigentesRespuestaDTO;
import com.app.backend.entities.tramites.Solicitud;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.academico.CarreraRepository;
import com.app.backend.repositories.sistema.UsuarioRepository;
import com.app.backend.repositories.tramites.PasoFlujoRepository;
import com.app.backend.repositories.tramites.PlantillaTramiteRepository;
import com.app.backend.repositories.tramites.SolicitudRepository;
import com.app.backend.services.externos.IAlmacenamientoService;
import com.app.backend.services.externos.IJwtService;
import com.app.backend.services.tramites.SolicitudService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final PlantillaTramiteRepository plantillaTramiteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;
    private final PasoFlujoRepository pasoFlujoRepository;
    private final HttpServletRequest request;
    private final IJwtService jwtService;
    private final IAlmacenamientoService almacenamientoService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudResponseDTO> listarTodas() {
        return solicitudRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudResponseDTO> listarPorUsuario(@NonNull Integer idUsuario) {
        return solicitudRepository.findByUsuarioIdUsuario(idUsuario).stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudResponseDTO> listarPorEstado(String estado) {
        return solicitudRepository.findByEstadoActual(estado).stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudResponseDTO obtenerPorId(@NonNull Integer id) {
        return toDTO(solicitudRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada con id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudResponseDTO obtenerPorCodigo(String codigo) {
        return toDTO(solicitudRepository.findByCodigoSolicitud(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada con cÃ³digo: " + codigo)));
    }

    @Override
    public SolicitudResponseDTO crear(SolicitudResponseDTO dto) {
        Solicitud s = Solicitud.builder()
                .codigoSolicitud(dto.getCodigoSolicitud())
                .plantilla(plantillaTramiteRepository.findById(dto.getIdPlantilla()).orElseThrow(
                        () -> new RecursoNoEncontradoException("Plantilla no encontrada: " + dto.getIdPlantilla())))
                .usuario(usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(
                        () -> new RecursoNoEncontradoException("Usuario no encontrado: " + dto.getIdUsuario())))
                .detallesSolicitud(dto.getDetallesSolicitud())
                .prioridad(dto.getPrioridad() != null ? dto.getPrioridad() : "normal")
                .estadoActual(dto.getEstadoActual() != null ? dto.getEstadoActual() : "pendiente")
                .fechaEstimadaFin(dto.getFechaEstimadaFin())
                .build();
        if (dto.getIdCarrera() != null)
            s.setCarrera(carreraRepository.findById(dto.getIdCarrera()).orElseThrow(
                    () -> new RecursoNoEncontradoException("Carrera no encontrada: " + dto.getIdCarrera())));
        if (dto.getCreadoPorId() != null)
            s.setCreadoPor(usuarioRepository.findById(dto.getCreadoPorId()).orElseThrow(
                    () -> new RecursoNoEncontradoException("Usuario no encontrado: " + dto.getCreadoPorId())));
        if (dto.getPasoActualId() != null)
            s.setPasoActual(pasoFlujoRepository.findById(dto.getPasoActualId()).orElseThrow(
                    () -> new RecursoNoEncontradoException("Paso no encontrado: " + dto.getPasoActualId())));
        return toDTO(solicitudRepository.save(s));
    }

    @Override
    public SolicitudResponseDTO actualizar(@NonNull Integer id, SolicitudResponseDTO dto) {
        Solicitud s = solicitudRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada con id: " + id));
        s.setDetallesSolicitud(dto.getDetallesSolicitud());
        s.setPrioridad(dto.getPrioridad());
        s.setEstadoActual(dto.getEstadoActual());
        s.setFechaEstimadaFin(dto.getFechaEstimadaFin());
        s.setFechaRealFin(dto.getFechaRealFin());
        s.setResolucion(dto.getResolucion());
        if (dto.getPasoActualId() != null)
            s.setPasoActual(pasoFlujoRepository.findById(dto.getPasoActualId()).orElseThrow(
                    () -> new RecursoNoEncontradoException("Paso no encontrado: " + dto.getPasoActualId())));
        return toDTO(solicitudRepository.save(s));
    }

    @Override
    public void eliminar(@NonNull Integer id) {
        if (!solicitudRepository.existsById(id))
            throw new RecursoNoEncontradoException("Solicitud no encontrada con id: " + id);
        solicitudRepository.deleteById(id);
    }

    @Override
    public void crearSolicitudConDocumentos(CrearSolicitudRequestDTO dto, List<MultipartFile> archivos) {
        // 1. Extraer idUsuario e idCarrera del JWT
        Integer idUsuario = null;
        Integer idCarrera = null;
        String encaAuth = request.getHeader("Authorization");
        if (encaAuth != null && encaAuth.startsWith("Bearer ")) {
            String jwt = encaAuth.substring(7);
            idUsuario = jwtService.extraerIdUsuario(jwt);
            idCarrera = jwtService.extraerIdCarrera(jwt);
        }
        if (idUsuario == null) {
            throw new RuntimeException("No se pudo extraer el usuario del token JWT");
        }

        // 2. Subir archivos a Azure y construir la lista de documentos adjuntos
        List<DocumentoAdjuntoRequestDTO> documentosAdjuntos = new ArrayList<>();

        if (archivos != null && !archivos.isEmpty()) {
            List<Integer> idsRequisitos = dto.getIdsRequisitos();
            if (idsRequisitos == null || idsRequisitos.size() != archivos.size()) {
                throw new IllegalArgumentException(
                        "La cantidad de idsRequisitos debe coincidir con la cantidad de archivos adjuntos");
            }

            for (int i = 0; i < archivos.size(); i++) {
                MultipartFile archivo = archivos.get(i);
                Integer idRequisito = idsRequisitos.get(i);

                // Generar nombre Ãºnico para el blob
                String extension = obtenerExtension(archivo.getOriginalFilename());
                String nombreGenerado = String.format("req_%d_user_%d_%d%s",
                        idRequisito, idUsuario, System.currentTimeMillis(), extension);

                // Subir a Azure Storage
                String urlArchivo = almacenamientoService.subirArchivos(archivo);

                documentosAdjuntos.add(DocumentoAdjuntoRequestDTO.builder()
                        .idRequisito(idRequisito)
                        .nombreArchivo(nombreGenerado)
                        .nombreOriginal(archivo.getOriginalFilename())
                        .rutaArchivo(urlArchivo)
                        .tamanoBytes(archivo.getSize())
                        .build());
            }
        }

        // 3. Construir el JSON para el SP
        try {
            ObjectNode jsonRoot = objectMapper.createObjectNode();
            jsonRoot.put("idplantilla", dto.getIdPlantilla());
            jsonRoot.put("idusuario", idUsuario);
            if (idCarrera != null) {
                jsonRoot.put("idcarrera", idCarrera);
            }
            jsonRoot.put("detallessolicitud", dto.getDetallesSolicitud());
            jsonRoot.put("prioridad", dto.getPrioridad() != null ? dto.getPrioridad() : "Normal");
            jsonRoot.put("pasoactual", dto.getPasoActual() != null ? dto.getPasoActual() : 1);

            if (!documentosAdjuntos.isEmpty()) {
                ArrayNode docsArray = jsonRoot.putArray("documentosadjuntos");
                for (DocumentoAdjuntoRequestDTO doc : documentosAdjuntos) {
                    ObjectNode docNode = objectMapper.createObjectNode();
                    docNode.put("idrequisito", doc.getIdRequisito());
                    docNode.put("nombrearchivo", doc.getNombreArchivo());
                    docNode.put("nombreoriginal", doc.getNombreOriginal());
                    docNode.put("rutaarchivo", doc.getRutaArchivo());
                    docNode.put("tamanobytes", doc.getTamanoBytes());
                    docsArray.add(docNode);
                }
            }

            String jsonSolicitud = objectMapper.writeValueAsString(jsonRoot);

            // 4. Llamar al stored procedure
            solicitudRepository.crearSolicitudTramite(jsonSolicitud);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la solicitud de trÃ¡mite: " + e.getMessage(), e);
        }
    }

    private SolicitudResponseDTO toDTO(Solicitud s) {
        return SolicitudResponseDTO.builder().idSolicitud(s.getIdSolicitud()).codigoSolicitud(s.getCodigoSolicitud())
                .idPlantilla(s.getPlantilla().getIdPlantilla()).idUsuario(s.getUsuario().getIdUsuario())
                .idCarrera(s.getCarrera() != null ? s.getCarrera().getIdCarrera() : null)
                .creadoPorId(s.getCreadoPor() != null ? s.getCreadoPor().getIdUsuario() : null)
                .detallesSolicitud(s.getDetallesSolicitud()).prioridad(s.getPrioridad())
                .pasoActualId(s.getPasoActual() != null ? s.getPasoActual().getIdPaso() : null)
                .estadoActual(s.getEstadoActual()).fechaCreacion(s.getFechaCreacion())
                .fechaEstimadaFin(s.getFechaEstimadaFin()).fechaRealFin(s.getFechaRealFin())
                .resolucion(s.getResolucion()).build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudesTramitesVigentesRespuestaDTO> listarTramitesVigente() {
        try {
            Integer idUsuario = null;
            String encaAuth = request.getHeader("Authorization");
            if (encaAuth != null && encaAuth.startsWith("Bearer ")) {
                String jwt = encaAuth.substring(7);
                idUsuario = jwtService.extraerIdUsuario(jwt);
            }
            return solicitudRepository.listarTramitesVigentes(idUsuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar las solicitudes de tramites: " + e.getMessage());
        }
    }

    private String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null || !nombreArchivo.contains("."))
            return "";
        return nombreArchivo.substring(nombreArchivo.lastIndexOf("."));
    }
}


