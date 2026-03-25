package com.app.backend.dtos.tramites.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequisitoTramiteResponseDTO {
    private Integer idRequisito;
    private Integer idTipoTramite;
    private String nombreRequisito;
    private String descripcionRequisito;
    private Boolean esObligatorio;
    private String tipoDocumento;
    private Integer tamanoMaxMb;
    private String extensionesPermitidas;
    private Integer numeroOrden;
}

