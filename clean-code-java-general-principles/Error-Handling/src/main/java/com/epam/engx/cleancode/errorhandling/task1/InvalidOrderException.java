package com.epam.engx.cleancode.errorhandling.task1;

public class InvalidOrderException extends UserReportException{
    public InvalidOrderException(String message){
        super(message);
    }
}
