package com.app.backend.dtos.externos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorreoRequestDTO {
    private String destinatario;
    private String asunto;
    private String mensaje;
}
