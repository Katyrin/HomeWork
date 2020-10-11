package com.company.homeWork2_2;

public class NonNumberException extends Exception{
    public NonNumberException(String message) {
        super(message);
    }

    public NonNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonNumberException(Throwable cause) {
        super(cause);
    }

    public NonNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String toString(){
        return getMessage() + " is not Number";
    }
}
