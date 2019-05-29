package com.lisandro.birdwatching.service;

public class NaturalReserveNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NaturalReserveNotFoundException() { }

    public NaturalReserveNotFoundException(String message) {
        super(message);
    }
}
