package com.equationsolver.exception;

public class InvalidEquationException extends RuntimeException {

    public InvalidEquationException(String input) {
        super("Cannot parse equation: \"" + input + "\"");
    }

    public InvalidEquationException(String input, Throwable cause) {
        super("Cannot parse equation: \"" + input + "\"", cause);
    }
}