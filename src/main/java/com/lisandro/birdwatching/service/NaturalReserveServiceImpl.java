package com.lisandro.birdwatching.service;

import java.util.ArrayList;
import java.util.List;

import com.lisandro.birdwatching.core.Assert;
import com.lisandro.birdwatching.core.BusinessException;
import com.lisandro.birdwatching.dto.NaturalReserveDTO;
import com.lisandro.birdwatching.dto.NaturalReserve_TupleDTO;
import com.lisandro.birdwatching.model.NaturalReserve;
import com.lisandro.birdwatching.model.Region;
import com.lisandro.birdwatching.repository.NaturalReserveRepository;
import com.lisandro.birdwatching.repository.RegionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NaturalReserveServiceImpl implements NaturalReserveService {

    @Autowired
    private NaturalReserveRepository naturalReserveRepository;
    @Autowired
    private RegionRepository regionRepository;

    @Override
    @Transactional(readOnly = true)
    public NaturalReserveDTO findByIdDTO(Long id) {
        Assert.notNull(id, "Natural reserve ID must not be null");
        return naturalReserveRepository.findById(id).map(this::toDTO).orElse(null);
    }

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

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id, "Natural reserve ID must not be null");
        NaturalReserve reserve = naturalReserveRepository.findById(id).orElseThrow(() ->
                new NaturalReserveNotFoundException("There is no natural reserve with ID = " + id)
        );
        naturalReserveRepository.delete(reserve);
    }

    @Override
    public NaturalReserveDTO createOrUpdateDTO(NaturalReserveDTO reserveDTO) {
        Assert.notNull(reserveDTO, "NaturalReserveDTO must not be null");
        return toDTO(save(fromDTO(reserveDTO)));
    }

    private NaturalReserve save(NaturalReserve reserve) {
        reserve.normalizeAndValidate();
        return naturalReserveRepository.save(reserve);
    }

    private NaturalReserveDTO toDTO(NaturalReserve reserve) {
        NaturalReserveDTO reserveDTO = new NaturalReserveDTO();
        reserveDTO.setId(reserve.getId());
        reserveDTO.setName(reserve.getName());
        reserveDTO.setRegionId(reserve.getRegion().getId());
        return reserveDTO;
    }

    private NaturalReserve fromDTO(NaturalReserveDTO reserveDTO) {
        NaturalReserve reserve = new NaturalReserve();
        reserve.setId(reserveDTO.getId());
        reserve.setName(reserveDTO.getName());
        Long regionId = reserveDTO.getRegionId();
        BusinessException.notNull(regionId, "Region ID must not be null");
        Region region = regionRepository.findById(regionId).orElseThrow(
                () -> new BusinessException("There is no region with ID = " + regionId)
        );
        reserve.setRegion(region);
        return reserve;
    }

}
