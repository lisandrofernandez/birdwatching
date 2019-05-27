package com.lisandro.birdwatching.repository;


import com.lisandro.birdwatching.model.NaturalReserve;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NaturalReserveRepository extends JpaRepository<NaturalReserve, Long> {
}
