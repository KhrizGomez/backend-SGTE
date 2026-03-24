package com.app.backend.repositories.tramites;

import com.app.backend.entities.tramites.TipoTramite;
import com.app.backend.repositories.tramites.projections.DetallesTramiteBaseProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoTramiteRepository extends JpaRepository<TipoTramite, Integer> {
    List<TipoTramite> findByEstaActivoTrue();
    List<TipoTramite> findByCategoriaIdCategoria(Integer idCategoria);

    @Query("""
        SELECT
            t.idTipoTramite AS idTipoTramite,
            t.nombreTramite AS nombreTramite,
            t.descripcionTramite AS descripcionTramite,
            c.idCategoria AS idCategoria,
            c.nombreCategoria AS categoria,
            f.idFlujo AS idFlujo,
            f.nombreFlujo AS nombreFlujo,
            f.descripcionFlujo AS descripcionFlujo,
            u.idUsuario AS idUsuarioCreador,
            COALESCE(cr.nombreUsuario, CONCAT(COALESCE(u.nombres, ''), ' ', COALESCE(u.apellidos, ''))) AS usuarioCreador,
            f.version AS version,
            p.idPlazo AS idPlazo,
            p.fechaApertura AS fechaApertura,
            p.fechaCierre AS fechaCierre,
            p.permiteExtension AS permiteExtension,
            t.diasEstimados AS diasEstimados,
            t.estaActivo AS estaActivo,
            t.disponibleExternos AS disponibleExternos
        FROM TipoTramite t
        LEFT JOIN t.categoria c
        LEFT JOIN t.definicionFlujo f
        LEFT JOIN f.creadoPor u
        LEFT JOIN u.credencial cr
        LEFT JOIN t.plazos p
               ON p.idPlazo = (
                   SELECT MAX(p2.idPlazo)
                   FROM PlazoTramite p2
                   WHERE p2.tipoTramite.idTipoTramite = t.idTipoTramite
               )
        ORDER BY t.idTipoTramite
        """)
    List<DetallesTramiteBaseProjection> findAllDetallesBase();

    @Query("""
        SELECT
            t.idTipoTramite AS idTipoTramite,
            t.nombreTramite AS nombreTramite,
            t.descripcionTramite AS descripcionTramite,
            c.idCategoria AS idCategoria,
            c.nombreCategoria AS categoria,
            f.idFlujo AS idFlujo,
            f.nombreFlujo AS nombreFlujo,
            f.descripcionFlujo AS descripcionFlujo,
            u.idUsuario AS idUsuarioCreador,
            COALESCE(cr.nombreUsuario, CONCAT(COALESCE(u.nombres, ''), ' ', COALESCE(u.apellidos, ''))) AS usuarioCreador,
            f.version AS version,
            p.idPlazo AS idPlazo,
            p.fechaApertura AS fechaApertura,
            p.fechaCierre AS fechaCierre,
            p.permiteExtension AS permiteExtension,
            t.diasEstimados AS diasEstimados,
            t.estaActivo AS estaActivo,
            t.disponibleExternos AS disponibleExternos
        FROM TipoTramite t
        LEFT JOIN t.categoria c
        LEFT JOIN t.definicionFlujo f
        LEFT JOIN f.creadoPor u
        LEFT JOIN u.credencial cr
        LEFT JOIN t.plazos p
               ON p.idPlazo = (
                   SELECT MAX(p2.idPlazo)
                   FROM PlazoTramite p2
                   WHERE p2.tipoTramite.idTipoTramite = t.idTipoTramite
               )
        WHERE t.idTipoTramite = :idTipoTramite
        """)
    Optional<DetallesTramiteBaseProjection> findDetalleBaseByIdTipoTramite(@Param("idTipoTramite") Integer idTipoTramite);
}
