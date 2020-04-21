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

package com.lisandro.birdwatching.model;

import com.lisandro.birdwatching.core.BusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Unit tests of {@link NaturalReserve}.
 *
 * @author Lisandro Fernandez
 */
class NaturalReserveTest {

    @Test
    void normalizeAndValidateWhenNameIsNullShouldThrowBusinessExceptionTest() {
        // given
        NaturalReserve naturalReserve = NaturalReserveTestDataProvider.createANaturalReserve().name(null);

        // when-then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(naturalReserve::normalizeAndValidate);
    }

    @Test
    void normalizeAndValidateWhenNameIsEmptyShouldThrowBusinessExceptionTest() {
        // given
        NaturalReserve naturalReserve = NaturalReserveTestDataProvider.createANaturalReserve().name(" ");

        // when-then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(naturalReserve::normalizeAndValidate);
    }

    @Test
    void normalizeAndValidateWhenNameIsTooLongShouldThrowBusinessExceptionTest() {
        // given
        String longName = "Marco Gastón Ruben Eduardo Germán Coudet Ángel Tulio Zof Edgardo Bauza Aldo Pedro Poy"
                        + "José Luis Rodríguez Cristian Alberto González Germán Ezequiel Rivarola";
        NaturalReserve naturalReserve = NaturalReserveTestDataProvider.createANaturalReserve().name(longName);

        // when-then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(naturalReserve::normalizeAndValidate);
    }

    @Test
    void normalizeAndValidateWhenRegionIsNullShouldThrowBusinessExceptionTest() {
        // given
        NaturalReserve naturalReserve = NaturalReserveTestDataProvider.createANaturalReserve().region(null);

        // when-then
        assertThatExceptionOfType(BusinessException.class).isThrownBy(naturalReserve::normalizeAndValidate);
    }
}
