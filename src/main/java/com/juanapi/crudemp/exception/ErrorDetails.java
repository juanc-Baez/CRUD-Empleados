package com.juanapi.crudemp.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
    private int status;
    private String message;
    private String details;

    public ErrorDetails(int status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

}

