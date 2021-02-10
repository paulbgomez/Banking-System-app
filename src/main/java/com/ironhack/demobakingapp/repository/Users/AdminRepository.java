package com.ironhack.demobakingapp.repository.Users;

import com.ironhack.demobakingapp.model.Users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);

}
