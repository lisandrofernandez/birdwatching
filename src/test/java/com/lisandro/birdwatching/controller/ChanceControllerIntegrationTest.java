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

@WebMvcTest(controllers = ChanceController.class)
public class ChanceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChanceService chanceService;

    @Test
    void getChancesByMonthWhenElements() throws Exception {
        // given
        given(chanceService.findByMonthDTO(Month.JANUARY)).willReturn(getChances());

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
        given(chanceService.findByMonthDTO(any())).willReturn(Collections.emptyList());

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
        ChanceDTO.BirdDTO birdDTO = chanceDTO.new BirdDTO();
        birdDTO.setName("Bird #1");
        birdDTO.setSize(BirdSize.SMALL);
        birdDTO.setPhotoURL("http://example.org/bird/images/1.jpg");
        birdDTO.getColors().add(Color.BLACK);
        birdDTO.getColors().add(Color.WHITE);
        chanceDTO.setBird(birdDTO);
        ChanceDTO.NaturalReserveDTO reserveDTO = chanceDTO.new NaturalReserveDTO();
        reserveDTO.setName("Natural Reserve #1");
        chanceDTO.setReserve(reserveDTO);
        chances.add(chanceDTO);
        // bird 2
        chanceDTO = new ChanceDTO();
        chanceDTO.setMonth(Month.JANUARY);
        birdDTO = chanceDTO.new BirdDTO();
        birdDTO.setName("Bird #2");
        birdDTO.setSize(BirdSize.BIG);
        birdDTO.setPhotoURL("http://example.org/bird/images/2.jpg");
        birdDTO.getColors().add(Color.BLUE);
        birdDTO.getColors().add(Color.YELLOW);
        chanceDTO.setBird(birdDTO);
        reserveDTO = chanceDTO.new NaturalReserveDTO();
        reserveDTO.setName("Natural Reserve #2");
        chanceDTO.setReserve(reserveDTO);
        chances.add(chanceDTO);

        return chances;
    }

}
