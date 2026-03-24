# backend-SGTE

**SGTE (Sistema de Gestión de Trámites Estudiantiles)** es una plataforma diseñada para automatizar, organizar y dar seguimiento a las solicitudes y trámites académicos que realizan los estudiantes dentro de la institución universitaria.

Este proyecto corresponde a la capa **Backend** del sistema, desarrollado bajo una arquitectura robusta y escalable utilizando **Java y Spring Boot**. Actúa como el motor central que gestiona la lógica de negocio, el acceso a la base de datos (PostgreSQL) y provee de APIs RESTful para la interacción con los clientes (frontend).

## Funcionalidades Principales

El backend expone puntos de acceso (endpoints) para cubrir todas las necesidades del ciclo de vida de un trámite universitario:

* **Gestión de Identidad y Acceso:** Autenticación de usuarios y administración de roles específicos (Estudiantes, Coordinadores de Carrera, Decanos, Administradores del Sistema).
* **Catálogo Académico:** Administración de datos institucionales centrales (Facultades, Carreras, Periodos/Semestres).
* **Parametrización de Trámites:** Sistema altamente configurable que permite definir Plantillas de Trámites, flujos de trabajo (workflows) personalizados, etapas de aprobación, ventanas de recepción (fechas) y requisitos documentales por cada tipo de trámite.
* **Motor de Solicitudes:** Capacidad para que los estudiantes inicien nuevas solicitudes de trámites, suban sus documentos de soporte e inicien el flujo de aprobación.
* **Seguimiento y Auditoría:** Registro detallado del historial de cambios de estado de cada solicitud, con opciones de aprobación, revisión y rechazo (con catálogo de motivos).
* **Gestión Documental:** Integración con sistemas de almacenamiento (como Azure Blob Storage o almacenamiento local) para la carga segura y validación de documentos adjuntos a los trámites.
* **Notificaciones:** Generación de alertas en tiempo real y correos electrónicos para mantener a los estudiantes y autoridades informados sobre actualizaciones en sus solicitudes.

## Tecnologías Utilizadas

* **Framework:** Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Security)
* **Base de Datos:** PostgreSQL
* **Compilación y Empaquetado:** Maven
* **Otros:** Lombok, JWT (JSON Web Tokens) para seguridad, etc.
