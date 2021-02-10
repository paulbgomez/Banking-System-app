package com.ironhack.demobakingapp.repository;

import com.ironhack.demobakingapp.model.Users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
