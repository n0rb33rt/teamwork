package com.norbert.backend.exception;

import lombok.Builder;

@Builder
public record ApiException(
        String error,
        String message,
        String path
) {
}
