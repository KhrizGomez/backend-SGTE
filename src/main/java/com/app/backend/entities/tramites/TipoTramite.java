package com.app.backend.entities.tramites;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "tipos_tramite", schema = "tramites")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TipoTramite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_tramite")
    private Integer idTipoTramite;

    @Column(name = "nombre_tramite", nullable = false, length = 255)
    private String nombreTramite;

    @Column(name = "descripcion_tramite", columnDefinition = "text")
    private String descripcionTramite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private CategoriaTramite categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_flujo")
    private DefinicionFlujo definicionFlujo;

    @Column(name = "dias_estimados")
    @Builder.Default
    private Integer diasEstimados = 5;

    @Column(name = "esta_activo")
    @Builder.Default
    private Boolean estaActivo = true;

    @Column(name = "disponible_externos")
    @Builder.Default
    private Boolean disponibleExternos = false;

    @OneToMany(mappedBy = "tipoTramite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequisitoTramite> requisitos;

    @OneToMany(mappedBy = "tipoTramite")
    private List<PlazoTramite> plazos;

    @OneToMany(mappedBy = "tipoTramite")
    private List<Solicitud> solicitudes;
}
