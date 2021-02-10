package com.ironhack.demobakingapp.repository;

import com.ironhack.demobakingapp.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    /** Sum of total money moved by an account by day **/

   // SELECT SUM(amount) FROM movement WHERE sender_account = :variable AND transference_date >= now() - INTERVAL 1 DAY;



    /** Sum of movements made by an account in the last second **/

}
