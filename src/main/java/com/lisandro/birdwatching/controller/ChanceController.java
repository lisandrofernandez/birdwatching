package com.lisandro.birdwatching.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

import com.lisandro.birdwatching.model.Chance;
import com.lisandro.birdwatching.service.ChanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ChanceController.BASE_URL)
public class ChanceController {

    public static final String BASE_URL = "/api/v1/chances";

    @Autowired
    private ChanceService chanceService;

    @GetMapping
    public List<Long> findByMonth(
            @RequestParam(name = "date", required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        List<Chance> chances = chanceService.findByMonth(date.getMonth());
        return chances.stream().map(Chance::getId).collect(Collectors.toList());
    }

}
