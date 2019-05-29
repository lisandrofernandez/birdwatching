package com.lisandro.birdwatching.controller;

import java.util.List;

import com.lisandro.birdwatching.dto.RegionDTO;
import com.lisandro.birdwatching.service.RegionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RegionController.BASE_URL)
public class RegionController {

    public static final String BASE_URL = "/api/v1/regions";

    @Autowired
    private RegionService regionService;

    @GetMapping
    public List<RegionDTO> all() {
        return regionService.findAllDTO();
    }

}
