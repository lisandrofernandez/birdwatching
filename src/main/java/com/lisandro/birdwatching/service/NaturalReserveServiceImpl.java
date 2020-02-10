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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NaturalReserveServiceImpl implements NaturalReserveService {

    private final NaturalReserveRepository naturalReserveRepository;
    private final RegionRepository regionRepository;

    public NaturalReserveServiceImpl(NaturalReserveRepository naturalReserveRepository,
                                     RegionRepository regionRepository) {
        this.naturalReserveRepository = naturalReserveRepository;
        this.regionRepository = regionRepository;
    }

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
    @Transactional
    public NaturalReserveDTO createDTO(NaturalReserveDTO reserveDTO) {
        Assert.notNull(reserveDTO, "NaturalReserveDTO must not be null");
        reserveDTO.setId(null); // omit ID to avoid an unwanted update
        NaturalReserve reserve = fromDTO(reserveDTO);
        reserve.normalizeAndValidate();
        return toDTO(naturalReserveRepository.save(reserve));
    }

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
