package com.lisandro.birdwatching.controller;

import java.util.List;

import com.lisandro.birdwatching.dto.Bird_TupleDTO;
import com.lisandro.birdwatching.service.BirdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BirdController.BASE_URL)
public class BirdController {

    public static final String BASE_URL = "/api/v1/birds";

    @Autowired
    private BirdService birdService;

    @GetMapping
    public List<Bird_TupleDTO> allTuples() {
        return birdService.findAllTuples();
    }

}
