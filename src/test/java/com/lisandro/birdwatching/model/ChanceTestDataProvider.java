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

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Object mother of {@link Chance}.
 *
 * @author Lisandro Fernandez
 */
public class ChanceTestDataProvider {

    public static List<Chance> createOrderedListOfChances(Month month) {
        return new ArrayList<>(Arrays.asList(
                new Chance()
                    .id(1L)
                    .bird(new Bird()
                        .id(1L)
                        .name("Bird one")
                        .size(BirdSize.SMALL)
                        .photoURL("http://example.org/bird/images/1.jpg")
                        .addColor(Color.BLACK).addColor(Color.WHITE)
                    )
                    .reserve(new NaturalReserve()
                        .id(1L)
                        .name("Natural Reserve A")
                        .region(new Region().id(1L).name("Region One"))
                    )
                    .mont(month)
                    .probability(.35),
                new Chance()
                    .id(2L)
                    .bird(new Bird()
                        .id(2L)
                        .name("Bird two")
                        .size(BirdSize.MEDIUM)
                        .photoURL("http://example.org/bird/images/2.jpg")
                        .addColor(Color.BLUE).addColor(Color.YELLOW)
                    )
                    .reserve(new NaturalReserve()
                        .id(2L)
                        .name("Natural Reserve B")
                        .region(new Region().id(2L).name("Region Two"))
                    )
                    .mont(month)
                    .probability(.2),
                new Chance()
                    .id(3L)
                    .bird(new Bird()
                        .id(3L)
                        .name("Bird three")
                        .size(BirdSize.BIG)
                        .photoURL("http://example.org/bird/images/3.jpg")
                        .addColor(Color.WHITE)
                    )
                    .reserve(new NaturalReserve()
                        .id(3L)
                        .name("Natural Reserve C")
                        .region(new Region().id(3L).name("Region Three"))
                    )
                    .mont(month)
                    .probability(.9)
        ));
    }
}
