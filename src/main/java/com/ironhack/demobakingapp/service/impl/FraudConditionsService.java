package com.ironhack.demobakingapp.service.impl;


import com.ironhack.demobakingapp.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FraudConditionsService {
    @Autowired
    MovementRepository movementRepository;

//    public boolean firstCondition(MovementDTO movementDTO){
//        BigDecimal sumLastDayAmounts = movementRepository.sumLastDayTransferences(newTransference.getOriginId());
//        List<BigDecimal> sumOfTransferenceByDay = transferenceRepository.sumOfTransferenceByDay(newTransference.getOriginId());
//
//        BigDecimal max = sumOfTransferenceByDay.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
//
//
//        boolean result;
//
//        if (max.compareTo(BigDecimal.ZERO) == 0 || max.multiply(new BigDecimal("1.5")).compareTo(newTransference.getAmount()) > 0 ) {
//            result = true;
//        } else {
//            result = false;
//        }
//        return result;
//    }
//
//    public boolean secondCondition(NewTransference newTransference){
//        boolean result;
//
//        List<Transference> transferences = transferenceRepository.lastSecondTransferences(newTransference.getOriginId());
//        if(transferences.size() != 0 ){
//            result = false;
//        }else{
//            result = true;
//        }
//        return result;
//    }
}
