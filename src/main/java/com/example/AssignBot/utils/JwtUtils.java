package com.example.AssignBot.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    // Secret key for signing the JWT, should be kept secure in production
    private static final String SECRET_KEY = "M2FmMzUyYjMwYjY5NzA5YjIzZDI0ZDQ4MzYzN2MyODdiYjRkYjRkYmYyMzE4ZjQwZTY2NTNkYmJkYjc4ZmRlZjJlM2RkYzY4MGMw";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60*5; // 5 hour expiration time

    // Create the signing key from the SECRET_KEY
    private Key getSigningKey() {
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate a JWT token based on the username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Create the JWT token with claims, subject, and expiration
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // 1 hour expiration time
                .setHeaderParam("typ", "JWT")
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate the JWT token by checking the username and expiration
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Extract the username from the JWT token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Extract all claims from the JWT token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())  // Use the signing key to parse the token
                    .build()
                    .parseClaimsJws(token)  // Parse the JWT token
                    .getBody();  // Return the claims
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid or expired token", e);
        }
    }

    // Parse the token and return the claims
    public Claims parseToken(String token) {
        return extractAllClaims(token);
    }

    // Generate token with custom expiration time
    public String generateTokenWithExpiration(String username, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();
        return createTokenWithExpiration(claims, username, expirationTime);
    }

    // Create a token with custom expiration time
    private String createTokenWithExpiration(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setHeaderParam("typ", "JWT")
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}