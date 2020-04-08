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
     * @see NaturalReserveService#findByIdDTO(Long)
     */
    @Override
    @Transactional(readOnly = true)
    public NaturalReserveDTO findByIdDTO(Long id) {
        Assert.notNull(id, "Natural reserve ID must not be null");
        return naturalReserveRepository.findById(id).map(this::toDTO).orElse(null);
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
     * @see NaturalReserveService#createDTO(NaturalReserveDTO)
     */
    @Override
    @Transactional
    public NaturalReserveDTO createDTO(NaturalReserveDTO reserveDTO) {
        Assert.notNull(reserveDTO, "NaturalReserveDTO must not be null");
        reserveDTO.setId(null); // omit ID to avoid an unwanted update
        NaturalReserve reserve = fromDTO(reserveDTO);
        reserve.normalizeAndValidate();
        return toDTO(naturalReserveRepository.save(reserve));
    }

    /*
     * (non-Javadoc)
     * @see NaturalReserveService#updateDTO(NaturalReserveDTO)
     */
    @Override
    @Transactional
    public NaturalReserveDTO updateDTO(NaturalReserveDTO reserveDTO) {
        Assert.notNull(reserveDTO, "NaturalReserveDTO must not be null");
        Long id = reserveDTO.getId();
        BusinessException.notNull(id, "Natural reserve ID must not be null");
        NaturalReserve reserve = naturalReserveRepository.findById(id).orElseThrow(() ->
                new NaturalReserveNotFoundException("There is no natural reserve with ID = " + id)
        );
        fromDTO(reserveDTO, reserve);
        reserve.normalizeAndValidate();
        return toDTO(reserve);
    }

    /*
     * (non-Javadoc)
     * @see NaturalReserveService#deleteById(Long)
     */
    @Override
    public void deleteById(Long id) {
        Assert.notNull(id, "Natural reserve ID must not be null");
        NaturalReserve reserve = naturalReserveRepository.findById(id).orElseThrow(() ->
                new NaturalReserveNotFoundException("There is no natural reserve with ID = " + id)
        );
        naturalReserveRepository.delete(reserve);
    }

    private NaturalReserveDTO toDTO(NaturalReserve reserve) {
        NaturalReserveDTO reserveDTO = new NaturalReserveDTO();
        reserveDTO.setId(reserve.getId());
        reserveDTO.setName(reserve.getName());
        reserveDTO.setRegionId(reserve.getRegion().getId());
        return reserveDTO;
    }

    private void fromDTO(NaturalReserveDTO reserveDTO, NaturalReserve reserve) {
        reserve.setId(reserveDTO.getId());
        reserve.setName(reserveDTO.getName());
        Long regionId = reserveDTO.getRegionId();
        BusinessException.notNull(regionId, "Region ID must not be null");
        Region region = regionRepository.findById(regionId).orElseThrow(
                () -> new BusinessException("There is no region with ID = " + regionId)
        );
        reserve.setRegion(region);
    }

    private NaturalReserve fromDTO(NaturalReserveDTO reserveDTO) {
        NaturalReserve reserve = new NaturalReserve();
        fromDTO(reserveDTO, reserve);
        return reserve;
    }

}
