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

package com.lisandro.birdwatching.controller;

import static com.lisandro.birdwatching.controller.NaturalReserveController.BASE_URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.lisandro.birdwatching.core.BusinessException;
import com.lisandro.birdwatching.dto.NaturalReserveDTO;
import com.lisandro.birdwatching.dto.NaturalReserve_TupleDTO;
import com.lisandro.birdwatching.service.NaturalReserveNotFoundException;
import com.lisandro.birdwatching.service.NaturalReserveService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests of {@link NaturalReserveController}.
 *
 * @author Lisandro Fernandez
 */
@WebMvcTest(controllers = NaturalReserveController.class)
public class NaturalReserveControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NaturalReserveService reserveService;

    @Test
    void getAllTuplesWhenElements() throws Exception {
        // given
        List<NaturalReserve_TupleDTO> tuples = Arrays.asList(
                new NaturalReserve_TupleDTO(1L, "Natural Reserve #1"),
                new NaturalReserve_TupleDTO(2L, "Natural Reserve #2"),
                new NaturalReserve_TupleDTO(3L, "Natural Reserve #3")
        );
        given(reserveService.findAllTuples()).willReturn(tuples);

        // when
        mockMvc.perform(get("{baseUrl}", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(
                      "[{'id': 1, 'name': 'Natural Reserve #1'},"
                    + " {'id': 2, 'name': 'Natural Reserve #2'},"
                    + " {'id': 3, 'name': 'Natural Reserve #3'}]" , true)
            );
    }

    @Test
    void getAllTuplesWhenNoElements() throws Exception {
        // given
        given(reserveService.findAllTuples()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("{baseUrl}", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]", true));
    }

    @Test
    void getExistingNaturalReserveTest() throws Exception {
        // given
        Long id = 1L;
        NaturalReserveDTO naturalReserveDTO = new NaturalReserveDTO();
        naturalReserveDTO.setId(id);
        naturalReserveDTO.setName("A Natural Reserve");
        naturalReserveDTO.setRegionId(2L);
        given(reserveService.findByIdDTO(id)).willReturn(naturalReserveDTO);

        // when
        mockMvc.perform(get("{baseUrl}/{id}", BASE_URL, id).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content()
                    .json("{'id': 1, 'name': 'A Natural Reserve', 'regionId': 2}", true)
            );
    }

    @Test
    void getNonExistingNaturalReserveTest() throws Exception {
        // when
        mockMvc.perform(get("{baseUrl}/{id}", BASE_URL, 1L).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    void createNaturalReserveOkTest() throws Exception {
        // given
        Long id = 1L;
        String name = "A Natural Reserve";
        Long regionId = 2L;
        NaturalReserveDTO newDTO = new NaturalReserveDTO();
        newDTO.setName(name);
        newDTO.setRegionId(regionId);
        NaturalReserveDTO createdDTO = new NaturalReserveDTO();
        createdDTO.setId(id);
        createdDTO.setName(name);
        createdDTO.setRegionId(regionId);
        given(reserveService.createDTO(argThat(new NaturalReserveDTOMatcher(newDTO))))
            .willReturn(createdDTO);

        // when
        String requestBody = "{\"name\": \"A Natural Reserve\", \"regionId\": 2}";
        mockMvc.perform(post("{baseUrl}", BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestBody).accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content()
                    .json("{'id': 1, 'name': 'A Natural Reserve', 'regionId': 2}", true)
            );
    }

    @Test
    void createNaturalReserveWhenServiceThrowsABusinessExceptionTest() throws Exception {
        // given
        given(reserveService.createDTO(any())).willThrow(new BusinessException("Exception test"));

        // when
        mockMvc.perform(post("{baseUrl}", BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content("{}").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest()) // then
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    void createNaturalReserveWhenServiceThrowsAnotherExceptionTest() throws Exception {
        // given
        given(reserveService.createDTO(any())).willThrow(new RuntimeException("Exception test"));

        // when
        mockMvc.perform(post("{baseUrl}", BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content("{}").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isInternalServerError()) // then
            .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));
    }

    @Test
    void updateNaturalReserveOkTest() throws Exception {
        // given
        Long id = 1L;
        String name = "A Natural Reserve Updated";
        Long regionLong = 2L;
        NaturalReserveDTO updateDTO = new NaturalReserveDTO();
        updateDTO.setId(id);
        updateDTO.setName(name);
        updateDTO.setRegionId(regionLong);
        given(reserveService.updateDTO(argThat(new NaturalReserveDTOMatcher(updateDTO))))
            .willReturn(updateDTO);

        // when
        String requestBody = "{\"name\": \"A Natural Reserve Updated\", \"regionId\": 2}";
        mockMvc.perform(put("{baseUrl}/{id}", BASE_URL, id).contentType(MediaType.APPLICATION_JSON)
                .content(requestBody).accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content()
                    .json("{'id': 1, 'name': 'A Natural Reserve Updated', 'regionId': 2}", true)
            );
    }

    @Test
    void updateNonExistingNaturalReserveTest() throws Exception {
        // given
        given(reserveService.updateDTO(any()))
            .willThrow(new NaturalReserveNotFoundException("Exception test"));

        // when
        mockMvc.perform(put("{baseUrl}/1", BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content("{}").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isNotFound()) // then
            .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    void updateNaturalReserveWhenServiceThrowsABusinessExceptionTest() throws Exception {
        // given
        given(reserveService.updateDTO(any())).willThrow(new BusinessException("Exception test"));

        // when
        mockMvc.perform(put("{baseUrl}/1", BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content("{}").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest()) // then
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    void updateNaturalReserveWhenServiceThrowsAnotherExceptionTest() throws Exception {
        // given
        given(reserveService.updateDTO(any())).willThrow(new RuntimeException("Exception test"));

        // when
        mockMvc.perform(put("{baseUrl}/1", BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content("{}").accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isInternalServerError()) // then
            .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"));
    }

    @Test
    void deleteExistingNaturalReserve() throws Exception {
        // when
        mockMvc.perform(delete("{baseUrl}/{id}", BASE_URL, 1L).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent()); // then
    }

    @Test
    void deleteNonExistingNaturalReserve() throws Exception {
        // given
        Long id = 1L;
        willThrow(new NaturalReserveNotFoundException("Exception test")).given(reserveService)
            .deleteById(id);

        // when
        mockMvc.perform(delete("{baseUrl}/{id}", BASE_URL, 1L).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }


    private class NaturalReserveDTOMatcher implements ArgumentMatcher<NaturalReserveDTO> {
        private final NaturalReserveDTO expected;

        public NaturalReserveDTOMatcher(NaturalReserveDTO expected) {
            this.expected = expected;
        }

        @Override
        public boolean matches(NaturalReserveDTO argument) {
            return Objects.equals(expected.getId(), argument.getId())
                    && Objects.equals(expected.getName(), argument.getName())
                    && Objects.equals(expected.getRegionId(), argument.getRegionId());
        }
    }

}
