package org.oop2023.utils.exceptions;

public abstract class APIException extends Exception {
    /**
     * Default constructor.
     */
    public APIException(String message) {
        super(message);
    }
}
