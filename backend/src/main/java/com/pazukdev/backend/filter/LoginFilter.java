package com.pazukdev.backend.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pazukdev.backend.dto.CredentialsDto;
import com.pazukdev.backend.service.TokenAuthenticationService;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Siarhei Sviarkaltsau
 */
//@Component
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_URL = "/login";

    private final TokenAuthenticationService tokenAuthenticationService;
    private final Jackson2ObjectMapperBuilder objectMapperBuilder;
    private final PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;

    public LoginFilter(final TokenAuthenticationService tokenAuthenticationService,
                       final AuthenticationManager authenticationManager,
                       final Jackson2ObjectMapperBuilder objectMapperBuilder,
                       PasswordEncoder passwordEncoder) {
        super(new AntPathRequestMatcher(LOGIN_URL));
        this.objectMapperBuilder = objectMapperBuilder;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.passwordEncoder = passwordEncoder;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response)
            throws AuthenticationException, IOException {
        final CredentialsDto credentials = getObjectMapper().readValue(request.getInputStream(), CredentialsDto.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + "ADMIN"));
        String pass = credentials.getPassword();
        pass = passwordEncoder.encode(pass);

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getAlias(), pass, authorities));
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authResult) {
        // Write Authorization to Headers of Response.
        final String alias = authResult.getName();
        final String roles = authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        tokenAuthenticationService.addAuthentication(response, alias, roles);
    }

    private ObjectMapper getObjectMapper() {
        return objectMapper == null ? objectMapper = objectMapperBuilder.build() : objectMapper;
    }

}
