package com.lisandro.birdwatching.service;

import java.time.Month;
import java.util.List;

import com.lisandro.birdwatching.model.Chance;

public interface ChanceService {
    static final double MIN_PROBABILITY = .15;

    List<Chance> findByMonth(Month month);
}
