package com.ironhack.demobakingapp.controller.DTO.Users;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AdminDTO extends UserDTO{

    /** CONSTRUCTOR **/

    public AdminDTO() {
    }

    public AdminDTO(@NotEmpty @NotNull String name, @NotEmpty @NotNull String username, @NotEmpty @NotNull String password) {
        super(name, username, password);
    }
}
