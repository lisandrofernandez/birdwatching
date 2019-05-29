package com.lisandro.birdwatching.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lisandro.birdwatching.core.BaseEntity;
import com.lisandro.birdwatching.core.BusinessException;

@Entity
@Table(name="natural_reserve")
public class NaturalReserve extends BaseEntity {

    private static final int NAME_LENGTH = 150;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    // Just for cascade delete
    @OneToMany(mappedBy = "reserve", cascade = CascadeType.REMOVE, orphanRemoval = true,
               fetch = FetchType.LAZY)
    private List<Chance> chances = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }


    public void normalizeAndValidate() {
        BusinessException.notNull(name, "Natural reserve name must not be null");
        name = name.trim();
        BusinessException.hasLength(name, "Natural reserve name must not be empty");
        BusinessException.isTrue(name.length() <= NAME_LENGTH,
                "Natural reserve name length must not be longer than " + NAME_LENGTH
        );
        BusinessException.notNull(region, "Region reserve must not be null");
    }

}
