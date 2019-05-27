package com.lisandro.birdwatching.repository;


import com.lisandro.birdwatching.model.Bird;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BirdRepository extends JpaRepository<Bird, Long> {
}
