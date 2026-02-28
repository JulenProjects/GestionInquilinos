-------------------------------------------------------------------------------------------------------


🏠 Sistema de Gestión de Inquilinos - Backend en Spring Boot


-------------------------------------------------------------------------------------------------------


Este proyecto es un backend completo en Java con Spring Boot para la gestión de:

- Inquilinos  
- Inmuebles  
- Pagos  
- Usuarios  
- Roles (ADMIN / USER)  
- Seguridad con JWT
  

Este proyecto forma parte de mi portfolio técnico para optar a prácticas FCT (DAM).
Aspiro a seguir creciendo como desarrollador backend, aprendiendo buenas prácticas y
tecnologias modernas como microservicios, Docker y servicios cloud.

--------------------------------------------------------------------------------------------------

🚀 Tecnologías utilizadas

- Java 21  
- Spring Boot 3  
- Spring Security  
- JWT (Json Web Tokens)  
- Spring Data JPA  
- Hibernate  
- MySQL  
- Lombok  
- Maven
- JUnit
- Mockito
- Postman

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

*️⃣ Importante para poder arrancar el proyecto:

Para ejecutar este backend es necesario tener MySQL instalado y en funcionamiento en el equipo.
Es imprescindible configurar los siguientes valores en el archivo: application.propierties 
(situado en src/main/resources/application.properties). 

Con tus datos:

-spring.datasource.username=TU_USUARIO

-spring.datasource.password=TU_PASSWORD

-jwt.secret=TU_SECRET_KEY


Solo necesitas esos tres valores para ejecutar el proyecto.
Se recomienda abrirlo con IntelliJ IDEA y ejecutar la clase principal del proyecto.



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


✅ Continuaré ampliando y mejorando este proyecto mientras avanzo en mi formación y crecimiento
como desarrollador, manteniéndolo abierto a mejoras y contribuciones.








