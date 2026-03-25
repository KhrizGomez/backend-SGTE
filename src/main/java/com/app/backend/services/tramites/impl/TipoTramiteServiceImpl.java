package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.TipoTramiteDTO;
import com.app.backend.dtos.tramites.response.PlantillaTramiteDTO;
import com.app.backend.dtos.tramites.response.PlantillaTramiteResponseDTO;
import com.app.backend.dtos.tramites.response.RequisitoTramiteResponseDTO;
import com.app.backend.entities.tramites.PlantillaTramite;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.CategoriaRepository;
import com.app.backend.repositories.tramites.FlujoTrabajoRepository;
import com.app.backend.repositories.tramites.PlantillaTramiteRepository;
import com.app.backend.repositories.academico.EstudianteRepository;
import com.app.backend.services.externos.IJwtService;
import com.app.backend.services.tramites.TipoTramiteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class TipoTramiteServiceImpl implements TipoTramiteService {

    private final PlantillaTramiteRepository plantillaTramiteRepository;
    private final CategoriaRepository categoriaRepository;
    private final FlujoTrabajoRepository flujoTrabajoRepository;
    private final EstudianteRepository estudianteRepository;
    private final IJwtService jwtService;
    private final HttpServletRequest request;

    @Override
    @Transactional(readOnly = true)
    public List<TipoTramiteDTO> listarTodos() {
        return plantillaTramiteRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoTramiteDTO> listarActivos() {
        return plantillaTramiteRepository.findByEstaActivoTrue().stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoTramiteDTO> listarPorCategoria(@NonNull Integer idCategoria) {
        return plantillaTramiteRepository.findByCategoriaIdCategoria(idCategoria).stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoTramiteDTO obtenerPorId(@NonNull Integer id) {
        return toDTO(plantillaTramiteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Plantilla no encontrada con id: " + id)));
    }

    @Override
    public TipoTramiteDTO crear(TipoTramiteDTO dto) {
        PlantillaTramite t = PlantillaTramite.builder()
                .nombrePlantilla(dto.getNombreTramite())
                .descripcionPlantilla(dto.getDescripcionTramite())
                .diasResolucionEstimados(dto.getDiasEstimados() != null ? dto.getDiasEstimados() : 5)
                .estaActivo(dto.getEstaActivo() != null ? dto.getEstaActivo() : true)
                .disponibleExternos(dto.getDisponibleExternos() != null ? dto.getDisponibleExternos() : false)
                .build();
        if (dto.getIdCategoria() != null)
            t.setCategoria(categoriaRepository.findById(dto.getIdCategoria()).orElseThrow(
                    () -> new RecursoNoEncontradoException("Categoría no encontrada: " + dto.getIdCategoria())));
        if (dto.getIdFlujo() != null)
            t.setFlujoTrabajo(flujoTrabajoRepository.findById(dto.getIdFlujo())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado: " + dto.getIdFlujo())));
        return toDTO(plantillaTramiteRepository.save(t));
    }

    @Override
    public TipoTramiteDTO actualizar(@NonNull Integer id, TipoTramiteDTO dto) {
        PlantillaTramite t = plantillaTramiteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Plantilla no encontrada con id: " + id));
        t.setNombrePlantilla(dto.getNombreTramite());
        t.setDescripcionPlantilla(dto.getDescripcionTramite());
        t.setDiasResolucionEstimados(dto.getDiasEstimados());
        t.setEstaActivo(dto.getEstaActivo());
        t.setDisponibleExternos(dto.getDisponibleExternos());
        if (dto.getIdCategoria() != null)
            t.setCategoria(categoriaRepository.findById(dto.getIdCategoria()).orElseThrow(
                    () -> new RecursoNoEncontradoException("Categoría no encontrada: " + dto.getIdCategoria())));
        if (dto.getIdFlujo() != null)
            t.setFlujoTrabajo(flujoTrabajoRepository.findById(dto.getIdFlujo())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Flujo no encontrado: " + dto.getIdFlujo())));
        return toDTO(plantillaTramiteRepository.save(t));
    }

    @Override
    public void eliminar(@NonNull Integer id) {
        if (!plantillaTramiteRepository.existsById(id))
            throw new RecursoNoEncontradoException("Plantilla no encontrada con id: " + id);
        plantillaTramiteRepository.deleteById(id);
    }

    private TipoTramiteDTO toDTO(PlantillaTramite t) {
        return TipoTramiteDTO.builder()
                .idTipoTramite(t.getIdPlantilla())
                .nombreTramite(t.getNombrePlantilla())
                .descripcionTramite(t.getDescripcionPlantilla())
                .idCategoria(t.getCategoria() != null ? t.getCategoria().getIdCategoria() : null)
                .idFlujo(t.getFlujoTrabajo() != null ? t.getFlujoTrabajo().getIdFlujo() : null)
                .diasEstimados(t.getDiasResolucionEstimados())
                .estaActivo(t.getEstaActivo())
                .disponibleExternos(t.getDisponibleExternos())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlantillaTramiteResponseDTO> listarPlantillasTramites(String categoria, Boolean activo,
            String busqueda) {
        Boolean esExternoCalculado = null;
        Integer idCarreraCalculado = null;

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String jwt = authHeader.substring(7);
                Integer idUsuario = jwtService.extraerIdUsuario(jwt);
                idCarreraCalculado = jwtService.extraerIdCarrera(jwt);

                if (idUsuario != null) {
                    var estudianteOpt = estudianteRepository.findByUsuarioIdUsuario(idUsuario);
                    if (estudianteOpt.isPresent()) {
                        esExternoCalculado = estudianteOpt.get().getEsExterno();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al procesar JWT en listarPlantillas: " + e.getMessage());
            }
        }

        Map<Integer, List<PlantillaTramiteDTO>> agrupado = plantillaTramiteRepository
                .listarPlantillas(categoria, activo, busqueda, esExternoCalculado, idCarreraCalculado)
                .stream().collect(Collectors.groupingBy(PlantillaTramiteDTO::getIdplantilla));

        return agrupado.entrySet().stream().map(entry -> {
            PlantillaTramiteDTO base = entry.getValue().get(0);

            PlantillaTramiteResponseDTO plantilla = new PlantillaTramiteResponseDTO();
            plantilla.setIdplantilla(base.getIdplantilla());
            plantilla.setNombreplantilla(base.getNombreplantilla());
            plantilla.setDescripcionplantilla(base.getDescripcionplantilla());
            plantilla.setNombrecategoria(base.getNombrecategoria());
            plantilla.setDiasresolucionestimados(base.getDiasresolucionestimados());
            plantilla.setEstaactivo(base.getEstaactivo());
            plantilla.setDisponiblesexternos(base.getDisponiblesexternos());
            plantilla.setPasos(base.getPasos());

            List<RequisitoTramiteResponseDTO> requisitos = entry.getValue().stream()
                    .filter(req -> req.getNombrerequisito() != null)
                    .map(req -> {
                        RequisitoTramiteResponseDTO r = new RequisitoTramiteResponseDTO();
                        r.setNombrerequisito(req.getNombrerequisito());
                        r.setDescripcionrequisito(req.getDescripcionrequisito());
                        r.setEsobligatorio(req.getEsobligatorio());
                        r.setTipodocumento(req.getTipodocumento());
                        r.setExtensionespermitidas(req.getExtensionespermitidas());
                        r.setNumeroorden(req.getNumeroorden());
                        return r;
                    }).collect(Collectors.toList());

            plantilla.setRequisitos(requisitos);
            return plantilla;
        }).collect(Collectors.toList());
    }
}
