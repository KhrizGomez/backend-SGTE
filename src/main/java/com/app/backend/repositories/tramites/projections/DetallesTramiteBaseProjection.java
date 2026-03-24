package com.app.backend.repositories.tramites.projections;

import java.time.LocalDate;

public interface DetallesTramiteBaseProjection {
    Integer getIdPlantilla();
    String getNombrePlantilla();
    String getDescripcionPlantilla();
    Integer getIdCategoria();
    String getCategoria();
    Integer getIdFlujo();
    String getNombreFlujo();
    String getDescripcion();
    Integer getIdUsuarioCreador();
    String getUsuarioCreador();
    Integer getVersion();
    Integer getIdVentana();
    LocalDate getFechaApertura();
    LocalDate getFechaCierre();
    Boolean getPermiteExtension();
    Integer getDiasResolucionEstimados();
    Boolean getEstaActivo();
    Boolean getDisponibleExternos();
}