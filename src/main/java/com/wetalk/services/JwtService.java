package com.wetalk.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${app.jwt.secret:changemechangemechangemechangeme}")
    private String secret;

    @Value("${app.jwt.expSeconds:86400}")
    private long expSeconds;

    private javax.crypto.SecretKey key(){
        // For HS256 secret must be at least 256-bit (32 bytes)
        byte[] bytes = java.util.Arrays.copyOf(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8), 32);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(Long userId, String account){
        Date now = new Date();
        Date exp = new Date(now.getTime() + expSeconds * 1000);
        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("account", account)
            .setIssuedAt(now)
            .setExpiration(exp)
            .signWith(key(), SignatureAlgorithm.HS256)
            .compact();
    }

    public Claims parse(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
    }
}
