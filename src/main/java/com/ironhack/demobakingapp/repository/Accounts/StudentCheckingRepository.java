package com.ironhack.demobakingapp.repository.Accounts;

import com.ironhack.demobakingapp.model.Accounts.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {
}
