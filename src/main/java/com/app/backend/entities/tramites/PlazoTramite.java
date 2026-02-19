package com.app.backend.entities.tramites;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "plazos_tramite", schema = "tramites")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PlazoTramite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plazo")
    private Integer idPlazo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_tramite")
    private TipoTramite tipoTramite;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDate fechaApertura;

    @Column(name = "fecha_cierre", nullable = false)
    private LocalDate fechaCierre;

    @Column(name = "permite_extension")
    @Builder.Default
    private Boolean permiteExtension = false;

    @Column(name = "dias_max_extension")
    @Builder.Default
    private Integer diasMaxExtension = 0;
}
