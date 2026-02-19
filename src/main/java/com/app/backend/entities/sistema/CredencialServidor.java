package com.app.backend.entities.sistema;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credenciales_servidor", schema = "sistema")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CredencialServidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credencial_srv")
    private Integer idCredencialSrv;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nombre_usuario_db", nullable = false, unique = true, length = 63)
    private String nombreUsuarioDb;

    @Column(name = "hash_contrasena_db", columnDefinition = "text")
    private String hashContrasenaDb;

    @Column(name = "metodo_auth", length = 50)
    @Builder.Default
    private String metodoAuth = "md5";

    @Column(name = "esta_activo")
    @Builder.Default
    private Boolean estaActivo = true;
}
