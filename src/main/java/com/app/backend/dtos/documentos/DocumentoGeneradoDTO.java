package com.app.backend.dtos.documentos;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoGeneradoDTO {
    private Integer idGenerado;
    private Integer idSolicitud;
    private String tipoDocumento;
    private String codigoDocumento;
    private String rutaArchivo;
    private String plantillaUsada;
    private Integer generadoPorId;
    private Integer firmadoPorId;
    private String firmaDigital;
    private Boolean esOficial;
    private LocalDateTime fechaGeneracion;
    private LocalDate validoHasta;
}
