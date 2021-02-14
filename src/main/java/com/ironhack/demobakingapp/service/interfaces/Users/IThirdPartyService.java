package com.ironhack.demobakingapp.service.interfaces.Users;

import com.ironhack.demobakingapp.controller.DTO.Users.ThirdPartyDTO;
import com.ironhack.demobakingapp.model.Users.ThirdParty;

public interface IThirdPartyService {
    ThirdParty add(ThirdPartyDTO thirdPartyDTO);

    ThirdParty findByName(String name);
}
