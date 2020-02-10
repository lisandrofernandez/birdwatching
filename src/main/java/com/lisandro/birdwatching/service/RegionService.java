package com.lisandro.birdwatching.service;

import java.util.List;

import com.lisandro.birdwatching.dto.RegionDTO;

public interface RegionService {
    List<RegionDTO> findAllDTO();
}
