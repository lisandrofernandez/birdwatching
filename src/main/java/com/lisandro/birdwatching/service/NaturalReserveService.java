package com.lisandro.birdwatching.service;

import java.util.List;

import com.lisandro.birdwatching.dto.NaturalReserveDTO;
import com.lisandro.birdwatching.dto.NaturalReserve_TupleDTO;

public interface NaturalReserveService {
    NaturalReserveDTO findByIdDTO(Long id);
    List<NaturalReserve_TupleDTO> findAllTuples();
    NaturalReserveDTO createDTO(NaturalReserveDTO reserveDTO);
    NaturalReserveDTO updateDTO(NaturalReserveDTO reserveDTO);
    void deleteById(Long id);
}
