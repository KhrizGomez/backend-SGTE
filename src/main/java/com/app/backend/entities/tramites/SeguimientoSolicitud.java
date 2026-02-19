package com.app.backend.entities.tramites;

import com.app.backend.entities.sistema.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguimiento_solicitud", schema = "tramites")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SeguimientoSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Integer idSeguimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paso")
    private PasoFlujo pasoFlujo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etapa")
    private EtapaProcesamiento etapaProcesamiento;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procesado_por")
    private Usuario procesadoPor;

    @Column(name = "comentarios", columnDefinition = "text")
    private String comentarios;

    @Column(name = "fecha_entrada")
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_completado")
    private LocalDateTime fechaCompletado;

    @Column(name = "sla_excedido")
    @Builder.Default
    private Boolean slaExcedido = false;
}
