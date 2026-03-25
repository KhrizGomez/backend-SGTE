package com.app.backend.dtos.tramites.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetallesTramiteResponseDTO {
    private Integer idPlantilla;
    private String nombrePlantilla;
    private String descripcionPlantilla;
    private Integer idCategoria;
    private String categoria;
    private Integer idCarrera;
    private String carrera;
    private Integer idFlujo;
    private String nombreFlujo;
    private String descripcion;
    private Integer idUsuarioCreador;
    private String usuarioCreador;
    private Integer version;
    private List<PasoFlujoTramiteResponseDTO> pasosTramite;
    private List<RequisitoTramiteResponseDTO> requisitosTramite;
    private Integer idVentana;
    private LocalDate fechaApertura;
    private LocalDate fechaCierre;
    private Boolean permiteExtension;
    private Integer diasResolucionEstimados;
    private Boolean estaActivo;
    private Boolean disponibleExternos;
}

