package com.example2.demo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .usernameParameter("login")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/success")
                .failureUrl("/fail")
                .and()
                .authorizeRequests()
                .requestMatchers("/success").authenticated()
                .requestMatchers("/user/**/games").authenticated()
                .requestMatchers("/user/**/history").authenticated()
                .and()
                .authorizeRequests()
                .requestMatchers("/restapi/v1/authenticate").permitAll()
                .requestMatchers("/restapi/v1/games").permitAll()
                .requestMatchers("/restapi/v1/register").permitAll()
                .requestMatchers("/restapi/v1/register/confirm/**").permitAll()
                .requestMatchers("/restapi/v1/users/**/histories").authenticated()
                .requestMatchers("/restapi/v1/users/**/games").authenticated()
                .requestMatchers("/restapi/v1/users/**/lends/**").authenticated()
                .requestMatchers("/restapi/v1/users/**/returns/**").authenticated()
                .requestMatchers("/restapi/v1/users/**/comments").authenticated()
                .requestMatchers("/restapi/v1/users/**/roles").authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}