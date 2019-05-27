package com.lisandro.birdwatching.service;

import java.util.ArrayList;
import java.util.List;

import com.lisandro.birdwatching.dto.BirdDTO;
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
    public List<BirdDTO> findAllTuples() {
        List<Bird> birds = birdRepository.findAll();
        List<BirdDTO> dtos = new ArrayList<>(birds.size());
        for(Bird bird : birds) {
            dtos.add(new BirdDTO(bird.getId(), bird.getName()));
        }
        return dtos;
    }

}
