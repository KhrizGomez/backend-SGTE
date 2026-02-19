package com.app.backend.entities.tramites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "etapas_procesamiento", schema = "tramites")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EtapaProcesamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etapa")
    private Integer idEtapa;

    @Column(name = "nombre_etapa", nullable = false, length = 255)
    private String nombreEtapa;

    @Column(name = "descripcion_etapa", columnDefinition = "text")
    private String descripcionEtapa;

    @Column(name = "codigo_etapa", unique = true, length = 50)
    private String codigoEtapa;
}
