package com.app.backend.dtos.tramites.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequisitoTramiteResponseDTO {
    private String nombrerequisito;
    private String descripcionrequisito;
    private Boolean esobligatorio;
    private String tipodocumento;
    private String extensionespermitidas;
    private Integer numeroorden;
}
