package com.stc.tracnghiemonline.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by: IntelliJ IDEA
 * User      : thangpx
 * Date      : 3/22/21
 * Time      : 10:40 PM
 * Filename  : InvalidException
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidException extends RuntimeException {
    public InvalidException() {
    }

    public InvalidException(String message) {
        super(message);
    }
}
