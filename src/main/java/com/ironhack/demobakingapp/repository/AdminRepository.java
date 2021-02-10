package com.ironhack.demobakingapp.repository;

import com.ironhack.demobakingapp.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Admin findByUsername(String username);
}
