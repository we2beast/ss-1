package com.poluhin.ss.demo.converter;

import com.poluhin.ss.demo.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


public class JwtAuthConverter implements AuthenticationConverter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;


    public JwtAuthConverter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        logger.trace(String.format("request: %s, %s", request.getRequestURI(), request.getQueryString()));

        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null && authorization.startsWith("Bearer ")) {
            var token = authorization.replace("Bearer ", "");

            if (!this.jwtService.validateAccessToken(token)) {
                throw new BadCredentialsException("token is invalid");
            }

            Claims claims = this.jwtService.parseAccessClaims(token);

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(claims.getSubject());

            return UsernamePasswordAuthenticationToken.unauthenticated(
                userDetails.getUsername(),
                "password"
            );
        }

        return null;
    }

}
