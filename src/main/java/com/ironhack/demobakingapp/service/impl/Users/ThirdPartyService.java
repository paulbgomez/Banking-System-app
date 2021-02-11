package com.ironhack.demobakingapp.service.impl.Users;

import com.ironhack.demobakingapp.controller.DTO.Users.AccountHolderDTO;
import com.ironhack.demobakingapp.controller.DTO.Users.ThirdPartyDTO;
import com.ironhack.demobakingapp.enums.UserRole;
import com.ironhack.demobakingapp.model.Users.AccountHolder;
import com.ironhack.demobakingapp.model.Users.Role;
import com.ironhack.demobakingapp.model.Users.ThirdParty;
import com.ironhack.demobakingapp.repository.Users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ThirdPartyService {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public ThirdParty add(ThirdPartyDTO thirdPartyDTO){

        ThirdParty thirdParty = new ThirdParty(
                thirdPartyDTO.getName(),
                thirdPartyDTO.getHashKey()
        );
        Role role = new Role(UserRole.THIRD_PARTY);
        Set<Role> roles = Stream.of(role).collect(Collectors.toCollection(HashSet::new));
        thirdPartyRepository.save(thirdParty);
        return thirdParty;
    }
}
