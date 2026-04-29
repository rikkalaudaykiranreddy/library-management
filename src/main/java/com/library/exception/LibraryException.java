package com.library.exception;

/**
 * Thrown when a business-level integrity rule is violated
 * (e.g., duplicate ISBN, missing author, etc.).
 */
public class LibraryException extends RuntimeException {

    public LibraryException(String message) {
        super(message);
    }

    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }
}
