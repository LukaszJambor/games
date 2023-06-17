package com.example2.demo.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .usernameParameter("login")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/success")
                .failureUrl("/fail")
                .and()
                .authorizeRequests()
                .antMatchers("/success").authenticated()
                .antMatchers("/user/**/games").authenticated()
                .antMatchers("/user/**/history").authenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/restapi/v1/authenticate").permitAll()
                .antMatchers("/restapi/v1/games").permitAll()
                .antMatchers("/restapi/v1/register").permitAll()
                .antMatchers("/restapi/v1/register/confirm/**").permitAll()
                .antMatchers("/restapi/v1/users/**/histories").authenticated()
                .antMatchers("/restapi/v1/users/**/games").authenticated()
                .antMatchers("/restapi/v1/users/**/lends/**").authenticated()
                .antMatchers("/restapi/v1/users/**/returns/**").authenticated()
                .antMatchers("/restapi/v1/users/**/comments").authenticated()
                .antMatchers("/restapi/v1/users/**/roles").authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}