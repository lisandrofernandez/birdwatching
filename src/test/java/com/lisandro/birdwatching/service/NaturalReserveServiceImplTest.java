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

package com.lisandro.birdwatching.service;

import com.lisandro.birdwatching.core.BusinessException;
import com.lisandro.birdwatching.dto.NaturalReserveDTO;
import com.lisandro.birdwatching.dto.NaturalReserveDTOTestDataProvider;
import com.lisandro.birdwatching.dto.NaturalReserveTupleDTO;
import com.lisandro.birdwatching.model.NaturalReserve;
import com.lisandro.birdwatching.model.NaturalReserveTestDataProvider;
import com.lisandro.birdwatching.repository.NaturalReserveRepository;
import com.lisandro.birdwatching.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Unit tests of {@link NaturalReserveServiceImpl}.
 *
 * @author Lisandro Fernandez
 */
@ExtendWith(MockitoExtension.class)

class NaturalReserveServiceImplTest {

    @Mock
    private NaturalReserveRepository naturalReserveRepository;
    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private NaturalReserveServiceImpl naturalReserveServiceImpl;

    @Test
    void findByIdWhenNaturalReserveExistsTest() {
        // given
        NaturalReserve reserve = NaturalReserveTestDataProvider.createANaturalReserve();
        Long id = reserve.getId();
        given(naturalReserveRepository.findById(id)).willReturn(Optional.of(reserve));

        // when
        NaturalReserveDTO dto = naturalReserveServiceImpl.findById(id);

        // then
        then(dto.getId()).isEqualTo(id);
        then(dto.getName()).isEqualTo(reserve.getName());
        then(dto.getRegionId()).isEqualTo(reserve.getRegion().getId());
    }

    @Test
    void findByIdWhenNaturalReserveDoNotExistShouldBeNullTest() {
        // when
        NaturalReserveDTO dto = naturalReserveServiceImpl.findById(1L);

        // then
        then(dto).isNull();
    }

    @Test
    void findByIdWhenIdIsNullShouldThrowIllegalArgumentExceptionTest() {
        // when-then
        assertThatIllegalArgumentException().isThrownBy(() -> naturalReserveServiceImpl.findById(null));
    }

    @Test
    void findAllTuplesTest() {
        // given
        List<NaturalReserve> reserves = NaturalReserveTestDataProvider.createAListOfNaturalReserves();
        given(naturalReserveRepository.findAll()).willReturn(reserves);

        // when
        List<NaturalReserveTupleDTO> tuples = naturalReserveServiceImpl.findAllTuples();

        // then
        then(tuples).hasSize(reserves.size());
        for (int i = 0; i < tuples.size(); i++) {
            NaturalReserveTupleDTO tuple = tuples.get(i);
            NaturalReserve reserve = reserves.get(i);
            then(tuple.getId()).isEqualTo(reserve.getId());
            then(tuple.getName()).isEqualTo(reserve.getName());
        }
    }

    @Test
    void createOkTest() {
        // given
        NaturalReserveDTO creationDTO = NaturalReserveDTOTestDataProvider.createANaturalReserveDTO();
        NaturalReserve reserve = NaturalReserveTestDataProvider.createANaturalReserve().name(creationDTO.getName());
        reserve.getRegion().id(creationDTO.getRegionId());
        given(regionRepository.findById(creationDTO.getRegionId())).willReturn(Optional.of(reserve.getRegion()));
        given(naturalReserveRepository.save(any())).willReturn(reserve);

        // when
        NaturalReserveDTO newDTO = naturalReserveServiceImpl.create(creationDTO);

        // then
        then(newDTO.getId()).isNotNull();
        then(newDTO.getName()).isEqualTo(creationDTO.getName());
        then(newDTO.getRegionId()).isEqualTo(creationDTO.getRegionId());
    }

    @Test
    void createWhenInputIsNullShouldThrowIllegalArgumentExceptionTest() {
        // when-then
        assertThatIllegalArgumentException().isThrownBy(() -> naturalReserveServiceImpl.create(null));
    }

    @Test
    void createWhenNameIsNullShouldThrowBusinessExceptionTest() {
        // given
        NaturalReserveDTO creationDTO = NaturalReserveDTOTestDataProvider.createANaturalReserveDTO().name(null);

        // when-then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> naturalReserveServiceImpl.create(creationDTO));
    }

    @Test
    void createWhenRegionIdIsNullShouldThrowBusinessExceptionTest() {
        // given
        NaturalReserveDTO creationDTO = NaturalReserveDTOTestDataProvider.createANaturalReserveDTO().regionId(null);

        // when-then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> naturalReserveServiceImpl.create(creationDTO));
    }

    @Test
    void createWhenRegionDoNotExistShouldThrowBusinessExceptionTest() {
        // given
        NaturalReserveDTO creationDTO = NaturalReserveDTOTestDataProvider.createANaturalReserveDTO();

        // when-then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> naturalReserveServiceImpl.create(creationDTO));
    }

    @Test
    void updateOk() {
        // given
        NaturalReserveDTO updateDTO = NaturalReserveDTOTestDataProvider.createANaturalReserveDTO();
        NaturalReserve reserve = NaturalReserveTestDataProvider.createANaturalReserve().id(updateDTO.getId());
        given(naturalReserveRepository.findById(updateDTO.getId())).willReturn(Optional.of(reserve));
        given(regionRepository.findById(updateDTO.getRegionId())).willReturn(Optional.of(reserve.getRegion()));

        // when
        NaturalReserveDTO updatedDTO = naturalReserveServiceImpl.update(updateDTO);

        // then
        then(updatedDTO.getId()).isEqualTo(updateDTO.getId());
        then(updatedDTO.getName()).isEqualTo(updateDTO.getName());
        then(updatedDTO.getRegionId()).isEqualTo(updateDTO.getRegionId());
    }

    @Test
    void updateWhenNaturalReserveDoNotExistShouldThrowNaturalReserveNotFoundExceptionTest() {
        // given
        NaturalReserveDTO updateDTO = NaturalReserveDTOTestDataProvider.createANaturalReserveDTO();

        // when-then
        assertThatExceptionOfType(NaturalReserveNotFoundException.class)
            .isThrownBy(() -> naturalReserveServiceImpl.update(updateDTO));
    }

    @Test
    void updateWhenInputIsNullShouldThrowIllegalArgumentExceptionTest() {
        // when-then
        assertThatIllegalArgumentException().isThrownBy(() -> naturalReserveServiceImpl.update(null));
    }

    @Test
    void updateWhenNameIsNullShouldThrowBusinessExceptionTest() {
        // given
        NaturalReserveDTO updateDTO = NaturalReserveDTOTestDataProvider.createANaturalReserveDTO().name(null);
        NaturalReserve reserve = NaturalReserveTestDataProvider.createANaturalReserve().id(updateDTO.getId());
        given(naturalReserveRepository.findById(updateDTO.getId())).willReturn(Optional.of(reserve));
        given(regionRepository.findById(updateDTO.getRegionId())).willReturn(Optional.of(reserve.getRegion()));

        // when-then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> naturalReserveServiceImpl.update(updateDTO));
    }

    @Test
    void updateWhenRegionIdIsNullShouldThrowBusinessExceptionTest() {
        // given
        NaturalReserveDTO updateDTO = NaturalReserveDTOTestDataProvider.createANaturalReserveDTO().regionId(null);
        NaturalReserve reserve = NaturalReserveTestDataProvider.createANaturalReserve().id(updateDTO.getId());
        given(naturalReserveRepository.findById(updateDTO.getId())).willReturn(Optional.of(reserve));

        // when-then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> naturalReserveServiceImpl.update(updateDTO));
    }

    @Test
    void updateWhenRegionDoNotExistShouldThrowBusinessExceptionTest() {
        // given
        NaturalReserveDTO updateDTO = NaturalReserveDTOTestDataProvider.createANaturalReserveDTO();
        NaturalReserve reserve = NaturalReserveTestDataProvider.createANaturalReserve().id(updateDTO.getId());
        given(naturalReserveRepository.findById(updateDTO.getId())).willReturn(Optional.of(reserve));

        // when-then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> naturalReserveServiceImpl.update(updateDTO));
    }

    @Test
    void deleteByIdOkTest() {
        // given
        NaturalReserve reserve = NaturalReserveTestDataProvider.createANaturalReserve();
        Long id = reserve.getId();
        given(naturalReserveRepository.findById(id)).willReturn(Optional.of(reserve));

        // when
        naturalReserveServiceImpl.deleteById(id);

        // then
        BDDMockito.then(naturalReserveRepository).should().delete(reserve);
    }

    @Test
    void deleteByIdWhenIdIsNullShouldThrowIllegalArgumentExceptionTest() {
        // when-then
        assertThatIllegalArgumentException().isThrownBy(() -> naturalReserveServiceImpl.deleteById(null));
    }

    @Test
    void deleteWhenNaturalReserveDoNotExistShouldThrowNaturalReserveNotFoundExceptionTest() {
        // when-then
        assertThatExceptionOfType(NaturalReserveNotFoundException.class)
            .isThrownBy(() -> naturalReserveServiceImpl.deleteById(1L));
    }
}
