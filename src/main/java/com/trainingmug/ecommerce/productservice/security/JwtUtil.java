package com.trainingmug.ecommerce.productservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component //tells Spring to create and manage a JwtUtil object
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity.accessToken}")
    private long accessTokenValidity;

    @Value("${jwt.validity.refreshToken}")
    private long refreshTokenValidity;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret); //specifies the algorithm used to sign and verify JWT signatures
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

        return JWT.create() //Begins building the token that will eventually be returned to the caller
                .withSubject(email)
                .withClaim("role", role)
                .withIssuer("TrainingMug")
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .sign(getAlgorithm()); //lets the server detect if someone changes the token's signed contents
    }

    //Accepts a token string and returns its decoded information if validation succeeds
    public DecodedJWT validateToken(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm()) //Creates a verifier that uses the configured signing algorithm and secret
                .withIssuer("TrainingMug")
                .build();
        return verifier.verify(token);
    }

    //method validates the token before extracting the email
    public String extractEmail(String token) {
        return validateToken(token).getSubject();
    }

    public String extractRole(String token) {
        return validateToken(token).getClaim("role").asString();
    }
}