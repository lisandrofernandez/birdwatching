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
package com.lisandro.birdwatching.core;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApiErrorTest {
    private static final String A_TEST_MESSAGE = "Test message";
    private static final String A_TEST_ERROR = "Test error";

    @Test
    void constructNullHttpStatusShouldThrowIllegalArgumentExceptionTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ApiError(null, A_TEST_MESSAGE, A_TEST_ERROR)
        );
    }

    @Test
    void errorListShouldBeMutableTest() {
        // when
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, A_TEST_MESSAGE);

        // then
        assertDoesNotThrow(() -> apiError.getErrors().add(A_TEST_ERROR));
    }
}
