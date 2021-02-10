package com.ironhack.demobakingapp.repository;

import com.ironhack.demobakingapp.model.Users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
    public Optional<AccountHolder> findByUsername(String username);
    public Optional<AccountHolder> findByName(String name);
}
