package com.agas.moviecollection.config.errors;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieException extends Throwable {

    public final Integer errorCode;
    public final String errorMessage;
    public final Integer statusCode;
    public final String statusMessage;
}
