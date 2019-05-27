package com.lisandro.birdwatching.dto;

public class NaturalReserve_TupleDTO {
    private Long id;
    private String name;

    public NaturalReserve_TupleDTO() { }

    public NaturalReserve_TupleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
