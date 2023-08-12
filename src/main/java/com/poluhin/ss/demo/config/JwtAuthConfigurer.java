package com.poluhin.ss.demo.config;

import com.poluhin.ss.demo.converter.JwtAuthConverter;
import com.poluhin.ss.demo.filter.JwtAuthFilter;
import com.poluhin.ss.demo.handler.JwtAuthenticationSuccessHandler;
import com.poluhin.ss.demo.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JwtAuthConfigurer extends AbstractHttpConfigurer<JwtAuthConfigurer, HttpSecurity> {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtAuthConfigurer(JwtService jwtService, UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void configure(HttpSecurity builder) {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter();
        jwtAuthFilter.setAuthenticationManager(authenticationManager);
        jwtAuthFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler(jwtService));

        PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(
            authenticationManager,
            new JwtAuthConverter(jwtService, userDetailsService)
        );
        authenticationFilter.setSuccessHandler((request, response, authentication) -> CsrfFilter.skipRequest(request));

        builder.addFilterAfter(jwtAuthFilter, ExceptionTranslationFilter.class)
			.addFilterBefore(authenticationFilter, CsrfFilter.class)
            .authenticationProvider(authenticationProvider);
    }

}
