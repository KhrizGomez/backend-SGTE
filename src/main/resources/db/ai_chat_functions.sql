-- ============================================================
--  SGTE – Funciones PostgreSQL para el contexto del chatbot IA
--  Ejecutar en la BD del SGTE (sgte-db01)
--  Crea el schema 'sgte' si no existe
-- ============================================================

CREATE SCHEMA IF NOT EXISTS sgte;

-- ============================================================
-- 1. fn_get_coordinador_chat_context
--    Contexto del coordinador: solicitudes de su carrera
--    en el período académico activo.
--    Param: p_id_carrera INTEGER
-- ============================================================
CREATE OR REPLACE FUNCTION sgte.fn_get_coordinador_chat_context(p_id_carrera INTEGER)
RETURNS JSON
LANGUAGE plpgsql
AS $$
DECLARE
    v_periodo_id   INTEGER;
    v_carrera_name TEXT;
    v_result       JSON;
BEGIN
    -- Período activo
    SELECT id_periodo INTO v_periodo_id
    FROM academico.periodos
    WHERE es_periodo_actual = true AND esta_activo = true
    LIMIT 1;

    -- Nombre de la carrera
    SELECT nombre_carrera INTO v_carrera_name
    FROM academico.carreras WHERE id_carrera = p_id_carrera;

    SELECT json_build_object(

        'carrera',  v_carrera_name,
        'periodo', (SELECT nombre_periodo FROM academico.periodos WHERE id_periodo = v_periodo_id),

        -- ── Totales de solicitudes de la carrera ──────────────
        'solicitudes', (
            SELECT json_build_object(
                'total',       COUNT(*),
                'pendientes',  COUNT(*) FILTER (WHERE s.estado_actual = 'pendiente'),
                'en_revision', COUNT(*) FILTER (WHERE s.estado_actual = 'en_revision'),
                'aprobadas',   COUNT(*) FILTER (WHERE s.estado_actual = 'aprobado'),
                'rechazadas',  COUNT(*) FILTER (WHERE s.estado_actual = 'rechazado'),
                'mas_antigua_pendiente', (
                    SELECT json_build_object(
                        'codigo',        s2.codigo_solicitud,
                        'tipo',          pt2.nombre_plantilla,
                        'estudiante',    u2.nombres || ' ' || u2.apellidos,
                        'dias_espera',   EXTRACT(DAY FROM NOW() - s2.fecha_creacion)::int
                    )
                    FROM tramites.solicitudes s2
                    JOIN tramites.plantillas_tramite pt2 ON s2.id_plantilla = pt2.id_plantilla
                    JOIN sistema.usuarios u2 ON s2.id_usuario = u2.id_usuario
                    WHERE s2.id_carrera = p_id_carrera
                      AND s2.estado_actual = 'pendiente'
                    ORDER BY s2.fecha_creacion ASC
                    LIMIT 1
                )
            )
            FROM tramites.solicitudes s
            WHERE s.id_carrera = p_id_carrera
        ),

        -- ── Top tipos de trámite más solicitados ─────────────
        'top_tramites', (
            SELECT json_agg(t)
            FROM (
                SELECT
                    pt.nombre_plantilla AS tipo,
                    COUNT(*)            AS total,
                    COUNT(*) FILTER (WHERE s.estado_actual = 'pendiente') AS pendientes
                FROM tramites.solicitudes s
                JOIN tramites.plantillas_tramite pt ON s.id_plantilla = pt.id_plantilla
                WHERE s.id_carrera = p_id_carrera
                GROUP BY pt.id_plantilla, pt.nombre_plantilla
                ORDER BY COUNT(*) DESC
                LIMIT 5
            ) t
        ),

        -- ── Solicitudes con SLA excedido ──────────────────────
        'sla_excedido', (
            SELECT COUNT(*)
            FROM tramites.historial_solicitud hs
            WHERE hs.sla_excedido = true
              AND hs.id_solicitud IN (
                  SELECT id_solicitud FROM tramites.solicitudes WHERE id_carrera = p_id_carrera
              )
        ),

        -- ── Ventanas de recepción abiertas ────────────────────
        'ventanas_abiertas', (
            SELECT json_agg(v)
            FROM (
                SELECT
                    pt.nombre_plantilla AS tramite,
                    vr.fecha_apertura,
                    vr.fecha_cierre,
                    (vr.fecha_cierre - CURRENT_DATE) AS dias_restantes
                FROM tramites.ventanas_recepcion vr
                JOIN tramites.plantillas_tramite pt ON vr.id_plantilla = pt.id_plantilla
                WHERE vr.fecha_apertura <= CURRENT_DATE
                  AND vr.fecha_cierre   >= CURRENT_DATE
                  AND (pt.id_carrera = p_id_carrera OR pt.id_carrera IS NULL)
                ORDER BY vr.fecha_cierre ASC
            ) v
        ),

        -- ── Historial reciente (últimas acciones) ─────────────
        'historial_reciente', (
            SELECT json_agg(h)
            FROM (
                SELECT
                    s.codigo_solicitud,
                    pt.nombre_plantilla AS tipo,
                    hs.estado,
                    hs.tipo_accion,
                    hs.fecha_entrada
                FROM tramites.historial_solicitud hs
                JOIN tramites.solicitudes s ON hs.id_solicitud = s.id_solicitud
                JOIN tramites.plantillas_tramite pt ON s.id_plantilla = pt.id_plantilla
                WHERE s.id_carrera = p_id_carrera
                ORDER BY hs.fecha_entrada DESC
                LIMIT 5
            ) h
        )

    ) INTO v_result;

    RETURN COALESCE(v_result, '{}'::json);
END;
$$;


-- ============================================================
-- 2. fn_get_decano_chat_context
--    Contexto del decano: resumen de todas las carreras
--    de su facultad.
--    Param: p_id_facultad INTEGER
-- ============================================================
CREATE OR REPLACE FUNCTION sgte.fn_get_decano_chat_context(p_id_facultad INTEGER)
RETURNS JSON
LANGUAGE plpgsql
AS $$
DECLARE
    v_facultad_name TEXT;
    v_result        JSON;
BEGIN
    SELECT nombre_facultad INTO v_facultad_name
    FROM academico.facultades WHERE id_facultad = p_id_facultad;

    SELECT json_build_object(

        'facultad', v_facultad_name,

        -- ── Resumen global de solicitudes por carrera ─────────
        'por_carrera', (
            SELECT json_agg(pc)
            FROM (
                SELECT
                    c.nombre_carrera                AS carrera,
                    COUNT(s.id_solicitud)           AS total,
                    COUNT(*) FILTER (WHERE s.estado_actual = 'pendiente')   AS pendientes,
                    COUNT(*) FILTER (WHERE s.estado_actual = 'en_revision') AS en_revision,
                    COUNT(*) FILTER (WHERE s.estado_actual = 'aprobado')    AS aprobadas,
                    COUNT(*) FILTER (WHERE s.estado_actual = 'rechazado')   AS rechazadas
                FROM academico.carreras c
                LEFT JOIN tramites.solicitudes s ON s.id_carrera = c.id_carrera
                WHERE c.id_facultad = p_id_facultad
                GROUP BY c.id_carrera, c.nombre_carrera
                ORDER BY COUNT(s.id_solicitud) DESC
            ) pc
        ),

        -- ── Totales de la facultad ────────────────────────────
        'totales_facultad', (
            SELECT json_build_object(
                'total',       COUNT(*),
                'pendientes',  COUNT(*) FILTER (WHERE s.estado_actual = 'pendiente'),
                'en_revision', COUNT(*) FILTER (WHERE s.estado_actual = 'en_revision'),
                'aprobadas',   COUNT(*) FILTER (WHERE s.estado_actual = 'aprobado'),
                'rechazadas',  COUNT(*) FILTER (WHERE s.estado_actual = 'rechazado')
            )
            FROM tramites.solicitudes s
            JOIN academico.carreras c ON s.id_carrera = c.id_carrera
            WHERE c.id_facultad = p_id_facultad
        ),

        -- ── Top tipos de trámite en toda la facultad ──────────
        'top_tramites_facultad', (
            SELECT json_agg(t)
            FROM (
                SELECT
                    pt.nombre_plantilla AS tipo,
                    COUNT(*)            AS total
                FROM tramites.solicitudes s
                JOIN tramites.plantillas_tramite pt ON s.id_plantilla = pt.id_plantilla
                JOIN academico.carreras c ON s.id_carrera = c.id_carrera
                WHERE c.id_facultad = p_id_facultad
                GROUP BY pt.id_plantilla, pt.nombre_plantilla
                ORDER BY COUNT(*) DESC
                LIMIT 5
            ) t
        ),

        -- ── Solicitudes con SLA excedido en la facultad ───────
        'sla_excedido_facultad', (
            SELECT COUNT(*)
            FROM tramites.historial_solicitud hs
            JOIN tramites.solicitudes s ON hs.id_solicitud = s.id_solicitud
            JOIN academico.carreras c ON s.id_carrera = c.id_carrera
            WHERE hs.sla_excedido = true
              AND c.id_facultad = p_id_facultad
        ),

        -- ── Solicitudes escaladas al decanato ─────────────────
        'escaladas_decanato', (
            SELECT COUNT(*)
            FROM tramites.solicitudes s
            JOIN tramites.pasos_flujo pf ON s.paso_actual = pf.id_paso
            JOIN sistema.roles r ON pf.rol_requerido = r.id_rol
            JOIN academico.carreras c ON s.id_carrera = c.id_carrera
            WHERE c.id_facultad = p_id_facultad
              AND LOWER(r.nombre_rol) LIKE '%decano%'
              AND s.estado_actual NOT IN ('aprobado', 'rechazado')
        )

    ) INTO v_result;

    RETURN COALESCE(v_result, '{}'::json);
END;
$$;


-- ============================================================
-- 3. fn_get_estudiante_chat_context
--    Contexto del estudiante: sus solicitudes activas,
--    historial reciente y ventanas de recepción abiertas.
--    Param: p_user_id INTEGER (sistema.usuarios.id_usuario)
-- ============================================================
CREATE OR REPLACE FUNCTION sgte.fn_get_estudiante_chat_context(p_user_id INTEGER)
RETURNS JSON
LANGUAGE plpgsql
AS $$
DECLARE
    v_id_carrera INTEGER;
    v_result     JSON;
BEGIN
    SELECT id_carrera INTO v_id_carrera
    FROM academico.estudiantes WHERE id_usuario = p_user_id;

    SELECT json_build_object(

        'estudiante', (
            SELECT json_build_object(
                'nombres',          u.nombres || ' ' || u.apellidos,
                'carrera',          c.nombre_carrera,
                'semestre',         e.numero_semestre,
                'estado_academico', e.estado_academico
            )
            FROM academico.estudiantes e
            JOIN sistema.usuarios u ON e.id_usuario = u.id_usuario
            JOIN academico.carreras c ON e.id_carrera = c.id_carrera
            WHERE e.id_usuario = p_user_id
        ),

        -- ── Solicitudes activas (no finalizadas) ──────────────
        'solicitudes_activas', (
            SELECT json_agg(sa)
            FROM (
                SELECT
                    s.codigo_solicitud,
                    pt.nombre_plantilla AS tipo,
                    s.estado_actual     AS estado,
                    s.fecha_creacion,
                    s.fecha_estimada_fin,
                    s.prioridad
                FROM tramites.solicitudes s
                JOIN tramites.plantillas_tramite pt ON s.id_plantilla = pt.id_plantilla
                WHERE s.id_usuario = p_user_id
                  AND s.estado_actual NOT IN ('aprobado', 'rechazado')
                ORDER BY s.fecha_creacion DESC
            ) sa
        ),

        -- ── Historial reciente (últimas 5 finalizadas) ────────
        'historial_reciente', (
            SELECT json_agg(hr)
            FROM (
                SELECT
                    s.codigo_solicitud,
                    pt.nombre_plantilla AS tipo,
                    s.estado_actual     AS estado,
                    s.fecha_creacion,
                    s.fecha_real_fin,
                    s.resolucion
                FROM tramites.solicitudes s
                JOIN tramites.plantillas_tramite pt ON s.id_plantilla = pt.id_plantilla
                WHERE s.id_usuario = p_user_id
                  AND s.estado_actual IN ('aprobado', 'rechazado')
                ORDER BY s.fecha_real_fin DESC NULLS LAST
                LIMIT 5
            ) hr
        ),

        -- ── Ventanas de recepción abiertas para su carrera ────
        'tramites_disponibles', (
            SELECT json_agg(td)
            FROM (
                SELECT
                    pt.nombre_plantilla              AS tramite,
                    cat.nombre_categoria             AS categoria,
                    vr.fecha_cierre,
                    (vr.fecha_cierre - CURRENT_DATE) AS dias_restantes,
                    pt.dias_resolucion_estimados
                FROM tramites.ventanas_recepcion vr
                JOIN tramites.plantillas_tramite pt ON vr.id_plantilla = pt.id_plantilla
                LEFT JOIN tramites.categorias cat ON pt.id_categoria = cat.id_categoria
                WHERE vr.fecha_apertura <= CURRENT_DATE
                  AND vr.fecha_cierre   >= CURRENT_DATE
                  AND (pt.id_carrera = v_id_carrera OR pt.id_carrera IS NULL)
                  AND pt.esta_activo = true
                ORDER BY vr.fecha_cierre ASC
            ) td
        ),

        -- ── Notificaciones no leídas ──────────────────────────
        'notificaciones_pendientes', (
            SELECT COUNT(*)
            FROM sistema.notificaciones
            WHERE id_usuario = p_user_id AND esta_leida = false
        )

    ) INTO v_result;

    RETURN COALESCE(v_result, '{}'::json);
END;
$$;