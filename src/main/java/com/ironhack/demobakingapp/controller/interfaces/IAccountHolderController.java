package com.ironhack.demobakingapp.controller.interfaces;

import com.ironhack.demobakingapp.controller.DTO.Transferences.MovementDTO;
import com.ironhack.demobakingapp.model.Movement;
import java.security.Principal;

public interface IAccountHolderController {

    Movement transfer(MovementDTO movementDTO, Principal principal);
}
