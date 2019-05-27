package com.lisandro.birdwatching.service;

import java.util.ArrayList;
import java.util.List;

import com.lisandro.birdwatching.dto.NaturalReserve_TupleDTO;
import com.lisandro.birdwatching.model.NaturalReserve;
import com.lisandro.birdwatching.repository.NaturalReserveRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NaturalReserveServiceImpl implements NaturalReserveService {

    @Autowired
    private NaturalReserveRepository naturalReserveRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NaturalReserve_TupleDTO> findAllTuples() {
        List<NaturalReserve> reserves = naturalReserveRepository.findAll();
        List<NaturalReserve_TupleDTO> dtos = new ArrayList<>(reserves.size());
        for (NaturalReserve reserve : reserves) {
            dtos.add(new NaturalReserve_TupleDTO(reserve.getId(), reserve.getName()));
        }
        return dtos;
    }


}
