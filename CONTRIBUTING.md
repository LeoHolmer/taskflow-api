# Guía de Contribución

¡Gracias por tu interés en contribuir a TaskFlow API! Este documento describe las pautas y procesos para contribuir al proyecto.

## 🚀 Cómo Contribuir

### 1. Preparación del Entorno

Antes de comenzar, asegúrate de tener instalado:
- Java 17+
- Maven 3.6+
- Docker (opcional)

### 2. Configuración del Proyecto

```bash
# Clona el repositorio
git clone https://github.com/LeoHolmer/taskflow-api.git
cd taskflow-api

# Instala dependencias
./mvnw clean install

# Ejecuta tests
./mvnw test

# Ejecuta la aplicación
./mvnw spring-boot:run
```

### 3. Flujo de Trabajo

1. **Crea una rama** para tu contribución:
   ```bash
   git checkout -b feature/nombre-de-tu-feature
   # o
   git checkout -b fix/nombre-del-bug
   ```

2. **Realiza tus cambios** siguiendo las mejores prácticas

3. **Escribe tests** para tu funcionalidad

4. **Ejecuta todos los tests**:
   ```bash
   ./mvnw test
   ```

5. **Verifica el estilo de código**:
   ```bash
   ./mvnw spotless:check
   ./mvnw spotless:apply  # Para aplicar correcciones automáticas
   ```

6. **Commit tus cambios**:
   ```bash
   git add .
   git commit -m "feat: descripción de la funcionalidad"
   ```

7. **Push y crea un Pull Request**:
   ```bash
   git push origin feature/nombre-de-tu-feature
   ```

## 📝 Estándares de Código

### Java
- Sigue las **convenciones de Java**
- Usa **Lombok** para reducir boilerplate
- Implementa **validaciones** apropiadas
- Maneja **excepciones** correctamente

### Commits
Usa **Conventional Commits**:

```bash
feat: agregar nueva funcionalidad
fix: corregir bug específico
docs: actualizar documentación
style: cambios de formato
refactor: refactorizar código
test: agregar o modificar tests
chore: cambios de mantenimiento
```

### Pull Requests
- **Título descriptivo** del PR
- **Descripción detallada** de los cambios
- **Referencia issues** relacionados
- **Tests incluidos** para nuevas funcionalidades
- **Documentación actualizada** si es necesario

## 🧪 Testing

### Tipos de Tests
- **Unit Tests**: Para servicios y utilidades
- **Integration Tests**: Para controladores y repositorios
- **Security Tests**: Para autenticación y autorización

### Ejecutar Tests
```bash
# Todos los tests
./mvnw test

# Tests con cobertura
./mvnw test jacoco:report

# Tests específicos
./mvnw test -Dtest=UserServiceTest
```

## 📚 Documentación

### API Documentation
- Mantén actualizada la documentación **OpenAPI/Swagger**
- Agrega ejemplos de uso en los controladores
- Documenta parámetros y respuestas

### Code Documentation
- Usa **JavaDoc** para clases y métodos públicos
- Comentarios claros en lógica compleja
- Mantén el README actualizado

## 🐛 Reportar Bugs

Para reportar bugs, crea un issue con:
- **Título descriptivo**
- **Pasos para reproducir**
- **Comportamiento esperado vs actual**
- **Entorno** (OS, Java version, etc.)
- **Logs** relevantes

## 💡 Sugerir Funcionalidades

Para nuevas funcionalidades:
- **Describe claramente** la necesidad
- **Proporciona casos de uso**
- **Considera el impacto** en la arquitectura
- **Discute alternativas** si es apropiado

## 📋 Checklist para PRs

Antes de enviar tu PR, verifica:
- [ ] Tests pasan localmente
- [ ] Código compila sin warnings
- [ ] Estilo de código consistente
- [ ] Documentación actualizada
- [ ] Variables de entorno documentadas
- [ ] Migrations de BD incluidas si es necesario
- [ ] Security considerations revisadas

## 🎯 Áreas de Contribución

### Prioritarias
- **Mejoras de performance**
- **Nuevos endpoints de API**
- **Mejor manejo de errores**
- **Tests adicionales**

### Futuras
- **Frontend web**
- **Aplicación móvil**
- **Integraciones con terceros**
- **Microservicios**

## 📞 Soporte

¿Necesitas ayuda?
- Revisa la [documentación](README.md)
- Busca en issues existentes
- Crea un nuevo issue para preguntas

¡Gracias por contribuir a TaskFlow API! 🎉</content>
<parameter name="filePath">/home/leo-holmer/Proyectos/Personales/TaskFlow/CONTRIBUTING.md