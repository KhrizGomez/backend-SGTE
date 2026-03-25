package com.app.backend.dtos.tramites.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolicitudesTramitesVigentesRespuestaDTO {
    private Integer idsolicitud;
    private String codigosolicitud;
    private String nombreplantilla;
    private String categoria;
    private String prioridad;
    private String estadoactual;
    private Date fechacreacion;
    private Date fechaestimado;
    private Integer etapaactual;
    private Long totaletapas;
}
