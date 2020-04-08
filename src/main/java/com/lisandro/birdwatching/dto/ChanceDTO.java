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

package com.lisandro.birdwatching.dto;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.lisandro.birdwatching.model.BirdSize;
import com.lisandro.birdwatching.model.Color;

/**
 * A DTO object used as input and output in service layer classes and
 * obviously, by its clients.
 *
 * @author Lisandro Fernandez
 */
public class ChanceDTO {
    private Month month;
    private BirdDTO bird;
    private NaturalReserveDTO reserve;

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public BirdDTO getBird() {
        return bird;
    }

    public void setBird(BirdDTO bird) {
        this.bird = bird;
    }

    public NaturalReserveDTO getReserve() {
        return reserve;
    }

    public void setReserve(NaturalReserveDTO reserve) {
        this.reserve = reserve;
    }

    public class BirdDTO {
        private String name;
        private BirdSize size;
        private String photoURL;
        private List<Color> colors = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BirdSize getSize() {
            return size;
        }

        public void setSize(BirdSize size) {
            this.size = size;
        }

        public String getPhotoURL() {
            return photoURL;
        }

        public void setPhotoURL(String photoURL) {
            this.photoURL = photoURL;
        }

        public List<Color> getColors() {
            return colors;
        }

        public void setColors(List<Color> colors) {
            this.colors = colors;
        }
    }

    public class NaturalReserveDTO {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
