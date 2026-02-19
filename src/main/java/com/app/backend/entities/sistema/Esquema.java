package com.app.backend.entities.sistema;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "esquemas", schema = "sistema")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Esquema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_esquema")
    private Integer idEsquema;

    @Column(name = "nombre_esquema", nullable = false, unique = true, length = 63)
    private String nombreEsquema;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "esta_activo")
    @Builder.Default
    private Boolean estaActivo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "esquema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EsquemaRol> esquemasRoles;
}
