package com.ironhack.demobakingapp.controller.interfaces;

import com.ironhack.demobakingapp.controller.DTO.Transferences.MovementDTO;
import com.ironhack.demobakingapp.model.Movement;
import java.security.Principal;

public interface IAccountController {
     Movement transfer(MovementDTO movementDTO, Principal principal);

     Movement transfer( String name, String hashKey,MovementDTO movementDTO, Principal principal);

        }
