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

import com.lisandro.birdwatching.dto.RegionDTO;
import com.lisandro.birdwatching.model.Region;
import com.lisandro.birdwatching.model.RegionTestDataProvider;
import com.lisandro.birdwatching.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Unit tests of {@link RegionServiceImpl}.
 *
 * @author Lisandro Fernandez
 */
@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionServiceImpl regionServiceImpl;

    @Test
    void findAllTest() {
        // given
        List<Region> regions = RegionTestDataProvider.createAListOfRegions();
        given(regionRepository.findAll()).willReturn(regions);

        // when
        List<RegionDTO> dtos = regionServiceImpl.findAll();

        // then
        assertThat(dtos).hasSize(regions.size());
        for (int i = 0; i < regions.size(); i++) {
            Region region = regions.get(i);
            RegionDTO dto = dtos.get(i);
            assertThat(dto.getId()).isEqualTo(region.getId());
            assertThat(dto.getName()).isEqualTo(region.getName());
        }
    }
}
