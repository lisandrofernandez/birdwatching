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

import java.time.LocalDate;
import java.util.List;

import com.lisandro.birdwatching.dto.ChanceDTO;
import com.lisandro.birdwatching.service.ChanceService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Chance rest controller.
 *
 * @author Lisandro Fernandez
 */
@RestController
@RequestMapping(ChanceController.BASE_URL)
public class ChanceController {

    public static final String BASE_URL = "/api/v1/chances";

    private final ChanceService chanceService;

    /**
     * Constructs a {@link ChanceController}.
     *
     * @param chanceService  used by the controller
     */
    public ChanceController (ChanceService chanceService) {
        this.chanceService = chanceService;
    }

    /**
     * Returns a all birds that can be seen in a natural reserve in a given
     * month, requested by an HTTP GET request.<p>
     *
     * The method accepts a date but only the month is used.
     *
     * @param date  date of the year, only month is used
     * @return all birds that can be seen in a natural reserve for the given
     *         month
     */
    @GetMapping
    public List<ChanceDTO> findByMonth(
            @RequestParam(name = "date", required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {
        return chanceService.findByMonth(date.getMonth());
    }

}
