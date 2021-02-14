package com.ironhack.demobakingapp.repository.Accounts;

import com.ironhack.demobakingapp.model.Accounts.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
