package com.epam.engx.cleancode.errorhandling.task1;

public class InvalidUserException extends UserReportException{
    public InvalidUserException(String message){
        super(message);
    }
}
