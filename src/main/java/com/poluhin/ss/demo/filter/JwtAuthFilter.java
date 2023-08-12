package com.poluhin.ss.demo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poluhin.ss.demo.domain.model.LoginRequest;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ServletInputStream inputStream = request.getInputStream();
            LoginRequest loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);
            logger.trace("loginRequest: " + loginRequest);

            return this.getAuthenticationManager().authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("Can not parse login request");
        }
    }

}
