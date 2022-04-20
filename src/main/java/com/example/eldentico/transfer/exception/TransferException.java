package com.example.eldentico.transfer.exception;

import org.springframework.http.HttpStatus;

public class TransferException extends TransferRestException {

    public TransferException(String message, HttpStatus httpStatus){
        super(message, httpStatus);
    }
}
