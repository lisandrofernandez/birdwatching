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

package com.lisandro.birdwatching.service;

import java.util.List;
import java.util.Optional;

import com.lisandro.birdwatching.dto.NaturalReserveDTO;
import com.lisandro.birdwatching.dto.NaturalReserveTupleDTO;

/**
 * Service interface which defines operations over natural reserves.
 *
 * @author Lisandro Fernandez
 */
public interface NaturalReserveService {

    /**
     * Returns a natural reserve data given its ID.
     *
     * @param id  ID of the natural reserve
     * @return the natural reserve data, or {@literal Optional#empty} if not
     *         found
     */
    Optional<NaturalReserveDTO> findById(long id);

    /**
     * Returns of all natural reserves data.
     *
     * @return all natural reserves data
     */
    List<NaturalReserveTupleDTO> findAllTuples();

    /**
     * Creates a natural reserve, requested by an HTTP POST request.
     *
     * @param naturalReserveDTO  the natural reserve data
     * @return the created natural reserve data
     * @throws IllegalArgumentException if naturalReserveDTO is {@literal null}
     * @throws com.lisandro.birdwatching.core.BusinessException if the name
     *         is {@literal null}, empty, contains only whitespaces or is too
     *         long.
     * @throws com.lisandro.birdwatching.core.BusinessException if the region
     *         does not exist
     */
    NaturalReserveDTO create(NaturalReserveDTO naturalReserveDTO);

    /**
     * Updates a natural reserve
     *
     * @param naturalReserveDTO  the natural reserve data to update
     * @return the updated natural reserve data
     * @throws IllegalArgumentException if naturalReserveDTO is {@literal null}
     * @throws com.lisandro.birdwatching.core.BusinessException if the id is
     *         {@literal null}
     * @throws com.lisandro.birdwatching.core.BusinessException if the name
     *         is {@literal null}, empty, contains only whitespaces or is too
     *         long.
     * @throws com.lisandro.birdwatching.core.BusinessException if the region
     *         does not exist
     */
    NaturalReserveDTO update(NaturalReserveDTO naturalReserveDTO);

    /**
     * Deletes a natural reserve
     *
     * @param id  ID of the natural reserve
     * @return {@literal true} if the natural reserve existed before deleting
     *         it, {@literal false} otherwise
     */
    boolean deleteById(long id);
}
