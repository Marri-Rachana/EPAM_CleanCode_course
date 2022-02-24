package com.epam.engx.cleancode.errorhandling.task1;

public class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String message) {
        super(message);
    }
}
