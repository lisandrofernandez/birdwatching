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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lisandro.birdwatching.core.Assert;
import com.lisandro.birdwatching.core.BusinessException;
import com.lisandro.birdwatching.dto.NaturalReserveDTO;
import com.lisandro.birdwatching.dto.NaturalReserveTupleDTO;
import com.lisandro.birdwatching.model.NaturalReserve;
import com.lisandro.birdwatching.model.Region;
import com.lisandro.birdwatching.repository.NaturalReserveRepository;
import com.lisandro.birdwatching.repository.RegionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link NaturalReserveService} implementation.
 *
 * @author Lisandro Fernandez
 */
@Service
@Transactional
public class NaturalReserveServiceImpl implements NaturalReserveService {

    private final NaturalReserveRepository naturalReserveRepository;
    private final RegionRepository regionRepository;

    /**
     * Constructs a {@link NaturalReserveServiceImpl}.
     *
     * @param naturalReserveRepository  used by the service
     * @param regionRepository  used by the service
     */
    public NaturalReserveServiceImpl(NaturalReserveRepository naturalReserveRepository,
                                     RegionRepository regionRepository) {
        this.naturalReserveRepository = naturalReserveRepository;
        this.regionRepository = regionRepository;
    }

    /*
     * (non-Javadoc)
     * @see NaturalReserveService#findById(Long)
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NaturalReserveDTO> findById(long id) {
        return naturalReserveRepository.findById(id).map(this::toDTO);
    }

    /*
     * (non-Javadoc)
     * @see NaturalReserveService#findAllTuples()
     */
    @Override
    @Transactional(readOnly = true)
    public List<NaturalReserveTupleDTO> findAllTuples() {
        List<NaturalReserve> reserves = naturalReserveRepository.findAll();
        List<NaturalReserveTupleDTO> dtos = new ArrayList<>(reserves.size());
        for (NaturalReserve reserve : reserves) {
            dtos.add(new NaturalReserveTupleDTO(reserve.getId(), reserve.getName()));
        }
        return dtos;
    }

    /*
     * (non-Javadoc)
     * @see NaturalReserveService#create(NaturalReserveDTO)
     */
    @Override
    public NaturalReserveDTO create(NaturalReserveDTO naturalReserveDTO) {
        Assert.notNull(naturalReserveDTO, "NaturalReserveDTO must not be null");
        naturalReserveDTO.setId(null); // omit ID to avoid an unwanted update
        NaturalReserve naturalReserve = fromDTO(naturalReserveDTO);
        naturalReserve.normalizeAndValidate();
        return toDTO(naturalReserveRepository.save(naturalReserve));
    }

    /*
     * (non-Javadoc)
     * @see NaturalReserveService#update(NaturalReserveDTO)
     */
    @Override
    public NaturalReserveDTO update(NaturalReserveDTO naturalReserveDTO) {
        Assert.notNull(naturalReserveDTO, "NaturalReserveDTO must not be null");
        Long id = naturalReserveDTO.getId();
        BusinessException.notNull(id, "Natural reserve ID must not be null");
        NaturalReserve naturalReserve = naturalReserveRepository.findById(id).orElseThrow(() ->
                new NaturalReserveNotFoundException("There is no natural reserve with ID = " + id)
        );
        fromDTO(naturalReserveDTO, naturalReserve);
        naturalReserve.normalizeAndValidate();
        return toDTO(naturalReserve);
    }

    /*
     * (non-Javadoc)
     * @see NaturalReserveService#deleteById(Long)
     */
    @Override
    public boolean deleteById(long id) {
        Optional<NaturalReserve> optionalNaturalReserve = naturalReserveRepository.findById(id);
        if (optionalNaturalReserve.isEmpty()) {
            return false;
        }
        naturalReserveRepository.delete(optionalNaturalReserve.get());
        return true;
    }

    private NaturalReserveDTO toDTO(NaturalReserve naturalReserve) {
        NaturalReserveDTO naturalReserveDTO = new NaturalReserveDTO();
        naturalReserveDTO.setId(naturalReserve.getId());
        naturalReserveDTO.setName(naturalReserve.getName());
        naturalReserveDTO.setRegionId(naturalReserve.getRegion().getId());
        return naturalReserveDTO;
    }

    private void fromDTO(NaturalReserveDTO naturalReserveDTO, NaturalReserve naturalReserve) {
        Long regionId = naturalReserveDTO.getRegionId();
        BusinessException.notNull(regionId, "Region ID must not be null");
        Region region = regionRepository.findById(regionId).orElseThrow(
                () -> new BusinessException("There is no region with ID = " + regionId)
        );
        naturalReserve.setId(naturalReserveDTO.getId());
        naturalReserve.setName(naturalReserveDTO.getName());
        naturalReserve.setRegion(region);
    }

    private NaturalReserve fromDTO(NaturalReserveDTO naturalReserveDTO) {
        NaturalReserve naturalReserve = new NaturalReserve();
        fromDTO(naturalReserveDTO, naturalReserve);
        return naturalReserve;
    }

}
