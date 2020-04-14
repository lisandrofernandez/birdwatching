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

import com.lisandro.birdwatching.dto.BirdTupleDTO;
import com.lisandro.birdwatching.model.Bird;
import com.lisandro.birdwatching.model.BirdTestDataProvider;
import com.lisandro.birdwatching.repository.BirdRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Unit tests of {@link BirdServiceImpl}.
 *
 * @author Lisandro Fernandez
 */
@ExtendWith(MockitoExtension.class)
public class BirdServiceImplTest {

    @Mock
    private BirdRepository birdRepository;

    @InjectMocks
    private BirdServiceImpl birdServiceImpl;

    @Test
    void findAllTuplesTest() {
        // given
        List<Bird> birds = BirdTestDataProvider.createAListOfBirds();
        given(birdRepository.findAll()).willReturn(birds);

        // when
        List<BirdTupleDTO> tuples = birdServiceImpl.findAllTuples();

        // then
        assertThat(tuples).hasSize(birds.size());
        for (int i = 0; i < birds.size(); i++) {
            Bird bird = birds.get(i);
            BirdTupleDTO dto = tuples.get(i);
            assertThat(dto.getId()).isEqualTo(bird.getId());
            assertThat(dto.getName()).isEqualTo(bird.getName());
        }
    }
}
