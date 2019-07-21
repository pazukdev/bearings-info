package com.pazukdev.backend.filter;

import com.pazukdev.backend.service.TokenAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Siarhei Sviarkaltsau
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends GenericFilterBean {

    private static final String SECRET = "D90#11%fhBpP";
    private static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    private final TokenAuthenticationService tokenAuthenticationService;

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        Authentication authentication;
        try {
            authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) request);
        } catch (Exception e) {
            authentication = null;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

}
