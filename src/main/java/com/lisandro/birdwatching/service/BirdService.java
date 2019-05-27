package com.lisandro.birdwatching.service;

import java.util.List;

import com.lisandro.birdwatching.dto.BirdDTO;
import com.lisandro.birdwatching.model.Bird;

public interface BirdService {
    Bird findById(Long id);
    List<BirdDTO> findAllTuples();
}
