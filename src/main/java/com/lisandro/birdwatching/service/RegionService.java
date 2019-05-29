package com.lisandro.birdwatching.service;

import java.util.List;

import com.lisandro.birdwatching.dto.RegionDTO;
import com.lisandro.birdwatching.model.Region;

public interface RegionService {
    Region findById(Long id);
    List<RegionDTO> findAllDTO();
}
