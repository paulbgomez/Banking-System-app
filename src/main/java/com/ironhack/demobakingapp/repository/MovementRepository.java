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

    /** Order last movements by date **/
    List<Movement> findBySenderAccountIdOrderByTransferenceDateDesc(Long id);

    /** Value all movements from one account **/
    @Query(value = "SELECT MAX(t.sum) FROM (SELECT DATE(transference_date) AS transference_date, SUM(quantity_amount) AS sum FROM movement WHERE sender_account = :id GROUP BY transference_date) AS t", nativeQuery = true)
    List<Movement> sumMovementsOneAccount(@Param("id") Long id);

    @Query(value = "SELECT transference_date, SUM(quantity_amount) AS sum FROM movement WHERE sender_account = :id AND transference_date >= NOW() - INTERVAL 1 DAY", nativeQuery = true)
    List<Movement> movementsByDay(@Param("id") Long id);
}
