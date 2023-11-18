package com.norbert.backend.email;

public record SendingEmailRequest(
        String message, String email,String subject
) {
}
