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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Object mother of {@link NaturalReserve}.
 *
 * @author Lisandro Fernandez
 */
public class NaturalReserveTestDataProvider {
    private NaturalReserveTestDataProvider() {
        // private constructor
    }

    public static NaturalReserve createANaturalReserve() {
        return new NaturalReserve()
                   .id(1L)
                   .name("A Natural Reserve")
                   .region(RegionTestDataProvider.createARegion());
    }

    public static List<NaturalReserve> createAListOfNaturalReserves() {
        return new ArrayList<>(Arrays.asList(
                new NaturalReserve()
                    .id(1L)
                    .name("Natural Reserve One")
                    .region(new Region().id(1L).name("Region One")),
                new NaturalReserve()
                    .id(2L)
                    .name("Natural Reserve Two")
                    .region(new Region().id(2L).name("Region Two")),
                new NaturalReserve()
                    .id(3L)
                    .name("Natural Reserve Three")
                    .region(new Region().id(3L).name("Region Three"))
        ));
    }
}
