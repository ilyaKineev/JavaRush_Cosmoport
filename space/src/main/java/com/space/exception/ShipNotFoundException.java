package com.space.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Обработка ошибок
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShipNotFoundException extends RuntimeException {

    public ShipNotFoundException() {
    }

    public ShipNotFoundException(String message) {
        super(message);
    }

    public ShipNotFoundException(Throwable cause) {
        super(cause);
    }

    public ShipNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
