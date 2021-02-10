package com.ironhack.demobakingapp.controller.DTO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ThirdPartyDTO{

    /** PARAMS **/

    @NotNull(message = "Name cannot be null")
    @NotEmpty
    private String name;
    @NotNull(message = "Hashkey cannot be null")
    @NotEmpty
    private String hashKey;

    /** CONSTRUCTOR **/

    public ThirdPartyDTO(@NotNull @NotEmpty String name, @NotNull @NotEmpty String hashKey) {
        setName(name);
        setHashKey(hashKey);
    }

    /** GETTERS & SETTERS **/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.hashKey = passwordEncoder.encode(hashKey);
    }
}
