package com.ironhack.demobakingapp.controller.DTO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String username;


    @NotEmpty
    @NotNull
    private String password;

    public UserDTO(@NotEmpty @NotNull String name, @NotEmpty @NotNull String username, @NotEmpty @NotNull String password) {
        this.name = name;
        this.username = username;
        setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
