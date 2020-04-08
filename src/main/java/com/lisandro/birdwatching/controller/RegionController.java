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

import com.lisandro.birdwatching.dto.RegionDTO;
import com.lisandro.birdwatching.service.RegionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Region rest controller.
 *
 * @author Lisandro Fernandez
 */
@RestController
@RequestMapping(RegionController.BASE_URL)
public class RegionController {

    public static final String BASE_URL = "/api/v1/regions";

    private final RegionService regionService;

    /**
     * Constructs a {@link RegionController}.
     *
     * @param regionService  used by the controller
     */
    public RegionController (RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * Returns all regions data, requested by an HTTP GET request.
     *
     * @return of all regions data
     */
    @GetMapping
    public List<RegionDTO> all() {
        return regionService.findAllDTO();
    }

}
