package com.app.backend.repositories.tramites;

import com.app.backend.dtos.tramites.response.SolicitudesDocumentosRespuestaDTO;
import com.app.backend.dtos.tramites.response.SolicitudesPlantillasVigentesRespuestaDTO;
import com.app.backend.entities.tramites.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
    Optional<Solicitud> findByCodigoSolicitud(String codigoSolicitud);
    List<Solicitud> findByUsuarioIdUsuario(Integer idUsuario);
    List<Solicitud> findByEstadoActual(String estadoActual);
    List<Solicitud> findByPlantillaIdPlantilla(Integer idPlantilla);
    void deleteAllByPlantillaIdPlantilla(Integer idPlantilla);

    @Query(value = "Select * from tramites.fn_sl_solicitudestramitesvigentes(:p_idusuario)", nativeQuery = true)
    List<SolicitudesPlantillasVigentesRespuestaDTO> listarPlantillasVigentes(@Param("p_idusuario") Integer idusuario);

    @Query(value = "Select * from tramites.fn_sl_solicitudestramiteshistorial(:p_idsolicitud)", nativeQuery = true)
    List<SolicitudesDocumentosRespuestaDTO> listarDocumentosTramites(@Param("p_idsolicitud") Integer idsolicitud);

    @Modifying
    @Query(value = "CALL tramites.sp_in_solicitudtramite(:p_jsonsolicitud)", nativeQuery = true)
    void crearSolicitudTramite(@Param("p_jsonsolicitud") String jsonSolicitud);
}
