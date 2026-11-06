# Sistema de Gestión de Delivery - Microservicios Spring Boot

**Estudiante:** Joel Facundo Quijada Zevallos  
**Sección:** C  
**Curso:** Desarrollo de Aplicaciones Web  
**Institución:** Tecsup  

---

## 1. Descripción del Proyecto

El sistema está diseñado bajo una arquitectura de microservicios orientada a la gestión descentralizada de entregas, clientes y repartidores. Cada servicio mantiene su propio dominio funcional y persiste los datos en su propia base de datos MySQL aislada. La comunicación externa se centraliza a través de un API Gateway y el descubrimiento de servicios se gestiona dinámicamente con Spring Cloud Eureka Server.

---

## 2. Arquitectura de Microservicios y Puertos

| Servicio | Puerto | Base de Datos (MySQL) | Descripción |
| :--- | :---: | :---: | :--- |
| **eureka-server** | `8761` | N/A | Servidor de Registro y Descubrimiento de Servicios. |
| **api-gateway** | `8080` | N/A | Punto único de entrada, enrutamiento dinámico de peticiones REST. |
| **ms-cliente** | `8081` | `bd_cliente` | Gestión CRUD y validación de Clientes. |
| **ms-repartidor** | `8082` | `bd_repartidor` | Gestión CRUD de Repartidores y su disponibilidad. |
| **ms-entrega** | `8083` | `bd_entrega` | Gestión del ciclo de vida de las Entregas. |

---

## 3. Requisitos Previos

Antes de ejecutar los microservicios, asegúrese de contar con las siguientes herramientas instaladas y configuradas:

- **Java JDK:** Versión 17 o superior.
- **Apache Maven:** Versión 3.8+ (o el wrapper `./mvnw` incluido).
- **MySQL Server:** Escuchando en el puerto estándar `3306`.
- **Postman:** Para ejecutar las pruebas RESTful.

---

## 4. Configuración de las Bases de Datos (MySQL)

Crear las tres bases de datos relacionales requeridas ejecutando la siguiente consulta en MySQL Workbench o CLI:

```sql
CREATE DATABASE IF NOT EXISTS bd_cliente;
CREATE DATABASE IF NOT EXISTS bd_repartidor;
CREATE DATABASE IF NOT EXISTS bd_entrega;
```

> **Nota:** Las tablas serán creadas automáticamente por Hibernate al iniciar cada microservicio (`spring.jpa.hibernate.ddl-auto=update`).

---

## 5. Orden Obligatorio de Ejecución

Para un correcto registro e interconexión a través del servidor Eureka, inicie los microservicios siguiendo **estrictamente este orden**:

1. **`eureka-server`** (Espera a que inicialice completamente en `http://localhost:8761`).
2. **`api-gateway`** (Escuchando en `http://localhost:8080`).
3. **`ms-cliente`** (Puerto 8081).
4. **`ms-repartidor`** (Puerto 8082).
5. **`ms-entrega`** (Puerto 8083).

### Comandos de Ejecución (CLI):

En cada carpeta raíz de servicio:

```bash
# Opción 1: Con Maven Wrapper
./mvnw spring-boot:run

# Opción 2: Con Maven instalado globalmente
mvn spring-boot:run
```

---

## 6. Mapeo de Endpoints (vía API Gateway - 8080)

### Clientes (`ms-cliente`)
- `POST /api/clientes` : Registrar un nuevo cliente.
- `GET /api/clientes` : Listar todos los clientes.

### Repartidores (`ms-repartidor`)
- `POST /api/repartidores` : Registrar un repartidor.
- `GET /api/repartidores` : Listar todos los repartidores.

### Entregas (`ms-entrega`)
- `POST /api/entregas` : Crear una entrega (Estado por defecto: `PENDIENTE`).
- `PUT /api/entregas/{id}` : Actualizar entrega/estado (`PENDIENTE`, `EN_CAMINO`, `ENTREGADA`).
- `GET /api/entregas/{id}` : Buscar entrega por ID.

---

## 7. Perfiles de Configuración (YAML)

El sistema soporta la gestión multi-ambiente configurada en los archivos `application.yml` o mediante archivos dedicados (`application-dev.yml` / `application-prod.yml`):

- **Ambiente `dev` (Desarrollo):**
  - Conexión a base de datos en `localhost:3306`.
  - Logging habilitado para SQL (`show-sql: true`).
  - Creación y actualización automática de esquemas (`ddl-auto: update`).
- **Ambiente `prod` (Producción):**
  - Conexión a servidor remoto de MySQL con credenciales cifradas/seguras.
  - Ocultamiento de logs SQL internos (`show-sql: false`).
  - Validación de esquemas existentes sin modificación destructiva (`ddl-auto: validate`).

---

## 8. Manejo Global de Excepciones

Cada microservicio implementa una clase annotated con `@RestControllerAdvice` y DTOs validados con Bean Validation (`@Valid`):

- **Código HTTP `400 Bad Request`:** Devuelto cuando faltan campos obligatorios o los datos enviados no cumplen las restricciones definidas (ej. costo negativo o campos vacíos).
- **Código HTTP `404 Not Found`:** Devuelto de forma descriptiva al consultar IDs de entidades inexistentes en la base de datos.