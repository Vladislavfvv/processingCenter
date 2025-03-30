package com.java.exception;

public class DaoException extends RuntimeException {
    private String message;
    public DaoException(Throwable throwable) {
        super(throwable);//проксирование в конструктор RuntimeException, который также принимает Exception
    }
    public DaoException(String message, Throwable throwable) {
        super(message, throwable);//с сообщением
    }
}
