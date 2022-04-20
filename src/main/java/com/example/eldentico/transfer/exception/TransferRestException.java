package com.example.eldentico.transfer.exception;

import org.springframework.http.HttpStatus;

public abstract class TransferRestException extends RuntimeException {

    public final HttpStatus httpStatus;


    public TransferRestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
