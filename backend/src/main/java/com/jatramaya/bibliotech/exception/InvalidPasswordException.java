package com.jatramaya.bibliotech.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String messagae) {
        super(messagae);
    }
}
