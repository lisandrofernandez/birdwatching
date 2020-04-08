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

import java.util.Collection;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Exception for business errors.<p>
 *
 * This class also provides utility static methods which acts as assertions.
 *
 * @author Lisandro Fernandez
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a {@link BusinessException}.
     */
    public BusinessException() { }

    /**
     * Constructs a {@link BusinessException}.
     *
     * @param message  the message
     */
    public BusinessException(String message) {
        super(message);
    }


    // begin of assertion methods

    /**
     * Asserts that a collection is not {@literal null} or empty.
     *
     * @param collection  a collection to check
     * @param message  the exception message to use if the assertion fails
     * @throws BusinessException if the collection is null or empty
     */
    public static void empty(Collection<?> collection, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    /**
     * Asserts that a string is not {@literal null} or empty.
     *
     * @param string  a string to check
     * @param message  the exception message to use if the assertion fails
     * @throws BusinessException if the string is null or empty
     */
    public static void hasLength(String string, String message) {
        if (!StringUtils.hasLength(string)) {
            throw new BusinessException(message);
        }
    }

    /**
     * Asserts that a boolean expression is {@literal true}.
     *
     * @param expression  a boolean expression
     * @param message  the exception message to use if the assertion fails
     * @throws BusinessException if the {@code expression} is {@code false}
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    /**
     * Asserts that an object is not {@code null}.
     *
     * @param object   an object to check
     * @param message  the exception message to use if the assertion fails
     * @throws BusinessException if the {@code object} is {@code null}
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

}
