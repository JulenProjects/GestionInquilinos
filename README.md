-------------------------------------------------------------------------------------------------------


🏠 Sistema de Gestión de Inquilinos - Backend en Spring Boot + Panel Web

Este proyecto es un backend completo en Java con Spring Boot para la gestión de:

* Inquilinos
* Inmuebles
* Pagos
* Usuarios
* Roles (ADMIN / USER)
* Seguridad con JWT

Incluye un panel web para interactuar con la API y está completamente dockerizado:
frontend, backend y base de datos se levantan con un solo comando.



--------------------------------------------------------------------------------------------------


🚀 Tecnologías utilizadas

Backend
* Java 21
* Spring Boot 3
* Spring Security
* JWT (JSON Web Tokens)
* Spring Data JPA
* Hibernate
* MySQL
* Lombok
* Maven

Testing
* JUnit 5
* Mockito
* Postman

Frontend
* HTML, CSS y JavaScript (desarrollado con apoyo de IA para visualizar y probar la API)

Despliegue
* Docker
* Docker Compose
* Nginx

--------------------------------------------------------------------------------------------------

 🔐 Seguridad

El sistema utiliza autenticación mediante JWT:

- Login genera un token
- Todos los endpoints están protegidos excepto el login
- Los roles controlan el acceso:
  - ADMIN: Gestión de todo el proyecto
  - USER: Limitacion en la gestion de usuarios

--------------------------------------------------------------------------------------------------

🧩 Estructura del proyecto

Este backend está organizado bajo una arquitectura limpia y mantenible, separada en capas:

-Entidad
-Repositorio
-Servicio
-servicio de DTO
-Controlador
-Seguridad

---------------------------------------------------------------------------------------------------

💾 Base de datos: MySQL

Tablas principales:
- usuario
- inquilino
- inmueble
- pagos

Las relaciones están gestionadas mediante JPA + Hibernate.


--------------------------------------------------------------------------------------------------

🧪 Testing (JUnit + Mockito)

El proyecto incluye pruebas unitarias básicas para la capa de servicio usando:

- JUnit 5
- Mockito


---------------------------------------------------------------------------------------------------

*️⃣ Cómo arrancar el proyecto:

🐳 Con Docker (recomendado)

Solo es necesario tener Docker Desktop instalado y en funcionamiento.
No hace falta instalar Java, Maven ni MySQL.

Desde la carpeta del proyecto, ejecutar:

docker compose up --build

Cuando en los logs aparezca "🚀 SISTEMA LISTO PARA SER PROBADO", la aplicación estará disponible en:

- Panel web: http://localhost:3000
- API REST: http://localhost:8080

Para detener la aplicación:

docker compose down


-----------------------------------------------------------------------------------------------------

✅ Usuario administrador por defecto

Al arrancar el proyecto por primera vez se crea automaticamente:

Usuario: adminPrincipal

Password: admin1234

Rol: ADMIN

Desde este usuario se pueden:
- Crear nuevos usuarios
- Gestionar todo el sistema

  

----------------------------------------------------------------------------------------------


🧪Endpoints principales

 🔑 - Login : POST http://localhost:8080/acceso/login
 
 (nos devuelve el token para poder realizar peticiones autenticadas dentro del proyecto.
 Ideal para realizar pruebas en Postman).
 
---

👥 Usuarios (ADMIN)

  GET    http://localhost:8080/usuarios/listado
  
  POST   http://localhost:8080/usuarios/listado/nuevo
  
  PUT    http://localhost:8080/usuarios/listado/{id}
  
  DELETE http://localhost:8080/usuarios/listado/{id}

---

🏠 Inmuebles

GET    http://localhost:8080/inmuebles/listado

POST   http://localhost:8080/inmuebles/listado

PUT    http://localhost:8080/inmuebles/listado/{id}

DELETE http://localhost:8080/inmuebles/listado/{id}

---

👤 Inquilinos

GET    http://localhost:8080/inquilinos/listado

POST   http://localhost:8080/inquilinos/listado

PUT    http://localhost:8080/inquilinos/listado/{id}

DELETE http://localhost:8080/inquilinos/listado/{id}

---

💰 Pagos

GET    http://localhost:8080/pagos/listado

POST   http://localhost:8080/pagos/listado/nuevo

PUT    http://localhost:8080/pagos/listado/{id}

DELETE http://localhost:8080/pagos/listado/{id}

GET    http://localhost:8080/pagos/listado/impagos

GET    http://localhost:8080/pagos/listado/porInmueble/{id}

GET    http://localhost:8080/pagos/listado/porFecha/{anio}/{mes} (Mes en formato 1-12)


------------------------------------------------------------------------------------------------


✅ Continuaré ampliando y mejorando este proyecto, manteniéndolo abierto a mejoras y contribuciones.








