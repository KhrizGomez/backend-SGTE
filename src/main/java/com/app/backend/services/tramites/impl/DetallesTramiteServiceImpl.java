package com.app.backend.services.tramites.impl;

import com.app.backend.dtos.tramites.DetallesTramiteDTO;
import com.app.backend.dtos.tramites.PasoFlujoTramiteDTO;
import com.app.backend.dtos.tramites.RequisitoTramiteDTO;
import com.app.backend.entities.sistema.Usuario;
import com.app.backend.entities.tramites.PasoFlujo;
import com.app.backend.entities.tramites.RequisitoTramite;
import com.app.backend.exceptions.RecursoNoEncontradoException;
import com.app.backend.repositories.tramites.PasoFlujoRepository;
import com.app.backend.repositories.tramites.RequisitoTramiteRepository;
import com.app.backend.repositories.tramites.TipoTramiteRepository;
import com.app.backend.repositories.tramites.projections.DetallesTramiteBaseProjection;
import com.app.backend.services.tramites.DetallesTramiteService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@SuppressWarnings("null")
public class DetallesTramiteServiceImpl implements DetallesTramiteService {

    private final TipoTramiteRepository tipoTramiteRepository;
    private final PasoFlujoRepository pasoFlujoRepository;
    private final RequisitoTramiteRepository requisitoTramiteRepository;

        @Override
        public List<DetallesTramiteDTO> listarTodos() {
        return tipoTramiteRepository.findAllDetallesBase()
            .stream()
            .map(this::buildDetalle)
            .toList();
        }

    @Override
    public DetallesTramiteDTO obtenerPorTipoTramite(@NonNull Integer idTipoTramite) {
        DetallesTramiteBaseProjection base = tipoTramiteRepository.findDetalleBaseByIdTipoTramite(idTipoTramite)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo trámite no encontrado con id: " + idTipoTramite));

        return buildDetalle(base);
        }

        private DetallesTramiteDTO buildDetalle(DetallesTramiteBaseProjection base) {

        List<PasoFlujoTramiteDTO> pasos = base.getIdFlujo() == null
                ? List.of()
                : pasoFlujoRepository.findByDefinicionFlujoIdFlujoOrderByOrdenPasoAsc(base.getIdFlujo())
                        .stream()
                        .map(this::toPasoFlujoTramiteDTO)
                        .toList();

        List<RequisitoTramiteDTO> requisitos = requisitoTramiteRepository
            .findByTipoTramiteIdTipoTramiteOrderByNumeroOrdenAsc(base.getIdTipoTramite())
                .stream()
                .map(this::toRequisitoTramiteDTO)
                .toList();

        return DetallesTramiteDTO.builder()
                .idTipoTramite(base.getIdTipoTramite())
                .nombreTramite(base.getNombreTramite())
                .descripcionTramite(base.getDescripcionTramite())
                .idCategoria(base.getIdCategoria())
                .categoria(base.getCategoria())
                .idFlujo(base.getIdFlujo())
                .nombreFlujo(base.getNombreFlujo())
                .descripcionFlujo(base.getDescripcionFlujo())
                .idUsuarioCreador(base.getIdUsuarioCreador())
                .usuarioCreador(base.getUsuarioCreador() != null ? base.getUsuarioCreador().trim() : null)
                .version(base.getVersion())
                .pasosTramite(pasos)
                .requisitosTramite(requisitos)
                .idPlazo(base.getIdPlazo())
                .fechaApertura(base.getFechaApertura())
                .fechaCierre(base.getFechaCierre())
                .permiteExtension(base.getPermiteExtension())
                .diasEstimados(base.getDiasEstimados())
                .estaActivo(base.getEstaActivo())
                .disponibleExternos(base.getDisponibleExternos())
                .build();
    }

    private PasoFlujoTramiteDTO toPasoFlujoTramiteDTO(PasoFlujo paso) {
        return PasoFlujoTramiteDTO.builder()
                .idPaso(paso.getIdPaso())
                .idFlujo(paso.getDefinicionFlujo().getIdFlujo())
                .ordenPaso(paso.getOrdenPaso())
                .idEtapa(paso.getEtapaProcesamiento().getIdEtapa())
                .codigoEtapa(paso.getEtapaProcesamiento().getCodigoEtapa())
                .nombreEtapa(paso.getEtapaProcesamiento().getNombreEtapa())
                .descripcionEtapa(paso.getEtapaProcesamiento().getDescripcionEtapa())
                .idRolRequerido(paso.getRolRequerido() != null ? paso.getRolRequerido().getIdRol() : null)
                .rolRequerido(paso.getRolRequerido() != null ? paso.getRolRequerido().getNombreRol() : null)
                .idUsuarioEncargado(paso.getUsuarioEncargado() != null ? paso.getUsuarioEncargado().getIdUsuario() : null)
                .usuarioEncargado(formatUsuario(paso.getUsuarioEncargado()))
                .horasSla(paso.getHorasSla())
                .build();
    }

    private RequisitoTramiteDTO toRequisitoTramiteDTO(RequisitoTramite requisito) {
        return RequisitoTramiteDTO.builder()
                .idRequisito(requisito.getIdRequisito())
                .idTipoTramite(requisito.getTipoTramite().getIdTipoTramite())
                .nombreRequisito(requisito.getNombreRequisito())
                .descripcionRequisito(requisito.getDescripcionRequisito())
                .esObligatorio(requisito.getEsObligatorio())
                .tipoDocumento(requisito.getTipoDocumento())
                .tamanoMaxMb(requisito.getTamanoMaxMb())
                .extensionesPermitidas(requisito.getExtensionesPermitidas())
                .numeroOrden(requisito.getNumeroOrden())
                .build();
    }

    private String formatUsuario(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        if (usuario.getCredencial() != null && usuario.getCredencial().getNombreUsuario() != null) {
            return usuario.getCredencial().getNombreUsuario();
        }
        String nombres = usuario.getNombres() != null ? usuario.getNombres() : "";
        String apellidos = usuario.getApellidos() != null ? usuario.getApellidos() : "";
        String nombreCompleto = (nombres + " " + apellidos).trim();
        return nombreCompleto.isEmpty() ? null : nombreCompleto;
    }
}