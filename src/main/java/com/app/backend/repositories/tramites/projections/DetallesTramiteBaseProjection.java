package com.app.backend.repositories.tramites.projections;

import java.time.LocalDate;

public interface DetallesTramiteBaseProjection {
    Integer getIdTipoTramite();
    String getNombreTramite();
    String getDescripcionTramite();
    Integer getIdCategoria();
    String getCategoria();
    Integer getIdFlujo();
    String getNombreFlujo();
    String getDescripcionFlujo();
    Integer getIdUsuarioCreador();
    String getUsuarioCreador();
    Integer getVersion();
    Integer getIdPlazo();
    LocalDate getFechaApertura();
    LocalDate getFechaCierre();
    Boolean getPermiteExtension();
    Integer getDiasEstimados();
    Boolean getEstaActivo();
    Boolean getDisponibleExternos();
}