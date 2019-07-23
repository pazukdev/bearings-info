package com.pazukdev.backend.config;

import com.pazukdev.backend.filter.LoginFilter;
import com.pazukdev.backend.service.TokenAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Siarhei Sviarkaltsau
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthProviderImpl authProvider;
    //private final DataSource dataSource;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final Jackson2ObjectMapperBuilder objectMapperBuilder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/**").authenticated()
                .antMatchers("/css/**", "/index", "/js/**").permitAll()
                .antMatchers("/registration**", "/login**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().logout()
                .and().formLogin()
                .loginPage("/login")
                //.defaultSuccessUrl("/motorcycle/list", true)
                .and()
                .addFilterBefore(new LoginFilter(tokenAuthenticationService, authenticationManager(), objectMapperBuilder),
                        UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore(new AuthenticationFilter(tokenAuthenticationService),
//                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
//                .jdbcAuthentication().dataSource(dataSource)
//                .passwordEncoder(passwordEncoder())
//                .usersByUsernameQuery("select alias, password, enabled from user where alias=?")
//                .authoritiesByUsernameQuery("select alias, role from user where alias=?");
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
