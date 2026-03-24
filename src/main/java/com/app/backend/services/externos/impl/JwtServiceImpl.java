package com.app.backend.services.externos.impl;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.app.backend.services.externos.IJwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements IJwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey obtenerClaveInicioSesion() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extraerUsuario(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Integer extraerIdUsuario(String token) {
        return extractClaim(token, claims -> claims.get("idUsuario", Integer.class));
    }

    @Override
    public Integer extraerIdCarrera(String token) {
        return extractClaim(token, claims -> claims.get("idCarrera", Integer.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(obtenerClaveInicioSesion())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    public String generarToken(Map<String, Object> extraClaims,String username) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(obtenerClaveInicioSesion())
                .compact();
    }

    public Boolean esTokenValido(String token, UserDetails userDetails) {
        final String username = extraerUsuario(token);
        return (username.equals(userDetails.getUsername())) && !esTokenExpirado(token);
    }

    private Boolean esTokenExpirado(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
