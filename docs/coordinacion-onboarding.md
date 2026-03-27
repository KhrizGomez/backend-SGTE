# Onboarding Tecnico - Modulo de Coordinacion (SGTE)

## 1. Alcance
Este documento resume el modulo de coordinacion centrado en tramites y documentos dentro de backend-SGTE.

Capas cubiertas:
- Controllers de tramites
- Services e impl de tramites
- Repositories de tramites
- Entidades y DTOs de tramites
- Servicios, repositorios, entidades y DTOs de documentos ligados a solicitudes

## 2. Mapa Rapido de Arquitectura
Patron principal:
Controller -> Service (interface) -> ServiceImpl -> Repository -> Entity

Excepciones:
- Algunas consultas de listados complejos usan proyecciones DTO en repositorio.
- Existe una funcion SQL legacy para listados de plantillas en PlantillaTramiteRepository.

## 3. Flujo Principal: Solicitudes
Entrada API:
- POST /api/solicitudes (multipart)
- POST /api/solicitudes/crear (multipart)
- POST /api/solicitudes/aprobar
- POST /api/solicitudes/rechazar
- GET /api/solicitudes/mis-solicitudes
- GET /api/solicitudes/{id}/detalle
- GET /api/solicitudes/por-rol/{nombreRol}

Archivo clave:
- src/main/java/com/app/backend/controllers/tramites/SolicitudTramiteController.java

Orquestacion de negocio:
- src/main/java/com/app/backend/services/tramites/impl/SolicitudServiceImpl.java

Secuencia de creacion de solicitud:
1. Extrae identidad del JWT (idUsuario, idCarrera).
2. Resuelve plantilla, usuario y primer paso del flujo.
3. Genera codigo de solicitud y persiste Solicitud.
4. Si hay archivos, sube a almacenamiento y persiste DocumentoAdjunto.
5. Dispara notificaciones (estudiante y gestor del primer paso).

Secuencia de aprobacion/rechazo:
1. Valida usuario autenticado y estado actual de solicitud.
2. Verifica rol requerido para el paso.
3. Registra HistorialSolicitud.
4. Aprobacion: avanza al siguiente paso o finaliza.
5. Rechazo: marca estado rechazado y registra Rechazo.
6. Dispara notificaciones segun evento.

## 4. Gestion de Plantillas y Flujos
Controllers:
- src/main/java/com/app/backend/controllers/tramites/PlantillaTramiteController.java
- src/main/java/com/app/backend/controllers/tramites/FlujoTrabajoController.java
- src/main/java/com/app/backend/controllers/tramites/CategoriaTramiteController.java
- src/main/java/com/app/backend/controllers/tramites/EtapaProcesamientoController.java

Servicios principales:
- TipoTramiteServiceImpl: alta/edicion/listado/eliminacion funcional de plantillas.
- GestionFlujoServiceImpl: alta y consulta de flujo completo (flujo + pasos).
- DetallesTramiteServiceImpl: armado de detalle expandido de plantillas.

Repositorio importante:
- PlantillaTramiteRepository (proyecciones DTO y listado filtrado).

Nota:
- En PlantillaTramiteRepository persiste una funcion SQL legacy para listar plantillas filtradas.

## 5. Documentos Ligados al Tramite
Servicios:
- src/main/java/com/app/backend/services/documentos/impl/DocumentoAdjuntoServiceImpl.java
- src/main/java/com/app/backend/services/documentos/impl/DocumentoGeneradoServiceImpl.java

Repositorios:
- src/main/java/com/app/backend/repositories/documentos/DocumentoAdjuntoRepository.java
- src/main/java/com/app/backend/repositories/documentos/DocumentoGeneradoRepository.java

Entidades:
- src/main/java/com/app/backend/entities/documentos/DocumentoAdjunto.java
- src/main/java/com/app/backend/entities/documentos/DocumentoGenerado.java

## 6. Seguridad y Contexto de Usuario
Piezas clave:
- src/main/java/com/app/backend/config/FiltroAutenticacionJwt.java
- src/main/java/com/app/backend/services/externos/impl/JwtServiceImpl.java
- src/main/java/com/app/backend/services/sistema/impl/AutenticacionServiceImpl.java

Claims usados en negocio:
- idUsuario
- idRol
- idCarrera
- idFacultad

## 7. Entidades Nucleo de Tramites
- Solicitud
- PlantillaTramite
- FlujoTrabajo
- PasoFlujo
- HistorialSolicitud
- Rechazo
- RequisitoPlantilla
- VentanaRecepcion
- TransicionFlujo
- MotivoRechazo
- Categoria
- Etapa

Ubicacion:
- src/main/java/com/app/backend/entities/tramites

## 8. Manejo de Errores
Manejador global:
- src/main/java/com/app/backend/exceptions/GlobalExceptionHandler.java

Excepciones de negocio frecuentes:
- RecursoNoEncontradoException
- SolicitudAdjuntosInvalidosException

## 9. Checklist para Nuevo Desarrollador
1. Revisar primero SolicitudTramiteController y SolicitudServiceImpl.
2. Seguir con PlantillaTramiteController + TipoTramiteServiceImpl.
3. Entender flujo y pasos en GestionFlujoServiceImpl.
4. Revisar notificaciones en NotificacionTramiteServiceImpl.
5. Validar entidades y DTOs de tramites/documentos para comprender contratos de API.

## 10. Estado de Documentacion
Los paquetes de coordinacion (tramites + documentos relacionados) fueron comentados a nivel clase/contrato para acelerar onboarding.