package com.ironhack.demobakingapp.security;

import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.service.impl.Users.CustomUserDetailsService;
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
                .mvcMatchers("/new/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/admin/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/savings/check-balance/**").hasRole(UserRole.ACCOUNT_HOLDER.toString())
                .mvcMatchers("/new-transference/**").hasAnyRole(UserRole.ACCOUNT_HOLDER.toString(), UserRole.THIRD_PARTY.toString())
                .anyRequest().permitAll();
    }
}

