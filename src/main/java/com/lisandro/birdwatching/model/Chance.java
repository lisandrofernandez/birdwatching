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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lisandro.birdwatching.core.BaseEntity;

/**
 * An entity class which represents a chance.<p>
 *
 * A chance indicates the probability to watch a bird in a natural reserve.<p>
 *
 * This class extends from {@link BaseEntity}.
 *
 * @author Lisandro Fernandez
 */
@Entity
@Table(name = "chance")
public class Chance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bird_id")
    private Bird bird;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "natural_reserve_id")
    private NaturalReserve reserve;

    @Enumerated
    private Month month;

    @Column(name = "probability")
    private Double probability;

    public Bird getBird() {
        return bird;
    }

    public void setBird(Bird bird) {
        this.bird = bird;
    }

    public NaturalReserve getReserve() {
        return reserve;
    }

    public void setReserve(NaturalReserve reserve) {
        this.reserve = reserve;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }


    // fluent API

    public Chance id(Long id) {
        setId(id);
        return this;
    }

    public Chance bird(Bird bird) {
        setBird(bird);
        return this;
    }

    public Chance reserve(NaturalReserve reserve) {
        setReserve(reserve);
        return this;
    }

    public Chance mont(Month month) {
        setMonth(month);
        return this;
    }

    public Chance probability(Double probability) {
        setProbability(probability);
        return this;
    }
}
