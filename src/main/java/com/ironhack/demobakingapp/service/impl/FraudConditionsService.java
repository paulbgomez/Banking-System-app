package com.ironhack.demobakingapp.service.impl;


import com.ironhack.demobakingapp.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FraudConditionsService {
    @Autowired
    MovementRepository movementRepository;

   /** Transactions made in 24 hours that total to more than 150% of the customers highest daily total transactions in any other 24 hour period. **/

   /** More than 2 transactions occurring on a single account within a 1 second period. **/
}
