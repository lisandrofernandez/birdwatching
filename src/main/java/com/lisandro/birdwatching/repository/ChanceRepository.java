package com.lisandro.birdwatching.repository;

import java.time.Month;
import java.util.List;

import com.lisandro.birdwatching.model.Chance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChanceRepository extends JpaRepository<Chance, Long> {
    List<Chance> findByMonthAndProbabilityGreaterThanEqual(Month month, double probability);
}
