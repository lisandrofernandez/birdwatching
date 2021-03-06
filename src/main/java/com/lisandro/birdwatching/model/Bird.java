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
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.lisandro.birdwatching.core.BaseEntity;

/**
 * An entity class which represents a bird.<p>
 *
 * This class extends from {@link BaseEntity}.
 *
 * @author Lisandro Fernandez
 */
@Entity
@Table(name = "bird")
public class Bird extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Enumerated
    private BirdSize size;

    @Column(name = "photo_url")
    private String photoURL;

    // https://www.w3ma.com/persisting-set-of-enums-in-a-many-to-many-spring-data-relationship/
    // https://stackoverflow.com/questions/416208/jpa-map-collection-of-enums
    @ElementCollection
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


    // fluent API

    public Bird id(Long id) {
        setId(id);
        return this;
    }

    public Bird name(String name) {
        setName(name);
        return this;
    }

    public Bird size(BirdSize size) {
        setSize(size);
        return this;
    }

    public Bird photoURL(String photoURL) {
        setPhotoURL(photoURL);
        return this;
    }

    public Bird addColor(Color color) {
        colors.add(color);
        return this;
    }

    public Bird removeColor(Color color) {
        colors.remove(color);
        return this;
    }
}
