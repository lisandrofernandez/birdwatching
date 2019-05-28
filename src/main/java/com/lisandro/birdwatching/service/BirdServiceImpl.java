package com.lisandro.birdwatching.service;

import java.util.ArrayList;
import java.util.List;

import com.lisandro.birdwatching.dto.Bird_TupleDTO;
import com.lisandro.birdwatching.model.Bird;
import com.lisandro.birdwatching.repository.BirdRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BirdServiceImpl implements BirdService {

    @Autowired
    private BirdRepository birdRepository;

    @Override
    @Transactional(readOnly = true)
    public Bird findById(Long id) {
        return birdRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bird_TupleDTO> findAllTuples() {
        List<Bird> birds = birdRepository.findAll();
        List<Bird_TupleDTO> dtos = new ArrayList<>(birds.size());
        for (Bird bird : birds) {
            dtos.add(new Bird_TupleDTO(bird.getId(), bird.getName()));
        }
        return dtos;
    }

}
