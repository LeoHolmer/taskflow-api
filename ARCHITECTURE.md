# Arquitectura de TaskFlow API

## 📐 Descripción General

TaskFlow API fue diseñada siguiendo principios de **Clean Architecture** para garantizar escalabilidad, mantenibilidad y testabilidad. La aplicación se estructura en capas independientes que comunican a través de interfaces bien definidas.

## 🏗️ Capas de la Arquitectura

### 1. **Presentation Layer (API REST)**
- **Ubicación**: `controller/`
- **Responsabilidad**: Recibir y validar requests HTTP
- **Componentes**:
  - `UserController`: Gestión de usuarios (CRUD)
  - `ProjectController`: Gestión de proyectos
  - `TaskController`: Gestión de tareas
  - Validación de entrada con annotations (`@Valid`, `@NotNull`)
  - Manejo de excepciones global en `GlobalExceptionHandler`

### 2. **Application/Service Layer**
- **Ubicación**: `service/`
- **Responsabilidad**: Lógica de negocio
- **Características**:
  - Servicios que implementan reglas de negocio
  - Orquestación entre repositorios y DTOs
  - Manejo de transacciones
  - Validaciones de negocio complejas

### 3. **Domain Layer**
- **Ubicación**: `entity/` y `dto/`
- **Responsabilidad**: Modelos de datos
- **Entidades JPA**:
  - `User`: Información de usuario con roles
  - `Project`: Proyectos creados por usuarios
  - `Task`: Tareas asignadas a proyectos
- **DTOs**: Transferencia de datos entre capas (separación de concerns)

### 4. **Persistence/Data Access Layer**
- **Ubicación**: `repository/`
- **Responsabilidad**: Acceso a base de datos
- **Spring Data JPA**:
  - Queries personalizadas con `@Query`
  - Soft deletes implementados (campo `deletedAt`)
  - Pagination automática

### 5. **Infrastructure/Config Layer**
- **Ubicación**: `config/`
- **Responsabilidad**: Configuraciones técnicas
- **Módulos**:
  - `SecurityConfig`: Configuración JWT y autorización
  - `CorsConfig`: CORS para desarrollo/producción
  - `PasswordEncoderConfig`: Bcrypt password encoding
  - `JwtAuthenticationFilter`: Interceptor de JWT

## 🔐 Seguridad

### Flujo de Autenticación
```
Request → JwtAuthenticationFilter → Valida JWT
                                  ↓
                            Extrae claims
                                  ↓
                            Carga UserDetails
                                  ↓
                            Autoriza según role
                                  ↓
                            Request permitido/rechazado
```

### Protección de Endpoints
- `/auth/**`: Públicos (login, registro)
- `/api/users/**`: Requieren JWT + role USER
- `/api/admin/**`: Requieren JWT + role ADMIN
- `/actuator/health`: Público (health checks)

## 🗄️ Base de Datos

### Modelo de Datos
```
Users (1) ──── (N) Projects (1) ──── (N) Tasks
  ├─ id            ├─ id                ├─ id
  ├─ email         ├─ name              ├─ title
  ├─ password      ├─ description       ├─ description
  ├─ name          ├─ created_at        ├─ priority
  ├─ role          ├─ updated_at        ├─ status
  └─ created_at    └─ deleted_at        ├─ due_date
                                        └─ deleted_at
```

### Soft Delete Pattern
Todas las entidades principales tienen un campo `deletedAt`:
- Si `deletedAt IS NULL` → registro activo
- Si `deletedAt IS NOT NULL` → registro eliminado lógicamente
- Las queries filtran automáticamente registros eliminados

## 📊 Flujos Principales

### 1. Crear una Tarea
```
POST /api/tasks
├─ TaskController.createTask(CreateTaskDTO)
├─ TaskService.create(dto)
│  ├─ Valida usuario existe
│  ├─ Valida proyecto existe
│  ├─ Valida prioridad/estado válidos
│  └─ Persiste en BD
├─ Retorna TaskResponseDTO
└─ HTTP 201 + location header
```

### 2. Listar Tareas (con Paginación)
```
GET /api/tasks?page=0&size=10&sort=createdAt,desc
├─ TaskController.getTasks(Pageable)
├─ TaskService.findAll(pageable)
│  ├─ Query con filtro deletedAt IS NULL
│  └─ Retorna Page<Task>
└─ HTTP 200 + JSON paginado
```

## 🧪 Testing

### Estrategia de Testing
- **Unit Tests**: Servicios y utilidades (mocking de repositorios)
- **Integration Tests**: Controladores con MockMvc
- **Test Database**: H2 en memoria para tests
- **Test Profile**: Configuración separada en `application-test.yml`

### Ejemplo de Test
```java
@SpringBootTest
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    
    @InjectMocks
    private TaskService taskService;
    
    @Test
    void shouldCreateTaskSuccessfully() {
        // Given
        CreateTaskDTO dto = new CreateTaskDTO("Test", "Desc", HIGH);
        
        // When
        TaskDTO result = taskService.create(dto);
        
        // Then
        assertNotNull(result.getId());
    }
}
```

## 🚀 Ciclo de Vida de una Request

```
1. Request entra → Spring DispatcherServlet
2. RequestMapping busca controlador
3. JwtAuthenticationFilter intercepta → Valida JWT
4. Controller llama Service
5. Service valida y orquesta
6. Repositorio persiste/consulta BD
7. Response Builder serializa DTO
8. Jackson convierte a JSON
9. HTTP Response retorna al cliente
```

## 🔄 Versionado de API

Actualmente se usa `/api/` como prefijo base. Para futuro:
- `/api/v1/` - Endpoints de producción
- `/api/v2/` - Nuevas features (backward compatible)
- Mantener deprecación mínimo 2 versiones

## 📈 Performance y Escalabilidad

### Optimizaciones Actuales
- **Connection Pool**: HikariCP con 20 conexiones máximo
- **Lazy Loading**: DTOs evitan N+1 queries
- **Índices BD**: En campos email (UNIQUE), createdAt
- **Caché**: Spring Cache Ready (anotaciones `@Cacheable`)

### Monitoreo
- Spring Boot Actuator: `/actuator/health`, `/actuator/metrics`
- Micrometer Prometheus: Métricas JVM y aplicación
- SLF4J Logging: INFO a producción, DEBUG a desarrollo

## 🛠️ Extensiones Futuras

1. **Message Queue**: RabbitMQ para notificaciones async
2. **Microservicios**: Separar Users, Projects, Tasks
3. **GraphQL**: Alternativa a REST API
4. **WebSockets**: Real-time updates de tareas
5. **File Upload**: S3 para adjuntos en tareas
