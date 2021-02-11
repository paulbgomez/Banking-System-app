package com.ironhack.demobakingapp.service.interfaces.Accounts;

import com.ironhack.demobakingapp.model.Movement;
import org.springframework.security.core.Authentication;

public interface IAccountService {

    Movement transfer(Movement movement, Authentication authentication);
}
