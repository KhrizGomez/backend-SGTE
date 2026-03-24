package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.PlazoTramiteDTO;
import com.app.backend.entities.tramites.VentanaRecepcion;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.VentanaRecepcionRepository;
import com.app.backend.repositories.tramites.PlantillaTramiteRepository;
import com.app.backend.services.tramites.PlazoTramiteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class PlazoTramiteServiceImpl implements PlazoTramiteService {

    private final VentanaRecepcionRepository ventanaRecepcionRepository;
    private final PlantillaTramiteRepository plantillaTramiteRepository;

    @Override @Transactional(readOnly = true)
    public List<PlazoTramiteDTO> listarPorTipoTramite(@NonNull Integer idTipoTramite) { return ventanaRecepcionRepository.findByPlantillaIdPlantilla(idTipoTramite).stream().map(this::toDTO).toList(); }

    @Override @Transactional(readOnly = true)
    public PlazoTramiteDTO obtenerPorId(@NonNull Integer id) { return toDTO(ventanaRecepcionRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Ventana no encontrada con id: " + id))); }

    @Override
    public PlazoTramiteDTO crear(PlazoTramiteDTO dto) {
        VentanaRecepcion p = VentanaRecepcion.builder()
                .plantilla(plantillaTramiteRepository.findById(dto.getIdTipoTramite()).orElseThrow(() -> new RecursoNoEncontradoException("Plantilla no encontrada: " + dto.getIdTipoTramite())))
                .fechaApertura(dto.getFechaApertura()).fechaCierre(dto.getFechaCierre())
                .permiteExtension(dto.getPermiteExtension() != null ? dto.getPermiteExtension() : false)
                .diasMaxExtension(dto.getDiasMaxExtension() != null ? dto.getDiasMaxExtension() : 0).build();
        return toDTO(ventanaRecepcionRepository.save(p));
    }

    @Override
    public PlazoTramiteDTO actualizar(@NonNull Integer id, PlazoTramiteDTO dto) {
        VentanaRecepcion p = ventanaRecepcionRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Ventana no encontrada con id: " + id));
        p.setFechaApertura(dto.getFechaApertura()); p.setFechaCierre(dto.getFechaCierre()); p.setPermiteExtension(dto.getPermiteExtension()); p.setDiasMaxExtension(dto.getDiasMaxExtension());
        return toDTO(ventanaRecepcionRepository.save(p));
    }

    @Override
    public void eliminar(@NonNull Integer id) { if (!ventanaRecepcionRepository.existsById(id)) throw new RecursoNoEncontradoException("Ventana no encontrada con id: " + id); ventanaRecepcionRepository.deleteById(id); }

    private PlazoTramiteDTO toDTO(VentanaRecepcion p) { return PlazoTramiteDTO.builder().idPlazo(p.getIdVentana()).idTipoTramite(p.getPlantilla().getIdPlantilla()).fechaApertura(p.getFechaApertura()).fechaCierre(p.getFechaCierre()).permiteExtension(p.getPermiteExtension()).diasMaxExtension(p.getDiasMaxExtension()).build(); }
}
