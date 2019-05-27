package com.lisandro.birdwatching.service;

import java.util.List;

import com.lisandro.birdwatching.dto.NaturalReserve_TupleDTO;

public interface NaturalReserveService {
    List<NaturalReserve_TupleDTO> findAllTuples();
}
