package com.workintech.zoo.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ZooException extends RuntimeException{
    private HttpStatus httpStatus;

    public ZooException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public ZooException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.httpStatus = status;
    }
}
