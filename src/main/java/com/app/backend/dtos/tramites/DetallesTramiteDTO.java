package com.app.backend.dtos.tramites;

import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallesTramiteDTO {
    private Integer idPlantilla;
    private String nombrePlantilla;
    private String descripcionPlantilla;
    private Integer idCategoria;
    private String categoria;
    private Integer idFlujo;
    private String nombreFlujo;
    private String descripcion;
    private Integer idUsuarioCreador;
    private String usuarioCreador;
    private Integer version;
    private List<PasoFlujoTramiteDTO> pasosTramite;
    private List<RequisitoTramiteDTO> requisitosTramite;
    private Integer idVentana;
    private LocalDate fechaApertura;
    private LocalDate fechaCierre;
    private Boolean permiteExtension;
    private Integer diasResolucionEstimados;
    private Boolean estaActivo;
    private Boolean disponibleExternos;
}
