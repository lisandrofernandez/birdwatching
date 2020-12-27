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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.lisandro.birdwatching.core.ApiError;
import com.lisandro.birdwatching.core.BusinessException;
import com.lisandro.birdwatching.dto.NaturalReserveDTO;
import com.lisandro.birdwatching.dto.NaturalReserveTupleDTO;
import com.lisandro.birdwatching.service.NaturalReserveNotFoundException;
import com.lisandro.birdwatching.service.NaturalReserveService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Natural reserve rest controller.
 *
 * @author Lisandro Fernandez
 */
@RestController
@RequestMapping(NaturalReserveController.BASE_URL)
public class NaturalReserveController {

    public static final String BASE_URL = "/api/v1/reserves";

    private static final Logger log = LoggerFactory.getLogger(NaturalReserveController.class);

    private final NaturalReserveService reserveService;

    /**
     * Constructs a {@link NaturalReserveController}.
     *
     * @param reserveService  used by the controller
     */
    public NaturalReserveController(NaturalReserveService reserveService) {
        this.reserveService = reserveService;
    }

    /**
     * Returns of all natural reserves data, requested by an HTTP GET request.
     *
     * @return all natural reserves data
     */
    @GetMapping
    public List<NaturalReserveTupleDTO> allTuples() {
        return reserveService.findAllTuples();
    }

    /**
     * Returns a natural reserve data given its ID, requested by an HTTP GET
     * request.
     *
     * @param id  ID of the natural reserve
     * @return the natural reserve data, or an {@literal ApiError} if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<NaturalReserveDTO> optionalNaturalReserveDTO = reserveService.findById(id);
        if (optionalNaturalReserveDTO.isEmpty()) {
            ApiError apiError = new ApiError(
                    HttpStatus.NOT_FOUND,
                    "Natural reserve not found",
                    "There is no natural reserve with ID = " + id
            );
            return ResponseEntity.status(apiError.getStatus()).body(apiError);
        }
        return ResponseEntity.ok(optionalNaturalReserveDTO.get());
    }

    /**
     * Creates a natural reserve, requested by an HTTP POST request.
     *
     * @param naturalReserveDTO  the natural reserve data
     * @return the created natural reserve data, or an {@link ApiError} if an
     *         error occurs
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody NaturalReserveDTO naturalReserveDTO) {
        ApiError apiError = null;
        try {
            naturalReserveDTO = reserveService.create(naturalReserveDTO);
        } catch (BusinessException e) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage());
        } catch (RuntimeException e) {
            log.error("Unable to create a natural reserve", e);
            apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal server error",
                    "There was an error while processing the request"
            );
        }
        if (apiError != null) {
            return ResponseEntity.status(apiError.getStatus()).body(apiError);
        }
        URI location = linkTo(methodOn(NaturalReserveController.class).findById(naturalReserveDTO.getId()))
                .toUri();
        return ResponseEntity.created(location).body(naturalReserveDTO);
    }

    /**
     * Updates a natural reserve, requested by an HTTP PUT request.
     *
     * @param id  ID of the natural reserve
     * @param naturalReserveDTO  the natural reserve data to update
     * @return the updated natural reserve data, or an {@link ApiError} if an
     *         error occurs
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody NaturalReserveDTO naturalReserveDTO) {
        naturalReserveDTO.setId(id); // use ID in URL
        ApiError apiError = null;
        try {
            naturalReserveDTO = reserveService.update(naturalReserveDTO);
        } catch(NaturalReserveNotFoundException e) {
            apiError = new ApiError(
                    HttpStatus.NOT_FOUND,
                    "Natural reserve not found",
                    "There is no natural reserve with ID = " + id
            );
        } catch (BusinessException e) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, "Bad Request", e.getMessage());
        } catch (RuntimeException e) {
            log.error("Unable to update a natural reserve", e);
            apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal server error",
                    "There was an error while processing the request"
            );
        }
        if (apiError != null) {
            return ResponseEntity.status(apiError.getStatus()).body(apiError);
        }
        return ResponseEntity.ok(naturalReserveDTO);
    }

    /**
     * Deletes a natural reserve, requested by an HTTP DELETE request.
     *
     * @param id  ID of the natural reserve
     * @return no content if deleted, or an {@link ApiError} if an error occurs
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiError apiError = null;
        try {
            if (!reserveService.deleteById(id)) {
                apiError = new ApiError(
                        HttpStatus.NOT_FOUND,
                        "Natural reserve not found",
                        "There is no natural reserve with ID = " + id
                );
            }
        } catch (RuntimeException e) {
            log.error("Unable to delete a natural reserve", e);
            apiError = new ApiError(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal server error",
                    "There was an error while processing the request"
            );
        }
        if (apiError != null) {
            return ResponseEntity.status(apiError.getStatus()).body(apiError);
        }
        return ResponseEntity.noContent().build();
    }

}
