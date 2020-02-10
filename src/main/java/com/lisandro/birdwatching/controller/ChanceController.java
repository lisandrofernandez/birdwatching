package com.lisandro.birdwatching.controller;

import java.time.LocalDate;
import java.util.List;

import com.lisandro.birdwatching.dto.ChanceDTO;
import com.lisandro.birdwatching.service.ChanceService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ChanceController.BASE_URL)
public class ChanceController {

    public static final String BASE_URL = "/api/v1/chances";

    private final ChanceService chanceService;

    public ChanceController (ChanceService chanceService) {
        this.chanceService = chanceService;
    }

    @GetMapping
    public List<ChanceDTO> findByMonth(
            @RequestParam(name = "date", required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return chanceService.findByMonthDTO(date.getMonth());
    }

}
