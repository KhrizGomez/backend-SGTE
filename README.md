# backend-SGTE

## Descripción General
backend-SGTE es una API REST desarrollada con Spring Boot 3.5 y Java 25, diseñada para servir como módulo auxiliar de un Sistema de Gestión Académica (SGA). Su función principal es gestionar y exponer los datos de los actores académicos de una institución educativa, incluyendo estudiantes, coordinadores, decanos, carrera, facultades y matrículas.

## Funcionalidades Principales
* **Gestión de Usuarios:** CRUD completo de usuarios del sistema con sus roles (estudiante, coordinador, decano).
* **Validación de Usuarios:** Permite validar si un usuario está registrado en el sistema por medio de su número de cédula, retornando la información completa de su perfil y rol académico.
* **Gestión Académica:** Administración de carreras, facultades y periodos académicos.
* **Historial de Matrículas:** Consulta y administración del historial de matrícula de los estudiantes.
* **Información por Rol:** Endpoints específicos para consultar y gestionar los perfiles de estudiantes, coordinadores y decanos.

## Tecnologías Utilizadas
| Tecnología | Versión |
| :--- | :--- |
| Java | 25 |
| Spring Boot | 3.5.12 |
| Spring Data JPA / Hibernate | 6.x |
| PostgreSQL | 17.x |
| Lombok | Edge |
| Maven | Wrapper incluido |

## Base de Datos
La aplicación se conecta a una instancia de PostgreSQL alojada en Azure (sga-auxiliar01). El esquema es gestionado externamente (`ddl-auto=validate`), lo que significa que Hibernate solo valida que las entidades coincidan con el esquema existente, sin modificarlo.

## Ejecución Local
```bash
# Compilar el proyecto
./mvnw clean package -DskipTests

# Levantar el servidor (puerto 9090)
./mvnw spring-boot:run
```
El servidor se inicia en http://localhost:9090.

## Estructura del Proyecto
```text
com.app.auxiliar
├── controllers/       # Controladores REST
├── dtos/              # Objetos de transferencia de datos
├── entities/          # Entidades JPA mapeadas a la BD
├── exceptions/        # Manejo global de excepciones
├── repositories/      # Interfaces de acceso a datos (Spring Data)
└── services/
    ├── (interfaces)
    └── implementacion/ # Implementaciones de los servicios
```
