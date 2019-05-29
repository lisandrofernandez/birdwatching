package com.lisandro.birdwatching.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lisandro.birdwatching.core.BaseEntity;

@Entity
@Table(name = "region")
public class Region extends BaseEntity {

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
