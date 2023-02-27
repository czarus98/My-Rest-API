package com.restapi.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsError extends RuntimeException {

    public ResourceAlreadyExistsError(String message) {
        super(message);
    }
}