package com.lisandro.birdwatching.dto;

import com.lisandro.birdwatching.dto.NaturalReserveDTO;

/**
 * Object mother of {@link NaturalReserveDTO}.
 *
 * @author Lisandro Fernandez
 */
public class NaturalReserveDTOTestDataProvider {
    private NaturalReserveDTOTestDataProvider() {
        // private constructor
    }

    public static NaturalReserveDTO createANaturalReserveDTO() {
        return new NaturalReserveDTO()
                   .id(1L)
                   .name("A Natural Reserve")
                   .regionId(1L);
    }
}
