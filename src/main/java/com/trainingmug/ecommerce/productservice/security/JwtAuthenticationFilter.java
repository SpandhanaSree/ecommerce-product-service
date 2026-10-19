package com.trainingmug.ecommerce.productservice.security;

import com.auth0.jwt.interfaces.DecodedJWT; //DecodedJWT represents a decoded token

import jakarta.servlet.FilterChain; //allows the request to continue to the next filter or application stage
import jakarta.servlet.ServletException; //represents a servlet-related exception
import jakarta.servlet.http.HttpServletRequest;//contains information about the incoming request, such as its headers and URL
import jakarta.servlet.http.HttpServletResponse; //lets your application send a response to the client.

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override //class is overriding a method inherited from OncePerRequestFilter
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain) 
      throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); //The prefix Bearer  contains seven characters

            try {
                DecodedJWT decodedJWT = jwtUtil.validateToken(token); //checks whether the JWT is valid
                String email = decodedJWT.getSubject();
                String role = decodedJWT.getClaim("role").asString();

                if (email != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        email, null, List.of(new SimpleGrantedAuthority(role))
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception exception) {

                //Clears the current security context so invalid-token processing doesn't leave authentication information behind.
                SecurityContextHolder.clearContext(); 
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //Sets the HTTP status to 401 Unauthorized.
                response.setContentType("application/json"); //Tells the client that the response body is JSON.
                response.getWriter().write("{\"message\":\"Invalid or expired JWT token\"}");
                return;
            }
        }
        filterChain.doFilter(request, response); //This allows the request and response to continue through the remaining filter chain
    }
}

/*
    - A valid JWT is authenticated, and the request continues.
    - A missing token doesn't enter the token-validation block, and the request continues.
    - An invalid JWT enters the catch block, receives a 401 response, and returns without continuing through this call.
 */