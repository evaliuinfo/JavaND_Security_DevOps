package com.example.demo.security;

import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import com.auth0.jwt.JWT;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter (AuthenticationManager authenticationManager) {
        authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
            log.info("Attempting authentication for user {}", credentials.getUsername());
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword(), new ArrayList<>())
            );
        } catch (JsonMappingException e) {
            e.printStackTrace();
            log.error("Json mapping failed: ", e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            e.printStackTrace();
            log.error("Json Parse failed: ", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Authentication attempt failed: ", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        String token = JWT.create()
    }
}
