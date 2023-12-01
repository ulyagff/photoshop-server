package org.example.exceptions;

public class ReadFileException extends RuntimeException {
    private ReadFileException(String message) {
        super(message);
    }

    public static ReadFileException UnexpectedEndOfFIle() {
        return new ReadFileException("Unexpected end of file");
    }

    public static ReadFileException NotPNMFIle() {
        return new ReadFileException("this is not a p6 or p5 PNM file");
    }
}
