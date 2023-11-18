package com.norbert.backend.exception;

public class MailSendingException extends RuntimeException{
    public MailSendingException(String message) {
        super(message);
    }
}
