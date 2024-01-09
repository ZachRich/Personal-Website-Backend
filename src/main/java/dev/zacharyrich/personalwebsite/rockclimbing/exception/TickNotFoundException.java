package dev.zacharyrich.personalwebsite.rockclimbing.exception;

public class TickNotFoundException extends RuntimeException {
    public TickNotFoundException(String message) {
        super(message);
    }
}
