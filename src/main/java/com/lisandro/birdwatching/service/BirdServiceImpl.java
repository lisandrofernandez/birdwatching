/*
 * Copyright (c) 2020 Lisandro Fernandez
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.lisandro.birdwatching.service;

import java.util.ArrayList;
import java.util.List;

import com.lisandro.birdwatching.dto.Bird_TupleDTO;
import com.lisandro.birdwatching.model.Bird;
import com.lisandro.birdwatching.repository.BirdRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link BirdService} implementation.
 *
 * @author Lisandro Fernandez
 */
@Service
@Transactional
public class BirdServiceImpl implements BirdService {

    private final BirdRepository birdRepository;

    /**
     * Constructs a {@link BirdServiceImpl}.
     *
     * @param birdRepository  used by the service
     */
    public BirdServiceImpl (BirdRepository birdRepository) {
        this.birdRepository = birdRepository;
    }

    /*
     * (non-Javadoc)
     * @see BirdService#findAllTuples()
     */
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
