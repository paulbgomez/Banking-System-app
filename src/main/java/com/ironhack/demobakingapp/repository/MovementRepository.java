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

    /** Max money transferred by an account in 24 hours **/
    @Query(value = "SELECT MAX(t.sum) FROM (SELECT DATE(transference_date) AS transference_day, SUM(quantity_amount) AS sum FROM movement WHERE sender_account = :id GROUP BY transference_day) AS t", nativeQuery = true)
    BigDecimal maxMoneyOneDay(@Param("id") Long id);

    /** Money transferred by an account in the last 24 hours **/
    @Query(value = "SELECT SUM(quantity_amount) AS sum FROM movement WHERE sender_account = :id AND transference_date >= NOW() - INTERVAL 1 DAY", nativeQuery = true)
    BigDecimal moneyLastDay(@Param("id") Long id);

}
