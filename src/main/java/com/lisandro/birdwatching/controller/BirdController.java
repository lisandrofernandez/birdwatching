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

import java.util.List;

import com.lisandro.birdwatching.dto.Bird_TupleDTO;
import com.lisandro.birdwatching.service.BirdService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Bird rest controller.
 *
 * @author Lisandro Fernandez
 */
@RestController
@RequestMapping(BirdController.BASE_URL)
public class BirdController {

    public static final String BASE_URL = "/api/v1/birds";

    private final BirdService birdService;

    /**
     * Constructs a {@link BirdController}.
     *
     * @param birdService  used by the controller
     */
    public BirdController (BirdService birdService) {
        this.birdService = birdService;
    }

    /**
     * Returns of all birds data, requested by an HTTP GET request.
     *
     * @return all birds data
     */
    @GetMapping
    public List<Bird_TupleDTO> allTuples() {
        return birdService.findAllTuples();
    }

}
