package com.lisandro.birdwatching.repository;

import java.time.Month;
import java.util.List;

import com.lisandro.birdwatching.model.Chance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChanceRepository extends JpaRepository<Chance, Long> {
    // join all eagerly
    @Query("SELECT DISTINCT c " +
           "FROM Chance c " +
           "LEFT JOIN FETCH c.bird b " +
           "LEFT JOIN FETCH b.colors " +
           "LEFT JOIN FETCH c.reserve r " +
           "WHERE c.probability >= :probability " +
           "AND c.month = :month " +
           "ORDER BY r.name, c.probability")
    List<Chance> findByMonthAndProbabilityGreaterThanEqual(
            @Param("month") Month month, @Param("probability") double probability
    );
}
