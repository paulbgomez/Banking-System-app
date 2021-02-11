package com.ironhack.demobakingapp.controller.DTO.Users;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {

    /** PARAMS **/

    @NotEmpty(message = "Name cannot be null")
    @NotNull
    private String name;

    @NotEmpty(message = "Username cannot be null")
    @NotNull
    private String username;


    @NotEmpty(message = "Password cannot be null")
    @NotNull
    private String password;

    /** CONSTRUCTOR **/

    public UserDTO(@NotEmpty @NotNull String name, @NotEmpty @NotNull String username, @NotEmpty @NotNull String password) {
        this.name = name;
        this.username = username;
        setPassword(password);
    }

    /** GETTERS & SETTERS **/

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
