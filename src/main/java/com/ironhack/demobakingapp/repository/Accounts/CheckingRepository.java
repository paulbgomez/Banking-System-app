package com.ironhack.demobakingapp.repository.Accounts;

import com.ironhack.demobakingapp.model.Accounts.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {
}
