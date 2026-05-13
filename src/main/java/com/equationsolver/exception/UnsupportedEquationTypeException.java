package com.equationsolver.exception;

import com.equationsolver.model.EquationType;

public class UnsupportedEquationTypeException extends RuntimeException {

    public UnsupportedEquationTypeException(EquationType type) {
        super("No solver or parser registered for equation type: " + type);
    }
}