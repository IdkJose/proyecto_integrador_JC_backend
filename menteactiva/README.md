# Mente Activa Backend

Backend project for "Mente Activa" built with **Kotlin** and **Spring Boot**, following a strict 3-layer architecture (Controller -> Service -> Repository) and 100% test coverage in the Service layer.

## Requirements

- Java 17+
- Docker & Docker Compose
- Gradle (included via wrapper)

## Project Structure

- `src/main/kotlin/com/pucetec/menteactiva/domain`: Contains all domain logic.
    - `controller`: REST Endpoints (returns DTOs only).
    - `service`: Business Logic (100% Test Coverage).
    - `repository`: Spring Data JPA Interfaces.
    - `entity`: Database Models (Category, Activity, Question).
    - `dto`: Request objects and Response objects and Mappers.

## How to Run

### 1. Start the Database
The project uses a **PostgreSQL** database running in Docker.

```bash
docker compose up -d
```
This will start the database on port `5432` (User: `admin`, Password: `password123`, DB: `menteactiva`).

### 2. Run the Application
You can run the application using Gradle:

```bash
./gradlew bootRun
```
The API will be available at `http://localhost:8080`.

### 3. Run Tests & Check Coverage
Service layer has 100% test coverage enforced.

```bash
./gradlew test jacocoTestReport
```
The coverage report will be generated at `build/reports/jacoco/test/html/index.html`.

## API Documentation

A Postman collection is included in this repository: `postman_collection.json`.

### Endpoints Overview

#### Categories (`/api/v1/categories`)
- `GET /` - List all categories.
- `GET /{id}` - Get category details.
- `POST /` - Create a new category.
- `PUT /{id}` - Update a category.
- `DELETE /{id}` - Delete a category.

#### Activities (`/api/v1/activities`)
- `GET /category/{categoryId}` - List activities by category.
- `GET /{id}` - Get activity details.
- `POST /` - Create a new activity.
- `DELETE /{id}` - Delete an activity.


## Cómo ejecutar en IntelliJ IDEA

1.  **Abrir Proyecto**:
    - Abre IntelliJ IDEA.
    - Selecciona `File > Open...` y navega a la carpeta `menteactiva`.
    - Selecciona el archivo `build.gradle.kts` y elige **"Open as Project"**.

2.  **Configurar SDK (Java)**:
    - Ve a `File > Project Structure > Project`.
    - Asegúrate de que **SDK** esté seleccionado en **Java 17** o superior (recomendado 21).

3.  **Levantar Base de Datos (Docker)**:
    - IntelliJ suele detectar el archivo `docker-compose.yml`.
    - Busca la pestaña **Services** (abajo) o haz clic derecho en el archivo `docker-compose.yml` -> **Run 'Compose: docker-compose.yml'**.
    - Verifica que el contenedor `menteactiva_db` esté en verde (running).

4.  **Ejecutar Aplicación**:
    - Abre `src/main/kotlin/com/pucetec/menteactiva/MenteactivaApplication.kt`.
    - Verás un triángulo verde ▶️ al lado de `fun main`. Haz clic y selecciona **Run 'MenteactivaApplicationKt'**.
