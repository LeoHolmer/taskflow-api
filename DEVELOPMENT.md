# Guía de Desarrollo - TaskFlow API

## 🚀 Configuración del Entorno Local

### Requisitos Mínimos
- Java 17+ (verificar con `java -version`)
- Maven 3.8.1+ (incluido `./mvnw`)
- Docker & Docker Compose (recomendado para PostgreSQL)
- IDE: IntelliJ IDEA, VS Code o similar

### Setup Inicial

#### Opción 1: Con Docker Compose (Recomendado)
```bash
# Clona el repositorio
git clone https://github.com/LeoHolmer/taskflow-api.git
cd taskflow-api

# Copia el archivo de ejemplo de ambiente
cp .env.example .env

# Edita .env con tus valores (solo para local)
# Ejecuta los servicios (DB + API)
docker-compose up -d

# Verifica que esté corriendo
curl http://localhost:8080/actuator/health
```

#### Opción 2: Local sin Docker
```bash
# Instala PostgreSQL localmente (o usa H2)
# En application.properties, cambia a:
# spring.profiles.active=dev

# Build
./mvnw clean install

# Run
./mvnw spring-boot:run

# O ejecuta el JAR directamente
java -jar target/api-0.0.1-SNAPSHOT.jar
```

## 📝 Convenciones de Código

### Java Naming
- **Clases**: `PascalCase` (ej: `UserService`, `TaskController`)
- **Métodos**: `camelCase` (ej: `getUserById()`, `createTask()`)
- **Constantes**: `UPPER_SNAKE_CASE` (ej: `MAX_POOL_SIZE = 20`)
- **Paquetes**: `com.taskflow.api.<layer>` (ej: `com.taskflow.api.service`)

### Estructura de Commits
Usar **Conventional Commits**:

```bash
# Feature nueva
git commit -m "feat(users): add user profile endpoint"

# Bugfix
git commit -m "fix(auth): resolve JWT expiration validation"

# Documentation
git commit -m "docs(api): add API endpoint examples"

# Refactor (sin cambios funcionales)
git commit -m "refactor(service): extract validation logic to helper"

# Tests
git commit -m "test(tasks): add edge case tests for priority validation"

# Chores (dependencias, configuración)
git commit -m "chore(deps): update spring-boot to 3.2.6"
```

### Formato de Código

#### DTOs (Data Transfer Objects)
```java
@Data  // Lombok: genera getter/setter/toString/equals/hashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    @JsonProperty("created_at")  // JSON key personalizada
    private LocalDateTime createdAt;
}
```

#### Entidades JPA
```java
@Entity
@Table(name = "users", indexes = @Index(name = "idx_email", columnList = "email"))
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {  // BaseEntity incluye id, timestamps
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;  // Nunca retornar en responses
}
```

#### Servicios
```java
@Service
@RequiredArgsConstructor  // Lombok: inyecta constructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
            .map(this::mapToDTO)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    
    @Transactional  // Permite escribir
    public UserDTO createUser(CreateUserDTO dto) {
        // lógica...
    }
}
```

## 🧪 Testing

### Correr Tests Localmente
```bash
# Todos los tests
./mvnw test

# Test específico
./mvnw test -Dtest=UserServiceTest

# Con cobertura
./mvnw test jacoco:report
# Reporta en: target/site/jacoco/index.html
```

### Escribir un Test de Integración
```java
@SpringBootTest  // Carga contexto completo
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        var createDTO = new CreateUserDTO("john@example.com", "John", "pass123");
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}
```

## 🔍 Debugging

### Logs
```bash
# Ver logs en tiempo real
docker-compose logs -f api

# Ver logs de base de datos
docker-compose logs -f db

# Logs de nivel DEBUG
export LOGGING_LEVEL_COM_TASKFLOW_API=DEBUG
./mvnw spring-boot:run
```

### H2 Console (Desarrollo)
```
Acceso: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:taskflow
User: sa
Password: (vacío)
```

### Breakpoints en IDE
- IntelliJ: Click en línea → F9 para correr hasta breakpoint
- VS Code + Extension Debugger for Java → Run & Debug

## 📚 Recursos Útiles

### Documentación Oficial
- [Spring Boot 3.x Docs](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)

### Librerías Usadas
- **Lombok**: Reduce boilerplate (getters, setters)
- **MapStruct**: Mapeo de objetos (alternativa a ModelMapper)
- **JWT (Auth0)**: Generación y validación de tokens

## ⚠️ Errores Comunes

### 1. "JWT token not valid"
- Verifica que `JWT_SECRET` esté configurada en `.env`
- Asegúrate de incluir `Authorization: Bearer <token>` en headers

### 2. "No qualifying bean of type 'UserRepository'"
- Spring no encontró el repositorio
- Verifica que la clase extienda `JpaRepository<User, Long>`
- Verifica que esté en `com.taskflow.api.repository`

### 3. "Hibernate dialect error"
- Asegúrate de que PostgreSQL esté corriendo
- Verifica `spring.datasource.url` en `application-prod.yml`

## 🎯 Próximos Pasos (Roadmap)

1. **v1.1**: Notificaciones por email en cambios de tareas
2. **v1.2**: Reportes y dashboards
3. **v2.0**: Microservicios y Event Sourcing
4. **v2.1**: Mobile app (React Native)

## 📞 Soporte

- **Issues**: https://github.com/LeoHolmer/taskflow-api/issues
- **Discussions**: https://github.com/LeoHolmer/taskflow-api/discussions
- **Email**: leonardoholmer1@gmail.com
