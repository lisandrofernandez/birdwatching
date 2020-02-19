package com.lisandro.birdwatching.controller;

import static com.lisandro.birdwatching.controller.BirdController.BASE_URL;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import com.lisandro.birdwatching.dto.Bird_TupleDTO;
import com.lisandro.birdwatching.service.BirdService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BirdController.class)
public class BirdControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BirdService birdService;

    @Test
    void getAllWhenElements() throws Exception {
        // given
        given(birdService.findAllTuples()).willReturn(
                Arrays.asList(new Bird_TupleDTO(1L, "Bird #1"), new Bird_TupleDTO(2L, "Bird #2"))
        );

        // when
        mockMvc.perform(get("{baseUrl}", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(
                    "[{'id': 1, 'name': 'Bird #1'}, {'id': 2, 'name': 'Bird #2'}]")
            );
    }

    @Test
    void getAllWhenNoElements() throws Exception {
        // given
        given(birdService.findAllTuples()).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("{baseUrl}", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"));
    }

}
