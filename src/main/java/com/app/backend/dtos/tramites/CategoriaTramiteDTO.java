package com.app.backend.dtos.tramites;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaTramiteDTO {
    private Integer idCategoria;
    private String nombreCategoria;
    private String descripcionCategoria;
    private Boolean estaActivo;
}
