package com.lisandro.birdwatching.service;

import java.util.ArrayList;
import java.util.List;

import com.lisandro.birdwatching.dto.RegionDTO;
import com.lisandro.birdwatching.model.Region;
import com.lisandro.birdwatching.repository.RegionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RegionDTO> findAllDTO() {
        List<Region> regions = regionRepository.findAll();
        List<RegionDTO> regionDTOs = new ArrayList<>(regions.size());
        for (Region region : regions) {
            regionDTOs.add(new RegionDTO(region.getId(), region.getName()));
        }
        return regionDTOs;
    }

}
