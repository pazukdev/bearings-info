package com.pazukdev.backend.config;

import com.pazukdev.backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

/**
 * @author Siarhei Sviarkaltsau
 */
@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //private final AuthProviderImpl authProvider;
//    private final DataSource dataSource;
//    private final TokenAuthenticationService tokenAuthenticationService;
//    private final Jackson2ObjectMapperBuilder objectMapperBuilder;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .antMatchers("/css/**", "/index", "/js/**").permitAll()
                .antMatchers("/registration**", "/login**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/**").hasRole("ADMIN")
                //.and().logout()
                .and().formLogin()
                .loginPage("/login").permitAll()
                .and().csrf().disable();
                //.defaultSuccessUrl("/motorcycle/list", true)
//                .and()
//                .addFilterBefore(new LoginFilter(tokenAuthenticationService, authenticationManager(), objectMapperBuilder, passwordEncoder),
//                        UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new AuthenticationFilter(tokenAuthenticationService),
//                        UsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth
//
//                .authenticationProvider(authProvider);
//                .jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select alias, password, enabled from user where alias=?")
//                .authoritiesByUsernameQuery("select alias, role from user where alias=?");
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Bean
//    public AuthenticationManager customAuthenticationManager() throws Exception {
//        return authManager;
//    }

    public SpringSecurityConfig() {
        super(false);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(new AuthProviderImpl()));
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .and().inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("123")).roles("ADMIN");
    }

}
