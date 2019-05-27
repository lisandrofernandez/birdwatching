package com.lisandro.birdwatching.controller;

import java.util.List;

import com.lisandro.birdwatching.dto.NaturalReserve_TupleDTO;
import com.lisandro.birdwatching.service.NaturalReserveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(NaturalReserveController.BASE_URL)
public class NaturalReserveController {

    public static final String BASE_URL = "/api/v1/reserves";

    @Autowired
    private NaturalReserveService naturalReserveService;

    @GetMapping("/tuples")
    public List<NaturalReserve_TupleDTO> allTuples() {
        return naturalReserveService.findAllTuples();
    }

}
