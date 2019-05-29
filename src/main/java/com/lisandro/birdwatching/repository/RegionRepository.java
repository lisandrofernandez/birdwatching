package com.lisandro.birdwatching.repository;

import com.lisandro.birdwatching.model.Region;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
