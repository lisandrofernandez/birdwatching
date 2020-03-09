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
package com.lisandro.birdwatching.repository;

import java.time.Month;
import java.util.List;

import com.lisandro.birdwatching.model.Chance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChanceRepository extends JpaRepository<Chance, Long> {
    // join all eagerly
    @Query("SELECT DISTINCT c " +
           "FROM Chance c " +
           "LEFT JOIN FETCH c.bird b " +
           "LEFT JOIN FETCH b.colors " +
           "LEFT JOIN FETCH c.reserve r " +
           "WHERE c.probability >= :probability " +
           "AND c.month = :month " +
           "ORDER BY r.name, c.probability")
    List<Chance> findByMonthAndProbabilityGreaterThanEqual(
            @Param("month") Month month, @Param("probability") double probability
    );
}
