package com.app.backend.dtos.tramites;

import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallesTramiteDTO {
    private Integer idTipoTramite;
    private String nombreTramite;
    private String descripcionTramite;
    private Integer idCategoria;
    private String categoria;
    private Integer idFlujo;
    private String nombreFlujo;
    private String descripcionFlujo;
    private Integer idUsuarioCreador;
    private String usuarioCreador;
    private Integer version;
    private List<PasoFlujoTramiteDTO> pasosTramite;
    private List<RequisitoTramiteDTO> requisitosTramite;
    private Integer idPlazo;
    private LocalDate fechaApertura;
    private LocalDate fechaCierre;
    private Boolean permiteExtension;
    private Integer diasEstimados;
    private Boolean estaActivo;
    private Boolean disponibleExternos;
}
