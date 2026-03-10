package br.com.api.forum_hub.services;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
