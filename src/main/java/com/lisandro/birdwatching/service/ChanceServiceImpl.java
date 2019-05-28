package com.lisandro.birdwatching.service;

import java.time.Month;
import java.util.List;

import com.lisandro.birdwatching.model.Chance;
import com.lisandro.birdwatching.repository.ChanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChanceServiceImpl implements ChanceService {

    @Autowired
    private ChanceRepository chanceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Chance> findByMonth(Month month) {
        return chanceRepository.findByMonthAndProbabilityGreaterThanEqual(
            month, ChanceService.MIN_PROBABILITY);
    }

}
