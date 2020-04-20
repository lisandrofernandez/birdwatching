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

package com.lisandro.birdwatching;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.lisandro.birdwatching.controller.NaturalReserveController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Functional tests of natural reserve use cases.
 *
 * @author Lisandro Fernandez
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NaturalReserveFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getNaturalReserveTuplesTest() throws Exception {
        // when
        mockMvc.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(
                      "[{'id': 1, 'name': 'Monfragüe'},"
                    + " {'id': 2, 'name': 'Doñana National Park'},"
                    + " {'id': 3, 'name': 'Llanos de Caceres'}]" , true)
            );
    }

    @Test
    void getExistingNaturalReserveTest() throws Exception {
        // when
        mockMvc.perform(get("{baseUrl}/1", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{'id': 1, 'name': 'Monfragüe', 'regionId': 1}", true)
        );
    }

    @Test
    void getNonExistingNaturalReserveShouldReturnNotFoundTest() throws Exception {
        // when
        mockMvc.perform(get("{baseUrl}/99", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    void createNaturalReserveOkTest() throws Exception {
        // when
        String requestBody = "{\"name\": \"Odiel Marshes\", \"regionId\": 1}";
        mockMvc.perform(post("{baseUrl}", BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{'name': 'Odiel Marshes', 'regionId': 1}"));
    }

    @Test
    void createNaturalWhenRegionDoesNotExistShouldReturnBadRequestTest() throws Exception {
        // when
        String requestBody = "{\"name\": \"Odiel Marses\", \"regionId\": 99}";
        mockMvc.perform(post("{baseUrl}", BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    void updateNaturalReserveExistingTest() throws Exception {
        // when
        String requestBody = "{\"name\": \"Monfragüeee\", \"regionId\": 2}";
        mockMvc.perform(put("{baseUrl}/1", BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestBody).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{'id': 1, 'name': 'Monfragüeee', 'regionId': 2}", true));
    }

    @Test
    void updateNaturalReserveWhenDoNotExistsShouldReturnNotFoundTest() throws Exception {
        // when
        String requestBody = "{\"name\": \"Monfragüeee\", \"regionId\": 2}";
        mockMvc.perform(put("{baseUrl}/99", BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestBody).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    void deleteExistingNaturalReserveTest() throws Exception {
        // when
        mockMvc.perform(delete("{baseUrl}/1", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent()); // then
    }

    @Test
    void deleteNonExistingNaturalReserveShouldReturnNotFound() throws Exception {
        // when
        mockMvc.perform(delete("{baseUrl}/99", BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }
}
