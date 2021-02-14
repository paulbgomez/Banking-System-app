package com.ironhack.demobakingapp.service.impl;

import com.ironhack.demobakingapp.model.Movement;
import com.ironhack.demobakingapp.repository.MovementRepository;
import com.ironhack.demobakingapp.service.interfaces.IMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovementService implements IMovementService {

    @Autowired
    private MovementRepository movementRepository;

    public Movement save(Movement movement) {
        return movementRepository.save(movement);
    }
}
