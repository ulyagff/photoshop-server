package org.example.exceptions;

public class FilterException extends RuntimeException{
    private FilterException(String message) {
        super(message);
    }

    public static FilterException invalidFilterNumber() {
        return new FilterException("Filter number must be between 1 and 3 (or equal)");
    }

}
