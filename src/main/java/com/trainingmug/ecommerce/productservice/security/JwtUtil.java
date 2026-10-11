
package com.trainingmug.ecommerce.productservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity.accessToken}")
    private long accessTokenValidity;

    @Value("${jwt.validity.refreshToken}")
    private long refreshTokenValidity;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    public String generateAccessToken(String email, String role) {
        return generateToken(email, role, accessTokenValidity);
    }

    public String generateRefreshToken(String email, String role) {
        return generateToken(email, role, refreshTokenValidity);
    }

    private String generateToken(String email, String role, long validity) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validity);

        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withIssuer("TrainingMug")
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .sign(getAlgorithm());
    }

    public DecodedJWT validateToken(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm())
                .withIssuer("TrainingMug")
                .build();

        return verifier.verify(token);
    }

    public String extractEmail(String token) {
        return validateToken(token).getSubject();
    }

    public String extractRole(String token) {
        return validateToken(token).getClaim("role").asString();
    }
}