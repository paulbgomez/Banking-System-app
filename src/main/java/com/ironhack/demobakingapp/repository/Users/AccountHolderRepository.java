package com.ironhack.demobakingapp.repository.Users;

import com.ironhack.demobakingapp.model.Users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
   Optional<AccountHolder> findByUsername(String username);
   Optional<AccountHolder> findByName(String name);
}
