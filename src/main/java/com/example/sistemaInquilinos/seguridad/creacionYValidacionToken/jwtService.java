package com.example.sistemaInquilinos.seguridad.creacionYValidacionToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service // Marca esta clase como un servicio de Spring
public class jwtService {

    // 🔑 Clave secreta cargada desde application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    // ⏱️ Tiempo de expiración cargado desde application.properties
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Genera un token JWT para un usuario.
     */
    public String getTokenFinal(UserDetails usuario){
        return getToken(new HashMap<>(), usuario);
    }

    /**
     * Genera un token JWT completo.
     */
    private String getToken(Map<String, Object> infoExtra, UserDetails usuario){

        return Jwts.builder()
                .setClaims(infoExtra)
                .setSubject(usuario.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(conseguirKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Convierte la clave secreta en un objeto Key válido para JWT.
     */
    private Key conseguirKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene todos los datos (claims) del token.
     */
    private Claims getAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(conseguirKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene un claim específico del token.
     */
    public <T> T getClaim(String token, Function<Claims, T> claims)
    {
        final Claims allClaims = getAllClaims(token);
        return claims.apply(allClaims);
    }

    /**
     * Obtiene el nombre de usuario almacenado en el token.
     */
    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Obtiene la fecha de expiración del token.
     */
    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Comprueba si el token ya ha expirado.
     */
    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }

    /**
     * Valida si un token es correcto.
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}



// Orden sencillo:
//Crear token 👉 getTokenFinal / getToken / conseguirKey
//
//Leer token 👉 getAllClaims / getClaim
//
//Validar token 👉 getUsernameFromToken / getExpiration / isTokenExpired / isTokenValid