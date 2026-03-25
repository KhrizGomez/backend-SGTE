package com.app.backend.dtos.documentos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoAdjuntoRequestDTO {
    private Integer idRequisito;
    private String nombreArchivo;
    private String nombreOriginal;
    private String rutaArchivo;
    private Long tamanoBytes;
}
