package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.controller.DTO.Transferences.MovementDTO;
import com.ironhack.demobakingapp.model.Movement;
import com.ironhack.demobakingapp.repository.Accounts.AccountRepository;
import com.ironhack.demobakingapp.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class FraudConditionsService {

    @Autowired
    MovementRepository movementRepository;

    @Autowired
    AccountRepository accountRepository;

   /** More than 2 transactions occurring on a single account within a 1 second period. **/

   public boolean fraudConditionSeconds(MovementDTO movementDTO){

       List<Movement> lastMovements = movementRepository.findBySenderAccountIdOrderByTransferenceDateDesc(movementDTO.getSenderAccount());
    if (lastMovements.size() >= 2){
    Timestamp ts1 = Timestamp.valueOf(lastMovements.get(0).getTransferenceDate());
    Timestamp ts2 = Timestamp.valueOf(lastMovements.get(1).getTransferenceDate());

        if (ts1.getTime() - ts2.getTime() <= 1){
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
   }

    /** Transactions made in 24 hours that total to more than 150% of the customers highest daily total transactions in any other 24 hour period. **/

    public boolean fraudConditionMaxMoney(MovementDTO movementDTO){

       if (movementRepository.moneyLastDay(movementDTO.getSenderAccount()) == null ||
               movementRepository.maxMoneyOneDay(movementDTO.getSenderAccount()) == null){
           return false;
       } else {
           BigDecimal maxMoney24Hours = movementRepository.moneyLastDay(movementDTO.getSenderAccount()).add(movementDTO.getAmount());
           BigDecimal maxMoneyDayMultiply150 = movementRepository.maxMoneyOneDay(movementDTO.getSenderAccount()).multiply(new BigDecimal(1.5));

           if(maxMoney24Hours.compareTo(maxMoneyDayMultiply150) == 1){
               return true;
           } else {
               return false;
           }
       }
   }

}
