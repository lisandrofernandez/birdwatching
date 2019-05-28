package com.lisandro.birdwatching.dto;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lisandro.birdwatching.model.BirdSize;
import com.lisandro.birdwatching.model.Color;

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

    @JsonProperty(value = "bird")
    public BirdDTO getBird() {
        return bird;
    }

    public void setBird(BirdDTO bird) {
        this.bird = bird;
    }

    @JsonProperty(value = "reserve")
    public NaturalReserveDTO getReserveDTO() {
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
