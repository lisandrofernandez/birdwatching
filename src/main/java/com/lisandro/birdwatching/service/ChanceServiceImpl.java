package com.lisandro.birdwatching.service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.lisandro.birdwatching.dto.ChanceDTO;
import com.lisandro.birdwatching.model.Bird;
import com.lisandro.birdwatching.model.Chance;
import com.lisandro.birdwatching.model.NaturalReserve;
import com.lisandro.birdwatching.repository.ChanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChanceServiceImpl implements ChanceService {

    @Autowired
    private ChanceRepository chanceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ChanceDTO> findByMonthDTO(Month month) {
        List<Chance> chances = chanceRepository.findByMonthAndProbabilityGreaterThanEqual(
                month, ChanceService.MIN_PROBABILITY);
        List<ChanceDTO> chancesDTO = new ArrayList<>(chances.size());
        for (Chance chance : chances) {
            chancesDTO.add(toDTO(chance));
        }
        return chancesDTO;
    }

    private ChanceDTO toDTO(Chance chance) {
        ChanceDTO chanceDTO = new ChanceDTO();
        chanceDTO.setMonth(chance.getMonth());
        Bird bird = chance.getBird();
        ChanceDTO.BirdDTO birdDTO = chanceDTO.new BirdDTO();
        birdDTO.setName(bird.getName());
        birdDTO.setSize(bird.getSize());
        birdDTO.setPhotoURL(bird.getPhotoURL());
        birdDTO.setColors(bird.getColors());
        chanceDTO.setBird(birdDTO);
        NaturalReserve reserve = chance.getReserve();
        ChanceDTO.NaturalReserveDTO reserveDTO = chanceDTO.new NaturalReserveDTO();
        reserveDTO.setName(reserve.getName());
        chanceDTO.setReserve(reserveDTO);
        return chanceDTO;
    }

}
