package com.lisandro.birdwatching.service;

import java.util.List;

import com.lisandro.birdwatching.dto.NaturalReserveDTO;
import com.lisandro.birdwatching.dto.NaturalReserve_TupleDTO;
import com.lisandro.birdwatching.model.NaturalReserve;

public interface NaturalReserveService {
    NaturalReserve findById(Long id);
    NaturalReserveDTO findByIdDTO(Long id);
    List<NaturalReserve_TupleDTO> findAllTuples();
    NaturalReserve save(NaturalReserve reserve);
    NaturalReserveDTO createOrUpdateDTO(NaturalReserveDTO reserveDTO);
    void deleteById(Long id);
}
