package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.controller.DTO.Transferences.MovementDTO;

import com.ironhack.demobakingapp.model.Movement;
import com.ironhack.demobakingapp.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class FraudConditionsService {

    @Autowired
    MovementRepository movementRepository;

   /** Transactions made in 24 hours that total to more than 150% of the customers highest daily total transactions in any other 24 hour period. **/
   /** More than 2 transactions occurring on a single account within a 1 second period. **/

//   public boolean fraudConditions(MovementDTO movementDTO){
//
//       List<Movement> lastMovements = movementRepository.orderMovementsLimitTwo(movementDTO.getSenderAccount());
//
//       for (Movement m: lastMovements
//            ) {
//           System.out.println(m.getSenderAccount());
//           System.out.println(m.getQuantity());
//       }
//       System.out.println(lastMovements.size()
//       );
//       int lastTransferSecond = lastMovements.get(0).getTransferenceDate().getSecond();
//       int secondLastTransferSecond = lastMovements.get(1).getTransferenceDate().getSecond();
//
//       if ((secondLastTransferSecond - lastTransferSecond) < 1){
//          return false;
//       }
//
//       return true;
//   }

}
