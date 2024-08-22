package com.mindhub.todolist.configurationsJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private final SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(String username, Map<String, Object> claims) { //genera el token
        return Jwts.builder() //patrón builder
                .subject(username) //subject: algo único de cada user, ej: email
                .issuedAt(new Date()) //fecha actual
                .expiration(new Date(System.currentTimeMillis() + expiration)) //fecha de expiración
                .signWith(secretKey) //firma
                .compact(); //compacta
    }

    public String generateClaims(String username){
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", "rol");
        return generateToken(username, claims);
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    } //valida que el token esté bien

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    } //este método obtiene cada uno de los claims

    private boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }
}
