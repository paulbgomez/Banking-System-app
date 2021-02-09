package com.ironhack.demobakingapp.controller.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AdminDTO extends UserDTO{

    public AdminDTO(@NotEmpty @NotNull String name, @NotEmpty @NotNull String username, @NotEmpty @NotNull String password) {
        super(name, username, password);
    }
}
