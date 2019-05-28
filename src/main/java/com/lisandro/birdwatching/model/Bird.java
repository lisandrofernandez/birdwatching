package com.lisandro.birdwatching.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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

    @ElementCollection(targetClass = Color.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "bird_color", joinColumns = @JoinColumn(name = "bird_id"))
    @Column(name = "color")
    @Enumerated
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

}
