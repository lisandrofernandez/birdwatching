package com.lisandro.birdwatching.controller;

import static com.lisandro.birdwatching.controller.RegionController.BASE_URL;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import com.lisandro.birdwatching.dto.RegionDTO;
import com.lisandro.birdwatching.service.RegionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RegionController.class)
public class RegionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegionService regionService;

    @Test
    void getAllWhenElements() throws Exception {
        // given
        given(regionService.findAllDTO()).willReturn(
                Arrays.asList(new RegionDTO(1L, "Region #1"), new RegionDTO(2L, "Region #2"))
        );

        // when
        mockMvc.perform(get("{baseUrl}", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(
                    "[{'id': 1, 'name': 'Region #1'}, {'id': 2, 'name': 'Region #2'}]")
            );
    }

    @Test
    void getAllWhenNoElements() throws Exception {
        // given
        given(regionService.findAllDTO()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("{baseUrl}", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"));
    }

}
