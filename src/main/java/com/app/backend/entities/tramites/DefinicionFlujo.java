package com.app.backend.entities.tramites;

import com.app.backend.entities.sistema.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "definiciones_flujo", schema = "tramites")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DefinicionFlujo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_flujo")
    private Integer idFlujo;

    @Column(name = "nombre_flujo", nullable = false, length = 255)
    private String nombreFlujo;

    @Column(name = "descripcion_flujo", columnDefinition = "text")
    private String descripcionFlujo;

    @Column(name = "esta_activo")
    @Builder.Default
    private Boolean estaActivo = true;

    @Column(name = "version")
    @Builder.Default
    private Integer version = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creado_por")
    private Usuario creadoPor;

    @OneToMany(mappedBy = "definicionFlujo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasoFlujo> pasos;

    @OneToMany(mappedBy = "definicionFlujo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransicionFlujo> transiciones;

    @OneToMany(mappedBy = "definicionFlujo")
    private List<TipoTramite> tiposTramite;
}
