package com.epam.engx.cleancode.errorhandling.task1;

public class InvalidTotalAmountException extends UserReportException{
    public InvalidTotalAmountException(String message){
        super(message);
    }
}
