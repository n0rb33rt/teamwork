package com.norbert.backend.email;

public interface EmailSender {
    void send(SendingEmailRequest request);
}