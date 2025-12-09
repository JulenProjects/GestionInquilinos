package com.example.sistemaInquilinos.seguridad.controlador;

import com.example.sistemaInquilinos.seguridad.dtos.loginRequest;
import com.example.sistemaInquilinos.seguridad.dtos.registerRequest;
import com.example.sistemaInquilinos.seguridad.servicio.seguridadServicio;
import com.example.sistemaInquilinos.seguridad.servicio.tokenDevolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de ACCESO a la aplicación.
 *
 * Este controlador gestiona los endpoints relacionados con:
 * - El LOGIN de usuarios
 * - El REGISTRO de nuevos usuarios
 *
 * ⚠️ IMPORTANTE:
 * - El endpoint de LOGIN es PÚBLICO.
 * - El endpoint de REGISTER está PROTEGIDO y SOLO puede usarlo un usuario con rol ADMIN.
 *   (Esta restricción se aplica desde la configuración de seguridad en SecurityFilterChain).
 */
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/acceso") // Ruta base: http://localhost:8080/acceso
@RequiredArgsConstructor // Lombok genera automáticamente el constructor con los atributos final
@CrossOrigin(origins = "http://localhost:4200") // Permite peticiones desde el frontend Angular
public class controladorAcceso {

    // Servicio de seguridad que contiene toda la lógica de login y registro
    private final seguridadServicio seguridadService;

    /**
     * ENDPOINT DE LOGIN (PÚBLICO)
     *
     * URL: POST /acceso/login
     *
     * Recibe:
     * - usuario
     * - password
     *
     * Funcionamiento:
     * 1. Verifica las credenciales con Spring Security.
     * 2. Si son correctas, genera un token JWT.
     * 3. Devuelve el token al cliente.
     *
     * Acceso:
     * ✅ Cualquiera puede usar este endpoint.
     */
    @PostMapping("/login")
    public ResponseEntity<tokenDevolver> login(@RequestBody loginRequest request) {

        return ResponseEntity.ok(seguridadService.login(request));
    }

    /**
     * ENDPOINT DE REGISTRO (PROTEGIDO)
     *
     * URL: POST /acceso/register
     *
     * Recibe:
     * - usuario
     * - password
     * - role
     *
     * Funcionamiento:
     * 1. Crea un nuevo usuario en la base de datos.
     * 2. Cifra la contraseña con BCrypt.
     * 3. Genera un token JWT.
     * 4. Devuelve el token al cliente.
     *
     * Acceso:
     * 🔒 SOLO puede acceder un usuario con rol ADMIN.
     * (Esta protección se configura en SecurityFilterChain).
     *
     * ℹ️ NOTA IMPORTANTE:
     * Actualmente no sería estrictamente necesario devolver un token en el register,
     * ya que solo el ADMIN puede acceder a este endpoint.
     * Aun así, se devuelve el token para:
     * - Mantener el mismo formato de respuesta que el login
     * - Facilitar posibles cambios futuros (por ejemplo, si se permite el registro público)
     */
    @PostMapping("/register")
    public ResponseEntity<tokenDevolver> register(@RequestBody registerRequest request) {

        return ResponseEntity.ok(seguridadService.register(request));
    }

}
