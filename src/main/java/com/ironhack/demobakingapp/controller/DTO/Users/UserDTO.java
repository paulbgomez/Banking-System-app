package com.ironhack.demobakingapp.controller.DTO.Users;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDTO {

    /** PARAMS **/

    @NotNull
    private String name;

    @NotNull
    private String username;


    @NotNull
    private String password;

    /** CONSTRUCTOR **/

    public UserDTO() {
    }

    public UserDTO( @NotNull String name,  @NotNull String username,  @NotNull String password) {
        setName(name);
        setUsername(username);
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
