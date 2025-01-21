package com.sasinet.sasinetTask.configuration;

import com.sasinet.sasinetTask.DTO.UserDTO;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    private final String secret_key;
    private long accessTokenValidity = 1 * 60 * 24;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    private final JwtParser jwtParser;

    public JwtUtil(@Value("${application.secret}") String secret_key) {
        this.secret_key = secret_key;
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }
    // Creates a JWT token with user details and roles.

    public String createToken(UserDTO user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("userName", user.getUsername());
        String[] types = {"USER", "ADMIN"};
        claims.put("roles", types);

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    //Parses and returns claims from a JWT token.

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    // Resolves claims from an HTTP request token.

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    // Extracts the JWT token from the HTTP request header.

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        System.out.println("bearer token: " + bearerToken);

        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

   // Validates the expiration of token claims.

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    // Retrieves the email (subject) from token claims.

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    // Retrieves roles from token claims.

    public List<String> getRoles(Claims claims) {
        return (ArrayList<String>) claims.get("roles");
    }




}
