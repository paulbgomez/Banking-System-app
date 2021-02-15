package com.ironhack.demobakingapp.service.interfaces.Accounts;

import com.ironhack.demobakingapp.controller.DTO.Transferences.BalanceDTO;
import com.ironhack.demobakingapp.controller.DTO.Transferences.MovementDTO;
import com.ironhack.demobakingapp.model.Movement;
import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {

    Movement transfer(MovementDTO movementDTO, String username);
    void incrementBalance(Long id, BigDecimal amount, String username);
    void decrementBalance(Long id, BigDecimal amount, String username);
    List<BalanceDTO> checkBalanceAll(Long id, String username);
    Movement transferToThirdParty(String name, String hashKey, MovementDTO movementDTO, String username);
    Movement transferFromThirdParty(String name, String hashKey, MovementDTO movementDTO);
    BalanceDTO checkBalance(Long id, String username);


}
