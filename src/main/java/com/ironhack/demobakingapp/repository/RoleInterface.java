package com.ironhack.demobakingapp.repository;

import com.ironhack.demobakingapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleInterface extends JpaRepository<Role, Long> {
}
