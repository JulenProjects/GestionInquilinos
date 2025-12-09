package com.example.sistemaInquilinos.seguridad.filtro;

import com.example.sistemaInquilinos.seguridad.creacionYValidacionToken.jwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * FILTRO JWT
 *
 * Este filtro se ejecuta AUTOMÁTICAMENTE en CADA petición HTTP.
 * Su función es:
 * 1. Leer el token JWT de la cabecera Authorization.
 * 2. Validar el token.
 * 3. Si es válido, autenticar al usuario dentro de Spring Security.
 *
 * Gracias a este filtro, el backend sabe qué usuario está autenticado
 * en cada petición sin usar sesiones.
 */
@Component // Registramos este filtro como un componente de Spring
@RequiredArgsConstructor // Lombok genera el constructor con los atributos final
public class filtrarToken extends OncePerRequestFilter {

    // Servicio encargado de validar y leer tokens JWT
    public final jwtService jwtService;

    // Servicio que carga usuarios desde la base de datos
    public final UserDetailsService userDetailsService;

    /**
     * Este método se ejecuta automáticamente en CADA petición.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtenemos la ruta que se está solicitando
        String path = request.getServletPath();

        // 2. Si la petición es al LOGIN, NO aplicamos el filtro
        // si en algun momento el registro lo dejamos publico se tendria que añadir:
        // || path.equals("/acceso/register"))
        if (path.equals("/acceso/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Obtenemos el token de la cabecera Authorization
        final String token = getTokenFromRequest(request);

        // 4. Variable donde guardaremos el nombre del usuario del token
        final String usuario;

        // 5. Objeto donde guardaremos los datos completos del usuario
        UserDetails usuarioDetails = null;

        // 6. Si no hay token, dejamos pasar la petición sin autenticar
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 7. Extraemos el nombre de usuario desde el token
        usuario = jwtService.getUsernameFromToken(token);

        // 8. Si hay usuario y aún no está autenticado en el contexto de seguridad
        if (usuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 9. Cargamos al usuario desde la base de datos
            usuarioDetails = userDetailsService.loadUserByUsername(usuario); // en caso de no existir lanza excepcion
        }

        // 10. Validamos el token con los datos del usuario
        if (jwtService.isTokenValid(token, usuarioDetails)) {

            // 11. Creamos el objeto de autenticación para Spring
            UsernamePasswordAuthenticationToken logado =
                    new UsernamePasswordAuthenticationToken(
                            usuarioDetails,          // Usuario autenticado
                            null,                   // Credenciales (no se usan aquí)
                            usuarioDetails.getAuthorities() // Roles del usuario
                    );

            // 12. Añadimos información extra de la petición (IP, navegador, etc.)
            logado.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 13. Registramos al usuario como autenticado en el contexto de Spring Security
            SecurityContextHolder.getContext().setAuthentication(logado);
        }

        // 14. Continuamos con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    /**
     * Método que extrae el token JWT de la cabecera Authorization.
     *
     * Formato esperado:
     * Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
     */
    private String getTokenFromRequest(HttpServletRequest request) {

        final String cabeza = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(cabeza) && cabeza.startsWith("Bearer")) {
            // Eliminamos "Bearer " del inicio del token
            return cabeza.substring(7);
        }

        return null;
    }
}
