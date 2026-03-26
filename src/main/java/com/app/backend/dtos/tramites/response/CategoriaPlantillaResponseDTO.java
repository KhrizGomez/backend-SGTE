package com.app.backend.dtos.tramites.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaPlantillaResponseDTO {
    private Integer idCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;
    private Boolean estaActivo;
}