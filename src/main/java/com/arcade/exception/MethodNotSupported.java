package com.arcade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class MethodNotSupported extends BaseException {

    public MethodNotSupported(String message) {
        super(message);
    }

    public MethodNotSupported(String message, Throwable cause) {
        super(message, cause);
    }

}
