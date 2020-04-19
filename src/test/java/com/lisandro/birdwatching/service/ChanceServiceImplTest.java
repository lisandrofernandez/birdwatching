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

import com.lisandro.birdwatching.dto.ChanceDTO;
import com.lisandro.birdwatching.model.Chance;
import com.lisandro.birdwatching.model.ChanceTestDataProvider;
import com.lisandro.birdwatching.repository.ChanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;

/**
 * Unit tests of {@link ChanceServiceImpl}.
 *
 * @author Lisandro Fernandez
 */
@ExtendWith(MockitoExtension.class)
class ChanceServiceImplTest {

    @Mock
    private ChanceRepository chanceRepository;

    @InjectMocks
    private ChanceServiceImpl chanceServiceImpl;

    @Test
    void findByMonthOkTest() {
        // given
        Month month = Month.JANUARY;
        List<Chance> chances = ChanceTestDataProvider.createOrderedListOfChances(month);
        given(chanceRepository.findByMonthAndProbabilityGreaterThanEqual(any(), anyDouble())).willReturn(chances);

        // when
        List<ChanceDTO> dtos = chanceServiceImpl.findByMonth(month);

        // then
        ChanceDTO previousDTO = null;
        for (ChanceDTO chanceDTO : dtos) {
            then(chanceDTO.getMonth()).isEqualTo(month);
            if (previousDTO != null) {
                String previousReserveName = previousDTO.getReserve().getName();
                String reserveName = chanceDTO.getReserve().getName();
                then(previousReserveName).isLessThanOrEqualTo(reserveName);
            }
            previousDTO = chanceDTO;
        }
    }

    @Test
    void findByMonthWhenMonthIsNullShouldThrowIllegalArgumentException() {
        // when-then
        assertThatIllegalArgumentException().isThrownBy(() -> chanceServiceImpl.findByMonth(null));
    }
}
