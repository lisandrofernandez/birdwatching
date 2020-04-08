/*
 * Copyright (c) 2020 Lisandro Fernandez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.lisandro.birdwatching.service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.lisandro.birdwatching.core.Assert;
import com.lisandro.birdwatching.dto.ChanceDTO;
import com.lisandro.birdwatching.model.Bird;
import com.lisandro.birdwatching.model.Chance;
import com.lisandro.birdwatching.model.NaturalReserve;
import com.lisandro.birdwatching.repository.ChanceRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link ChanceService} implementation.
 *
 * @author Lisandro Fernandez
 */
@Service
@Transactional
public class ChanceServiceImpl implements ChanceService {

    private final ChanceRepository chanceRepository;

    /**
     * Constructs a {@link ChanceServiceImpl}.
     *
     * @param chanceRepository  used by the service.
     */
    public ChanceServiceImpl (ChanceRepository chanceRepository) {
        this.chanceRepository = chanceRepository;
    }

    /*
     * (non-Javadoc)
     * @see ChanceService#findByMonthDTO(Long)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChanceDTO> findByMonthDTO(Month month) {
        Assert.notNull(month, "Month must not be null");
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
        ChanceDTO.BirdDTO birdDTO = new ChanceDTO.BirdDTO();
        birdDTO.setName(bird.getName());
        birdDTO.setSize(bird.getSize());
        birdDTO.setPhotoURL(bird.getPhotoURL());
        birdDTO.setColors(bird.getColors());
        chanceDTO.setBird(birdDTO);
        NaturalReserve reserve = chance.getReserve();
        ChanceDTO.NaturalReserveDTO reserveDTO = new ChanceDTO.NaturalReserveDTO();
        reserveDTO.setName(reserve.getName());
        chanceDTO.setReserve(reserveDTO);
        return chanceDTO;
    }

}
