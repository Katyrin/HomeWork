package com.company.homeWork2_2;

public class NonSquareArrayException extends Exception {
    public NonSquareArrayException() {
    }

    public NonSquareArrayException(String message) {
        super(message);
    }

    public NonSquareArrayException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonSquareArrayException(Throwable cause) {
        super(cause);
    }

    public NonSquareArrayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String toString(){
        return "Array is not 4x4";
    }
}
