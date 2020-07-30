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

import java.time.LocalDate;

import static com.lisandro.birdwatching.controller.ChanceController.BASE_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Functional tests of chance use cases.
 *
 * @author Lisandro Fernandez
 */
@SpringBootTest
@AutoConfigureMockMvc
class ChanceFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getChancesByDateOkTest() throws Exception {
        // given
        LocalDate date = LocalDate.of(2000, 6, 1);
        String isoDate = date.toString();

        // when
        mockMvc.perform(get(BASE_URL + "/?date={date}", isoDate).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) // then
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(
                      "["
                    + "    {"
                    + "        'bird': {"
                    + "            'colors': ['BLACK', 'BROWN', 'GREY', 'YELLOW'],"
                    + "            'name': 'Spoonbill',"
                    + "            'photoURL': 'https://en.wikipedia.org/wiki/File:Roseate_Spoonbill_-_Myakka_River_State_Park.jpg',"
                    + "            'size': 'BIG'"
                    + "        },"
                    + "        'month': 'JUNE',"
                    + "        'reserve': {'name': 'Doñana National Park'}"
                    + "    },"
                    + "    {"
                    + "        'bird': {"
                    + "            'colors': ['BLACK', 'WHITE'],"
                    + "            'name': 'Great bustard',"
                    + "            'photoURL': 'https://upload.wikimedia.org/wikipedia/commons/9/90/Drop_f%C3%BAzat%C3%BD_%28Otis_tarda%29_%282416576086%29.jpg',"
                    + "            'size': 'BIG'"
                    + "        },"
                    + "        'month': 'JUNE',"
                    + "        'reserve': {'name': 'Llanos de Caceres'}"
                    + "    },"
                    + "    {"
                    + "        'bird': {"
                    + "            'colors': ['BLACK', 'BROWN', 'GREY', 'WHITE'],"
                    + "            'name': 'Falcon',"
                    + "            'photoURL': 'https://upload.wikimedia.org/wikipedia/commons/3/39/Brown-Falcon%2C-Vic%2C-3.1.2008.jpg',"
                    + "            'size': 'MEDIUM'"
                    + "        },"
                    + "        'month': 'JUNE',"
                    + "        'reserve': {'name': 'Monfragüe'}"
                    + "    }"
                    + "]", true));
    }

    @Test
    void getChancesByDateWhenNoDateShouldReturnBadRequestTest() throws Exception {
        // when
        mockMvc.perform(get(BASE_URL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest()); // then
    }
}
