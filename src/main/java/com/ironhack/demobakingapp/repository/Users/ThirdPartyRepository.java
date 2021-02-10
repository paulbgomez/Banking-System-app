package com.ironhack.demobakingapp.repository.Users;

import com.ironhack.demobakingapp.model.Users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
}
