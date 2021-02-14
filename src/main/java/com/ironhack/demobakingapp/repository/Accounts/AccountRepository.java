package com.ironhack.demobakingapp.repository.Accounts;

import com.ironhack.demobakingapp.model.Accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
