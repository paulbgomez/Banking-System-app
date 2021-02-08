package com.ironhack.demobakingapp.security;

import com.ironhack.demobakingapp.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable().authorizeRequests()
                .mvcMatchers("/blogpost/").hasAnyRole("ADMIN", "CONTRIBUTOR")
                .mvcMatchers("/blogpost/**/update").hasAnyRole("ADMIN", "CONTRIBUTOR")
                .mvcMatchers("/blogpost/**/delete").hasRole("ADMIN")
                .mvcMatchers("/author/").hasRole("ADMIN")
                .mvcMatchers("/author/**/update").hasAnyRole("ADMIN", "CONTRIBUTOR")
                .mvcMatchers("/author/**/delete").hasRole("ADMIN")
                .anyRequest().permitAll();
    }
}

