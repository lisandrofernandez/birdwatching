package com.lisandro.birdwatching.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.lisandro.birdwatching.core.BaseEntity;

@Entity
@Table(name="bird")
public class Bird extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Enumerated
    private BirdSize size;

    @Column(name = "photo_url")
    private String photoURL;

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

}
