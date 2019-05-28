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

@Entity
@Table(name="chance")
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

}
