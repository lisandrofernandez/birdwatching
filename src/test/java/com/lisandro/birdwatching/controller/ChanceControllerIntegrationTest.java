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

import static com.lisandro.birdwatching.controller.ChanceController.BASE_URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lisandro.birdwatching.dto.ChanceDTO;
import com.lisandro.birdwatching.model.BirdSize;
import com.lisandro.birdwatching.model.Color;
import com.lisandro.birdwatching.service.ChanceService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests of {@link ChanceController}.
 *
 * @author Lisandro Fernandez
 */
@WebMvcTest(controllers = ChanceController.class)
public class ChanceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChanceService chanceService;

    @Test
    void getChancesByMonthWhenElements() throws Exception {
        // given
        given(chanceService.findByMonth(Month.JANUARY)).willReturn(getChances());

        // when
        mockMvc.perform(get("{baseUrl}/?date=2000-01-01", BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(
                     "["
                    + "    {"
                    + "        'bird': {"
                    + "            'colors': ['BLACK', 'WHITE'],"
                    + "            'name': 'Bird #1',"
                    + "            'photoURL': 'http://example.org/bird/images/1.jpg',"
                    + "            'size': 'SMALL'"
                    + "        },"
                    + "        'month': 'JANUARY',"
                    + "        'reserve': {'name': 'Natural Reserve #1'}"
                    + "    },"
                    + "    {"
                    + "        'bird': {"
                    + "            'colors': ['BLUE', 'YELLOW'],"
                    + "            'name': 'Bird #2',"
                    + "            'photoURL': 'http://example.org/bird/images/2.jpg',"
                    + "            'size': 'BIG'"
                    + "        },"
                    + "        'month': 'JANUARY',"
                    + "        'reserve': {'name': 'Natural Reserve #2'}"
                    + "    }"
                    + "]", true));
    }

    @Test
    void getChancesByMonthWhenNoElements() throws Exception {
        // given
        given(chanceService.findByMonth(any())).willReturn(Collections.emptyList());

        // when
        mockMvc.perform(get("{baseUrl}/?date=2000-01-01", BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]", true));
    }

    private List<ChanceDTO> getChances() {
        List<ChanceDTO> chances = new ArrayList<>(2);

        // bird 1
        ChanceDTO chanceDTO = new ChanceDTO();
        chanceDTO.setMonth(Month.JANUARY);
        ChanceDTO.BirdDTO birdDTO = new ChanceDTO.BirdDTO();
        birdDTO.setName("Bird #1");
        birdDTO.setSize(BirdSize.SMALL);
        birdDTO.setPhotoURL("http://example.org/bird/images/1.jpg");
        birdDTO.getColors().add(Color.BLACK);
        birdDTO.getColors().add(Color.WHITE);
        chanceDTO.setBird(birdDTO);
        ChanceDTO.NaturalReserveDTO reserveDTO = new ChanceDTO.NaturalReserveDTO();
        reserveDTO.setName("Natural Reserve #1");
        chanceDTO.setReserve(reserveDTO);
        chances.add(chanceDTO);
        // bird 2
        chanceDTO = new ChanceDTO();
        chanceDTO.setMonth(Month.JANUARY);
        birdDTO = new ChanceDTO.BirdDTO();
        birdDTO.setName("Bird #2");
        birdDTO.setSize(BirdSize.BIG);
        birdDTO.setPhotoURL("http://example.org/bird/images/2.jpg");
        birdDTO.getColors().add(Color.BLUE);
        birdDTO.getColors().add(Color.YELLOW);
        chanceDTO.setBird(birdDTO);
        reserveDTO = new ChanceDTO.NaturalReserveDTO();
        reserveDTO.setName("Natural Reserve #2");
        chanceDTO.setReserve(reserveDTO);
        chances.add(chanceDTO);

        return chances;
    }

}
