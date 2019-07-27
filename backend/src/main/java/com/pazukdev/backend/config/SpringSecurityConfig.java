package com.pazukdev.backend.config;

import com.pazukdev.backend.filter.JwtAuthenticationFilter;
import com.pazukdev.backend.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Siarhei Sviarkaltsau
 */
@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static String REALM="MY_TEST_REALM";

    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/**").permitAll()
                //.antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/css/**", "/index", "/js/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                //.and().httpBasic().realmName(REALM).authenticationEntryPoint(authenticationEntryPoint)
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().permitAll();
//                .and()
//                .logout()
//                .and()
//                .addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
//                .csrf().disable()
//                .authorizeRequests().anyRequest().authenticated()
//                .antMatchers("/css/**", "/index", "/js/**").permitAll()
//                .antMatchers("/registration**", "/login**").permitAll()
//                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
//                .antMatchers("/**").authenticated()
//                .and().formLogin().loginPage("/login").permitAll()
//                .and().logout();
                //.defaultSuccessUrl("/motorcycle/list", true)
//                .and()
//                .addFilterBefore(new LoginFilter(tokenAuthenticationService, authenticationManager(), objectMapperBuilder, passwordEncoder),
//                        UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new AuthenticationFilter(tokenAuthenticationService),
//                        UsernamePasswordAuthenticationFilter.class);
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("password")).roles("ADMIN")
                .and()
                .withUser("user").password(passwordEncoder.encode("password")).roles("USER");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
