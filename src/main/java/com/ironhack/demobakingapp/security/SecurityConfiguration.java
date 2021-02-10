package com.ironhack.demobakingapp.security;

import com.ironhack.demobakingapp.enums.UserRole;
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

import javax.management.relation.RoleStatus;

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
                .mvcMatchers("/new/admin/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/new/account-holder/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/account-balance/all/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/account-balance/**/single/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/admin/savings/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/check-balance/savings/**").hasAnyRole(UserRole.ADMIN.toString(), UserRole.ACCOUNT_HOLDER.toString())
                .mvcMatchers("/check-balance/admin/savings/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/admin/checkings/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/admin/credit-card/**").hasRole(UserRole.ADMIN.toString())
                .mvcMatchers("/savings/**").hasAnyRole(UserRole.ADMIN.toString(), UserRole.ACCOUNT_HOLDER.toString())
                .anyRequest().permitAll();
    }
}

