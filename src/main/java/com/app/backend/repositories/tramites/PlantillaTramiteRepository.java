package com.app.backend.repositories.tramites;

import com.app.backend.dtos.tramites.response.PlantillaTramiteDTO;
import com.app.backend.entities.tramites.PlantillaTramite;
import com.app.backend.repositories.tramites.projections.DetallesTramiteBaseProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantillaTramiteRepository extends JpaRepository<PlantillaTramite, Integer> {
    List<PlantillaTramite> findByEstaActivoTrue();
    List<PlantillaTramite> findByCategoriaIdCategoria(Integer idCategoria);

    @Query("""
        SELECT
            p.idPlantilla AS idPlantilla,
            p.nombrePlantilla AS nombrePlantilla,
            p.descripcionPlantilla AS descripcionPlantilla,
            c.idCategoria AS idCategoria,
            c.nombreCategoria AS categoria,
            car.idCarrera AS idCarrera,
            car.nombreCarrera AS carrera,
            f.idFlujo AS idFlujo,
            f.nombreFlujo AS nombreFlujo,
            f.descripcion AS descripcion,
            u.idUsuario AS idUsuarioCreador,
            COALESCE(cr.nombreUsuario, CONCAT(COALESCE(u.nombres, ''), ' ', COALESCE(u.apellidos, ''))) AS usuarioCreador,
            f.version AS version,
            v.idVentana AS idVentana,
            v.fechaApertura AS fechaApertura,
            v.fechaCierre AS fechaCierre,
            v.permiteExtension AS permiteExtension,
            p.diasResolucionEstimados AS diasResolucionEstimados,
            p.estaActivo AS estaActivo,
            p.disponibleExternos AS disponibleExternos
        FROM PlantillaTramite p
        LEFT JOIN p.categoria c
        LEFT JOIN p.carrera car
        LEFT JOIN p.flujoTrabajo f
        LEFT JOIN f.creadoPor u
        LEFT JOIN u.credencial cr
        LEFT JOIN p.ventanas v
               ON v.idVentana = (
                   SELECT MAX(v2.idVentana)
                   FROM VentanaRecepcion v2
                   WHERE v2.plantilla.idPlantilla = p.idPlantilla
               )
        ORDER BY p.idPlantilla
        """)
    List<DetallesTramiteBaseProjection> findAllDetallesBase();

    @Query("""
        SELECT
            p.idPlantilla AS idPlantilla,
            p.nombrePlantilla AS nombrePlantilla,
            p.descripcionPlantilla AS descripcionPlantilla,
            c.idCategoria AS idCategoria,
            c.nombreCategoria AS categoria,
            car.idCarrera AS idCarrera,
            car.nombreCarrera AS carrera,
            f.idFlujo AS idFlujo,
            f.nombreFlujo AS nombreFlujo,
            f.descripcion AS descripcion,
            u.idUsuario AS idUsuarioCreador,
            COALESCE(cr.nombreUsuario, CONCAT(COALESCE(u.nombres, ''), ' ', COALESCE(u.apellidos, ''))) AS usuarioCreador,
            f.version AS version,
            v.idVentana AS idVentana,
            v.fechaApertura AS fechaApertura,
            v.fechaCierre AS fechaCierre,
            v.permiteExtension AS permiteExtension,
            p.diasResolucionEstimados AS diasResolucionEstimados,
            p.estaActivo AS estaActivo,
            p.disponibleExternos AS disponibleExternos
        FROM PlantillaTramite p
        LEFT JOIN p.categoria c
        LEFT JOIN p.carrera car
        LEFT JOIN p.flujoTrabajo f
        LEFT JOIN f.creadoPor u
        LEFT JOIN u.credencial cr
        LEFT JOIN p.ventanas v
               ON v.idVentana = (
                   SELECT MAX(v2.idVentana)
                   FROM VentanaRecepcion v2
                   WHERE v2.plantilla.idPlantilla = p.idPlantilla
               )
        WHERE p.idPlantilla = :idPlantilla
        """)
    Optional<DetallesTramiteBaseProjection> findDetalleBaseByIdPlantilla(@Param("idPlantilla") Integer idPlantilla);

    @Query("""
        SELECT
            p.idPlantilla AS idPlantilla,
            p.nombrePlantilla AS nombrePlantilla,
            p.descripcionPlantilla AS descripcionPlantilla,
            c.idCategoria AS idCategoria,
            c.nombreCategoria AS categoria,
            car.idCarrera AS idCarrera,
            car.nombreCarrera AS carrera,
            f.idFlujo AS idFlujo,
            f.nombreFlujo AS nombreFlujo,
            f.descripcion AS descripcion,
            u.idUsuario AS idUsuarioCreador,
            COALESCE(cr.nombreUsuario, CONCAT(COALESCE(u.nombres, ''), ' ', COALESCE(u.apellidos, ''))) AS usuarioCreador,
            f.version AS version,
            v.idVentana AS idVentana,
            v.fechaApertura AS fechaApertura,
            v.fechaCierre AS fechaCierre,
            v.permiteExtension AS permiteExtension,
            p.diasResolucionEstimados AS diasResolucionEstimados,
            p.estaActivo AS estaActivo,
            p.disponibleExternos AS disponibleExternos
        FROM PlantillaTramite p
        LEFT JOIN p.categoria c
        JOIN p.carrera car
        LEFT JOIN p.flujoTrabajo f
        LEFT JOIN f.creadoPor u
        LEFT JOIN u.credencial cr
        LEFT JOIN p.ventanas v
               ON v.idVentana = (
                   SELECT MAX(v2.idVentana)
                   FROM VentanaRecepcion v2
                   WHERE v2.plantilla.idPlantilla = p.idPlantilla
               )
        WHERE car.idCarrera = :idCarrera
        ORDER BY p.idPlantilla
        """)
    List<DetallesTramiteBaseProjection> findAllDetallesBaseByCarrera(@Param("idCarrera") Integer idCarrera);

    @Query(value = "Select * from tramites.fn_sl_plantillastramites(:categoria, :activo, :busqueda, :esExterno, :idCarrera)", nativeQuery = true)
    List<PlantillaTramiteDTO> listarPlantillas(
            @Param("categoria") String categoria,
            @Param("activo") Boolean activo,
            @Param("busqueda") String busqueda,
            @Param("esExterno") Boolean esExterno,
            @Param("idCarrera") Integer idCarrera
    );
}
