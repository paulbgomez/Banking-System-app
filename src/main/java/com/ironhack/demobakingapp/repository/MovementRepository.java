package com.ironhack.demobakingapp.repository;

import com.ironhack.demobakingapp.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    /** Order 2 last movements by date **/
//    @Query( value = "SELECT transference_date FROM movement WHERE sender_account = :id ORDER BY STR_TO_DATE(transference_date,'%Y-%m-%d') DESC LIMIT 2", nativeQuery = true)
//    List<Movement> orderMovementsLimitTwo(@Param("id") Long id);

    /** Order last movement by date **/

}
