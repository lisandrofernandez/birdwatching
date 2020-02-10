package com.lisandro.birdwatching.service;

import java.util.List;

import com.lisandro.birdwatching.dto.Bird_TupleDTO;

public interface BirdService {
    List<Bird_TupleDTO> findAllTuples();
}
