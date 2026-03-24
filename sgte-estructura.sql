-- ============================================================================
-- SGTE - Sistema de Gestión de Trámites Estudiantiles
-- Base de Datos Reestructurada v2
-- ============================================================================
-- RESUMEN DE CAMBIOS REALIZADOS:
--
-- ═══════════════════════════════════════════════════════════════════════════
-- ESQUEMA: sistema (LIMPIEZA DE TABLAS DE SERVIDOR)
-- ═══════════════════════════════════════════════════════════════════════════
--
-- ELIMINADAS (6 tablas):
--   ✗ sistema.credenciales_servidor     → Credenciales de conexión PostgreSQL por usuario
--   ✗ sistema.roles_servidor            → Roles de PostgreSQL (rol_estudiante, etc.)
--   ✗ sistema.usuarios_roles_servidor   → Asignación usuario ↔ rol PostgreSQL
--   ✗ sistema.esquemas                  → Catálogo de esquemas de la BD
--   ✗ sistema.esquemas_roles            → Permisos de roles_servidor sobre esquemas
--   ✗ sistema.permisos_servidor         → Privilegios granulares (SELECT, INSERT, etc.)
--
-- CONSERVADAS (6 tablas):
--   ✓ sistema.usuarios                  → Sin cambios
--   ✓ sistema.credenciales              → Sin cambios
--   ✓ sistema.roles                     → Se quita es_rol_sistema (ya no aplica)
--   ✓ sistema.usuarios_roles            → Sin cambios
--   ✓ sistema.configuraciones_usuario   → Sin cambios
--   ✓ sistema.tipos_notificacion        → Sin cambios
--   ✓ sistema.notificaciones            → Sin cambios
--
-- ═══════════════════════════════════════════════════════════════════════════
-- ESQUEMA: academico (CAMPOS AÑADIDOS PARA COMPATIBILIDAD CON SGA)
-- ═══════════════════════════════════════════════════════════════════════════
--
-- academico.facultades:
--   + codigo_facultad VARCHAR(20)        ← SGA tiene este campo y es útil para mapeo
--   + telefono_oficina VARCHAR(20)       ← SGA lo tiene
--
-- academico.carreras:
--   + duracion_semestres INTEGER         ← SGA lo tiene (DEFAULT 10)
--   + modalidad VARCHAR(50)              ← SGA lo tiene (DEFAULT 'Presencial')
--
-- academico.periodos:
--   + es_periodo_actual BOOLEAN          ← SGA lo tiene, útil para queries rápidas
--
-- academico.estudiantes:
--   + numero_matricula VARCHAR(50)       ← SGA: campo clave de identidad estudiantil
--   + jornada VARCHAR(20)                ← SGA: Matutina/Vespertina/Nocturna
--   + promedio_general NUMERIC(4,2)      ← SGA: necesario para validar requisitos de trámites
--   + creditos_aprobados INTEGER         ← SGA: necesario para validar requisitos
--   + es_becado BOOLEAN                  ← SGA: flag útil para trámites de becas
--
-- academico.coordinadores:
--   + extension_telefonica VARCHAR(20)   ← SGA lo tiene
--   + fecha_fin_periodo DATE             ← SGA lo tiene, útil para saber vigencia
--
-- academico.decanos:
--   + fecha_fin_periodo DATE             ← SGA lo tiene
--   + resolucion_nombramiento VARCHAR(100) ← SGA lo tiene, respaldo legal
--
-- ═══════════════════════════════════════════════════════════════════════════
-- ESQUEMA: tramites (RENOMBRAMIENTOS PARA CLARIDAD)
-- ═══════════════════════════════════════════════════════════════════════════
--
-- RENOMBRAMIENTOS:
--   tipos_tramite           → plantillas_tramite
--     Razón: No es un "tipo" genérico, es la PLANTILLA/DEFINICIÓN completa
--     de un trámite con sus reglas, flujo asignado y días estimados.
--     "Tipo" sugiere solo una categoría, "plantilla" refleja que es la
--     definición operativa del trámite.
--     Campos renombrados internamente:
--       id_tipo_tramite     → id_plantilla
--       nombre_tramite      → nombre_plantilla
--       descripcion_tramite → descripcion_plantilla
--
--   definiciones_flujo      → flujos_trabajo
--     Razón: "Definiciones de flujo" es redundante y abstracto.
--     "Flujo de trabajo" (workflow) es el término estándar.
--     Campos renombrados:
--       id_flujo            → id_flujo  (se mantiene)
--       nombre_flujo        → nombre_flujo (se mantiene)
--       descripcion_flujo   → descripcion (simplificado)
--
--   etapas_procesamiento    → etapas
--     Razón: "Procesamiento" es innecesario, el contexto ya lo da.
--     Campos renombrados:
--       descripcion_etapa   → descripcion
--       codigo_etapa        → codigo
--
--   seguimiento_solicitud   → historial_solicitud
--     Razón: "Seguimiento" sugiere algo en curso. "Historial" refleja
--     mejor que es el LOG de todos los estados por los que pasó.
--
--   requisitos_tramite      → requisitos_plantilla
--     Razón: Coherente con el rename de tipos_tramite → plantillas_tramite.
--     El FK ahora apunta a plantillas_tramite.
--
--   plazos_tramite          → ventanas_recepcion
--     Razón: "Plazo de trámite" es ambiguo (¿plazo de resolución? ¿de
--     entrega?). "Ventana de recepción" deja claro que es el PERÍODO
--     en que se pueden crear solicitudes de ese tipo.
--
--   rechazos_solicitud      → rechazos  (simplificado, el schema da contexto)
--   motivos_rechazo         → motivos_rechazo (se mantiene, ya es claro)
--   categorias_tramite      → categorias (simplificado)
--
-- SIN CAMBIOS:
--   solicitudes             → solicitudes (ya es claro: lo que crea el estudiante)
--   pasos_flujo             → pasos_flujo (claro en contexto)
--   transiciones_flujo      → transiciones_flujo (claro en contexto)
--
-- ═══════════════════════════════════════════════════════════════════════════
-- ESQUEMA: documentos (SIN CAMBIOS ESTRUCTURALES)
-- ═══════════════════════════════════════════════════════════════════════════
--   documentos_adjuntos     → Sin cambios
--   documentos_generados    → Sin cambios
--
-- ═══════════════════════════════════════════════════════════════════════════
-- MEJORAS ADICIONALES RECOMENDADAS (APLICADAS)
-- ═══════════════════════════════════════════════════════════════════════════
--
-- 1. solicitudes: añadido fecha_creacion TIMESTAMP DEFAULT now()
--    → La tabla original no registraba cuándo se creó la solicitud.
--
-- 2. plantillas_tramite: renombrado dias_estimados → dias_resolucion_estimados
--    → Más explícito sobre qué estima (no es plazo de entrega).
--
-- 3. ventanas_recepcion: añadido id_periodo FK
--    → Vincula la ventana al periodo académico, evita fechas sueltas.
--
-- 4. roles: eliminado es_rol_sistema (era para roles_servidor, ya no aplica).
--
-- 5. historial_solicitud: añadido tipo_accion VARCHAR(30)
--    → Permite diferenciar: 'avance', 'devolucion', 'observacion', 'rechazo'
--    sin depender solo del campo estado.
--
-- ============================================================================


-- ============================================================================
-- SCHEMAS
-- ============================================================================
CREATE SCHEMA IF NOT EXISTS academico;
COMMENT ON SCHEMA academico IS 'Estructura institucional y perfiles académicos';

CREATE SCHEMA IF NOT EXISTS documentos;
COMMENT ON SCHEMA documentos IS 'Gestión de archivos adjuntos y documentos generados';

CREATE SCHEMA IF NOT EXISTS sistema;
COMMENT ON SCHEMA sistema IS 'Autenticación, autorización, configuración y notificaciones';

CREATE SCHEMA IF NOT EXISTS tramites;
COMMENT ON SCHEMA tramites IS 'Ciclo de vida de trámites, flujos y solicitudes';


-- ============================================================================
-- ESQUEMA: sistema
-- ============================================================================

-- ── usuarios ──
CREATE TABLE sistema.usuarios (
    id_usuario              INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cedula                  VARCHAR(20)  NOT NULL UNIQUE,
    nombres                 VARCHAR(255) NOT NULL,
    apellidos               VARCHAR(255) NOT NULL,
    correo_personal         VARCHAR(255),
    correo_institucional    VARCHAR(255) NOT NULL UNIQUE,
    telefono                VARCHAR(20),
    fecha_nacimiento        DATE,
    genero                  VARCHAR(20),
    direccion               TEXT,
    estado                  BOOLEAN DEFAULT true
);

CREATE INDEX idx_usuarios_cedula       ON sistema.usuarios (cedula);
CREATE INDEX idx_usuarios_correo_inst  ON sistema.usuarios (correo_institucional);

-- ── credenciales ──
CREATE TABLE sistema.credenciales (
    id_credencial   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario      INTEGER NOT NULL UNIQUE REFERENCES sistema.usuarios(id_usuario) ON DELETE CASCADE,
    nombre_usuario  VARCHAR(25),
    hash_contrasena TEXT NOT NULL,
    estado          BOOLEAN DEFAULT true
);

-- ── roles ──
CREATE TABLE sistema.roles (
    id_rol              INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_rol          VARCHAR(100) NOT NULL UNIQUE,
    descripcion_rol     TEXT,
    nivel_jerarquico    INTEGER DEFAULT 0
);

-- ── usuarios_roles ──
CREATE TABLE sistema.usuarios_roles (
    id_usuario  INTEGER NOT NULL REFERENCES sistema.usuarios(id_usuario) ON DELETE CASCADE,
    id_rol      INTEGER NOT NULL REFERENCES sistema.roles(id_rol) ON DELETE CASCADE,
    PRIMARY KEY (id_usuario, id_rol)
);

CREATE INDEX idx_usuarios_roles_usuario ON sistema.usuarios_roles (id_usuario);
CREATE INDEX idx_usuarios_roles_rol     ON sistema.usuarios_roles (id_rol);

-- ── configuraciones_usuario ──
CREATE TABLE sistema.configuraciones_usuario (
    id_configuracion        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario              INTEGER NOT NULL UNIQUE REFERENCES sistema.usuarios(id_usuario) ON DELETE CASCADE,
    ruta_foto_perfil        VARCHAR(500),
    ruta_firma_escaneada    VARCHAR(500),
    notificar_sms           BOOLEAN DEFAULT false,
    notificar_email         BOOLEAN DEFAULT true,
    notificar_whatsapp      BOOLEAN DEFAULT false,
    notificar_push          BOOLEAN DEFAULT true,
    idioma                  VARCHAR(10) DEFAULT 'es',
    zona_horaria            VARCHAR(50) DEFAULT 'America/Guayaquil'
);

-- ── tipos_notificacion ──
CREATE TABLE sistema.tipos_notificacion (
    id_tipo         INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo_tipo     VARCHAR(50) NOT NULL UNIQUE,
    nombre_tipo     VARCHAR(100) NOT NULL,
    plantilla_defecto TEXT,
    esta_activo     BOOLEAN DEFAULT true
);

-- ── notificaciones ──
CREATE TABLE sistema.notificaciones (
    id_notificacion     INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_tipo             INTEGER NOT NULL REFERENCES sistema.tipos_notificacion(id_tipo),
    id_usuario          INTEGER NOT NULL REFERENCES sistema.usuarios(id_usuario),
    id_solicitud        INTEGER,  -- FK se agrega después de crear tramites.solicitudes
    titulo              VARCHAR(255) NOT NULL,
    mensaje             TEXT NOT NULL,
    canal               VARCHAR(50),
    esta_leida          BOOLEAN DEFAULT false,
    fecha_lectura       TIMESTAMP,
    esta_enviada        BOOLEAN DEFAULT false,
    fecha_envio         TIMESTAMP,
    error_envio         TEXT,
    programada_para     TIMESTAMP
);

CREATE INDEX idx_notificaciones_usuario    ON sistema.notificaciones (id_usuario);
CREATE INDEX idx_notificaciones_leida      ON sistema.notificaciones (id_usuario, esta_leida);
CREATE INDEX idx_notificaciones_solicitud  ON sistema.notificaciones (id_solicitud);


-- ============================================================================
-- ESQUEMA: academico
-- ============================================================================

-- ── universidades ──
CREATE TABLE academico.universidades (
    id_universidad      INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_universidad  VARCHAR(255) NOT NULL,
    es_publica          BOOLEAN NOT NULL DEFAULT true
);

-- ── facultades ──  (+ codigo_facultad, telefono_oficina desde SGA)
CREATE TABLE academico.facultades (
    id_facultad         INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_facultad     VARCHAR(255) NOT NULL UNIQUE,
    codigo_facultad     VARCHAR(20),          -- ← NUEVO: desde SGA auxiliar
    ubicacion_oficina   VARCHAR(255),
    telefono_oficina    VARCHAR(20),           -- ← NUEVO: desde SGA auxiliar
    email_facultad      VARCHAR(255),
    id_universidad      INTEGER NOT NULL REFERENCES academico.universidades(id_universidad)
                            ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE INDEX idx_facultades_universidad ON academico.facultades (id_universidad);

-- ── carreras ──  (+ duracion_semestres, modalidad desde SGA)
CREATE TABLE academico.carreras (
    id_carrera          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_carrera      VARCHAR(255) NOT NULL,
    codigo_carrera      VARCHAR(50) UNIQUE,
    id_facultad         INTEGER NOT NULL REFERENCES academico.facultades(id_facultad)
                            ON UPDATE CASCADE ON DELETE RESTRICT,
    duracion_semestres  INTEGER DEFAULT 10,    -- ← NUEVO: desde SGA auxiliar
    modalidad           VARCHAR(50) DEFAULT 'Presencial'  -- ← NUEVO: desde SGA auxiliar
);

CREATE INDEX idx_carreras_facultad ON academico.carreras (id_facultad);

-- ── periodos ──  (+ es_periodo_actual desde SGA)
CREATE TABLE academico.periodos (
    id_periodo         INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo_periodo      VARCHAR(50) NOT NULL UNIQUE,
    nombre_periodo      VARCHAR(100),
    fecha_inicio        DATE NOT NULL,
    fecha_fin           DATE NOT NULL,
    esta_activo         BOOLEAN DEFAULT true,
    es_periodo_actual   BOOLEAN DEFAULT false  -- ← NUEVO: desde SGA auxiliar
);

-- ── estudiantes ──  (+ numero_matricula, jornada, promedio_general, creditos_aprobados, es_becado)
CREATE TABLE academico.estudiantes (
    id_estudiante           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario              INTEGER NOT NULL UNIQUE REFERENCES sistema.usuarios(id_usuario) ON DELETE CASCADE,
    id_carrera              INTEGER NOT NULL REFERENCES academico.carreras(id_carrera),
    id_periodo             INTEGER REFERENCES academico.periodos(id_periodo),
    numero_semestre         INTEGER,                   -- ← NUEVO: nivel cursando (1, 2, 3...)
    numero_matricula        VARCHAR(50),           -- ← NUEVO: desde SGA (MAT-SOFT-1, etc.)
    paralelo                VARCHAR(1),
    jornada                 VARCHAR(20) DEFAULT 'Matutina',  -- ← NUEVO: desde SGA
    estado_academico        VARCHAR(50) DEFAULT 'Regular',
    promedio_general        NUMERIC(4,2),          -- ← NUEVO: desde SGA (útil para validar requisitos)
    creditos_aprobados      INTEGER DEFAULT 0,     -- ← NUEVO: desde SGA
    es_becado               BOOLEAN DEFAULT false, -- ← NUEVO: desde SGA (útil para trámites de becas)
    fecha_matricula         DATE,
    es_externo              BOOLEAN DEFAULT false,
    id_estudiante_sga       INTEGER,               -- FK lógico al SGA auxiliar
    ultima_sincronizacion   TIMESTAMP
);

CREATE INDEX idx_estudiantes_usuario  ON academico.estudiantes (id_usuario);
CREATE INDEX idx_estudiantes_carrera  ON academico.estudiantes (id_carrera);
CREATE INDEX idx_estudiantes_sga      ON academico.estudiantes (id_estudiante_sga);

-- ── coordinadores ──  (+ extension_telefonica, fecha_fin_periodo)
CREATE TABLE academico.coordinadores (
    id_coordinador          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario              INTEGER NOT NULL UNIQUE REFERENCES sistema.usuarios(id_usuario) ON DELETE CASCADE,
    id_carrera              INTEGER REFERENCES academico.carreras(id_carrera),
    ubicacion_oficina       VARCHAR(255),
    horario_atencion        VARCHAR(255),
    extension_telefonica    VARCHAR(20),            -- ← NUEVO: desde SGA auxiliar
    esta_activo             BOOLEAN DEFAULT true,
    fecha_nombramiento      DATE,
    fecha_fin_periodo       DATE,                   -- ← NUEVO: desde SGA auxiliar
    id_coordinador_sga      INTEGER
);

CREATE INDEX idx_coordinadores_usuario  ON academico.coordinadores (id_usuario);
CREATE INDEX idx_coordinadores_carrera  ON academico.coordinadores (id_carrera);

-- ── decanos ──  (+ fecha_fin_periodo, resolucion_nombramiento)
CREATE TABLE academico.decanos (
    id_decano               INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario              INTEGER NOT NULL UNIQUE REFERENCES sistema.usuarios(id_usuario) ON DELETE CASCADE,
    id_facultad             INTEGER REFERENCES academico.facultades(id_facultad),
    fecha_nombramiento      TIMESTAMP,
    fecha_fin_periodo       DATE,                   -- ← NUEVO: desde SGA auxiliar
    resolucion_nombramiento VARCHAR(100),            -- ← NUEVO: desde SGA auxiliar
    esta_activo             BOOLEAN DEFAULT true,
    id_decano_sga           INTEGER
);

CREATE INDEX idx_decanos_usuario   ON academico.decanos (id_usuario);
CREATE INDEX idx_decanos_facultad  ON academico.decanos (id_facultad);


-- ============================================================================
-- ESQUEMA: tramites
-- ============================================================================

-- ── categorias ──  (antes: categorias_tramite)
CREATE TABLE tramites.categorias (
    id_categoria            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_categoria        VARCHAR(255) NOT NULL,
    descripcion_categoria   TEXT,
    esta_activo             BOOLEAN DEFAULT true
);

-- ── flujos_trabajo ──  (antes: definiciones_flujo)
CREATE TABLE tramites.flujos_trabajo (
    id_flujo        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_flujo    VARCHAR(255) NOT NULL,
    descripcion     TEXT,                      -- ← simplificado de descripcion_flujo
    esta_activo     BOOLEAN DEFAULT true,
    version         INTEGER DEFAULT 1,
    creado_por      INTEGER REFERENCES sistema.usuarios(id_usuario)
);

-- ── etapas ──  (antes: etapas_procesamiento)
CREATE TABLE tramites.etapas (
    id_etapa        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_etapa    VARCHAR(255) NOT NULL,
    descripcion     TEXT,                      -- ← simplificado de descripcion_etapa
    codigo          VARCHAR(50) UNIQUE         -- ← simplificado de codigo_etapa
);

-- ── pasos_flujo ──
CREATE TABLE tramites.pasos_flujo (
    id_paso                 INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_flujo                INTEGER NOT NULL REFERENCES tramites.flujos_trabajo(id_flujo) ON DELETE CASCADE,
    id_etapa                INTEGER NOT NULL REFERENCES tramites.etapas(id_etapa),
    orden_paso              INTEGER NOT NULL,
    rol_requerido           INTEGER REFERENCES sistema.roles(id_rol),
    id_usuario_encargado    INTEGER REFERENCES sistema.usuarios(id_usuario),
    horas_sla               INTEGER,
    UNIQUE (id_flujo, orden_paso)
);

-- ── transiciones_flujo ──
CREATE TABLE tramites.transiciones_flujo (
    id_transicion       INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_flujo            INTEGER NOT NULL REFERENCES tramites.flujos_trabajo(id_flujo) ON DELETE CASCADE,
    id_paso_origen      INTEGER REFERENCES tramites.pasos_flujo(id_paso),
    id_paso_destino     INTEGER NOT NULL REFERENCES tramites.pasos_flujo(id_paso),
    observacion         BOOLEAN DEFAULT false,
    documento_generado  BOOLEAN DEFAULT false
);

-- ── plantillas_tramite ──  (antes: tipos_tramite)
CREATE TABLE tramites.plantillas_tramite (
    id_plantilla                INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_plantilla            VARCHAR(255) NOT NULL,     -- ← antes nombre_tramite
    descripcion_plantilla       TEXT,                       -- ← antes descripcion_tramite
    id_categoria                INTEGER REFERENCES tramites.categorias(id_categoria),
    id_flujo                    INTEGER REFERENCES tramites.flujos_trabajo(id_flujo),
    id_carrera                  INTEGER REFERENCES academico.carreras(id_carrera),  -- NULL = todas las carreras
    dias_resolucion_estimados   INTEGER DEFAULT 5,          -- ← antes dias_estimados
    esta_activo                 BOOLEAN DEFAULT true,
    disponible_externos         BOOLEAN DEFAULT false
);

-- ── requisitos_plantilla ──  (antes: requisitos_tramite)
CREATE TABLE tramites.requisitos_plantilla (
    id_requisito            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_plantilla            INTEGER NOT NULL REFERENCES tramites.plantillas_tramite(id_plantilla) ON DELETE CASCADE,
    nombre_requisito        VARCHAR(255) NOT NULL,
    descripcion_requisito   TEXT,
    es_obligatorio          BOOLEAN DEFAULT true,
    tipo_documento          VARCHAR(100),
    tamano_max_mb           INTEGER DEFAULT 10,
    extensiones_permitidas  VARCHAR(255) DEFAULT 'pdf,jpg,png',
    numero_orden            INTEGER DEFAULT 0
);

-- ── ventanas_recepcion ──  (antes: plazos_tramite)
CREATE TABLE tramites.ventanas_recepcion (
    id_ventana          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_plantilla        INTEGER REFERENCES tramites.plantillas_tramite(id_plantilla),
    id_periodo         INTEGER REFERENCES academico.periodos(id_periodo),  -- ← NUEVO: vínculo al periodo
    fecha_apertura      DATE NOT NULL,
    fecha_cierre        DATE NOT NULL,
    permite_extension   BOOLEAN DEFAULT false,
    dias_max_extension  INTEGER DEFAULT 0
);

-- ── motivos_rechazo ──
CREATE TABLE tramites.motivos_rechazo (
    id_motivo           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo_motivo       VARCHAR(50) UNIQUE,
    nombre_motivo       VARCHAR(255) NOT NULL,
    descripcion_motivo  TEXT,
    id_categoria        INTEGER REFERENCES tramites.categorias(id_categoria),
    esta_activo         BOOLEAN DEFAULT true
);

-- ── solicitudes ──  (+ fecha_creacion)
CREATE TABLE tramites.solicitudes (
    id_solicitud            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo_solicitud        VARCHAR(50) UNIQUE,
    id_plantilla            INTEGER NOT NULL REFERENCES tramites.plantillas_tramite(id_plantilla),
    id_usuario              INTEGER NOT NULL REFERENCES sistema.usuarios(id_usuario),
    id_carrera              INTEGER REFERENCES academico.carreras(id_carrera),
    creado_por              INTEGER REFERENCES sistema.usuarios(id_usuario),
    detalles_solicitud      TEXT,
    prioridad               VARCHAR(20) DEFAULT 'normal',
    paso_actual             INTEGER REFERENCES tramites.pasos_flujo(id_paso),
    estado_actual           VARCHAR(50) DEFAULT 'pendiente',
    fecha_creacion          TIMESTAMP DEFAULT now(),       -- ← NUEVO: antes no existía
    fecha_estimada_fin      DATE,
    fecha_real_fin          TIMESTAMP,
    resolucion              TEXT
);

CREATE INDEX idx_solicitudes_codigo   ON tramites.solicitudes (codigo_solicitud);
CREATE INDEX idx_solicitudes_estado   ON tramites.solicitudes (estado_actual);
CREATE INDEX idx_solicitudes_tipo     ON tramites.solicitudes (id_plantilla);
CREATE INDEX idx_solicitudes_usuario  ON tramites.solicitudes (id_usuario);

-- ── historial_solicitud ──  (antes: seguimiento_solicitud, + tipo_accion)
CREATE TABLE tramites.historial_solicitud (
    id_historial        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_solicitud        INTEGER NOT NULL REFERENCES tramites.solicitudes(id_solicitud) ON DELETE CASCADE,
    id_paso             INTEGER REFERENCES tramites.pasos_flujo(id_paso),
    id_etapa            INTEGER REFERENCES tramites.etapas(id_etapa),
    estado              VARCHAR(50) NOT NULL,
    tipo_accion         VARCHAR(30),               -- ← NUEVO: 'avance','devolucion','observacion','rechazo'
    procesado_por       INTEGER REFERENCES sistema.usuarios(id_usuario),
    comentarios         TEXT,
    fecha_entrada       TIMESTAMP DEFAULT now(),
    fecha_completado    TIMESTAMP,
    sla_excedido        BOOLEAN DEFAULT false
);

CREATE INDEX idx_historial_solicitud ON tramites.historial_solicitud (id_solicitud);

-- ── rechazos ──  (antes: rechazos_solicitud)
CREATE TABLE tramites.rechazos (
    id_rechazo              INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_solicitud            INTEGER NOT NULL REFERENCES tramites.solicitudes(id_solicitud),
    id_motivo               INTEGER NOT NULL REFERENCES tramites.motivos_rechazo(id_motivo),
    rechazado_por           INTEGER NOT NULL REFERENCES sistema.usuarios(id_usuario),
    comentarios             TEXT NOT NULL,
    fecha_rechazo           TIMESTAMP DEFAULT now(),
    notificacion_enviada    BOOLEAN DEFAULT false,
    fecha_notificacion      TIMESTAMP
);


-- ============================================================================
-- ESQUEMA: documentos
-- ============================================================================

CREATE TABLE documentos.documentos_adjuntos (
    id_documento        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_solicitud        INTEGER NOT NULL REFERENCES tramites.solicitudes(id_solicitud) ON DELETE CASCADE,
    id_requisito        INTEGER REFERENCES tramites.requisitos_plantilla(id_requisito),
    nombre_archivo      VARCHAR(255) NOT NULL,
    nombre_original     VARCHAR(255),
    ruta_archivo        VARCHAR(500) NOT NULL,
    tamano_bytes        BIGINT,
    tipo_mime           VARCHAR(100),
    checksum            VARCHAR(64),
    es_valido           BOOLEAN,
    mensaje_validacion  TEXT,
    subido_por          INTEGER REFERENCES sistema.usuarios(id_usuario),
    fecha_subida        TIMESTAMP DEFAULT now()
);

CREATE INDEX idx_documentos_solicitud ON documentos.documentos_adjuntos (id_solicitud);

CREATE TABLE documentos.documentos_generados (
    id_generado         INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_solicitud        INTEGER NOT NULL REFERENCES tramites.solicitudes(id_solicitud),
    tipo_documento      VARCHAR(100) NOT NULL,
    codigo_documento    VARCHAR(100) UNIQUE,
    ruta_archivo        VARCHAR(500) NOT NULL,
    plantilla_usada     VARCHAR(255),
    generado_por        INTEGER REFERENCES sistema.usuarios(id_usuario),
    firmado_por         INTEGER REFERENCES sistema.usuarios(id_usuario),
    firma_digital       TEXT,
    es_oficial          BOOLEAN DEFAULT false,
    fecha_generacion    TIMESTAMP DEFAULT now(),
    valido_hasta        DATE
);


-- ============================================================================
-- FK DIFERIDA: notificaciones → solicitudes
-- ============================================================================
ALTER TABLE sistema.notificaciones
    ADD CONSTRAINT notificaciones_id_solicitud_fkey
    FOREIGN KEY (id_solicitud) REFERENCES tramites.solicitudes(id_solicitud);
