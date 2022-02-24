package com.epam.engx.cleancode.comments.task1;

import com.epam.engx.cleancode.comments.task1.thirdpartyjar.InvalidInputException;

public class MortgageInstallmentCalculator {

    private static final int MONTHS_IN_YEAR = 12;
    private static final int HUNDRED_PERCENT = 100;

    public static double calculateMonthlyPayment(
            int principalAmount, int termInYears, double interestRate) {

        validatePaymentParameters(principalAmount, termInYears, interestRate);
        return doCalculateMonthlyPayment(principalAmount, termInYears, interestRate);
    }

    private static void validatePaymentParameters(int principalAmount, int termInYears, double interestRate) {
        if (isAnyPaymentParametersInvalid(principalAmount, termInYears, interestRate)) {
            throw new InvalidInputException("Invalid values are not allowed");
        }
    }

    private static boolean isAnyPaymentParametersInvalid(int principalAmount, int termInYears, double interestRate) {
        return principalAmount < 0 || termInYears <= 0 || interestRate < 0;
    }

    private static double doCalculateMonthlyPayment(int principalAmount, int termInYears, double interestRate) {
        double interestRateInFraction = convertIntoDecimal(interestRate);
        double termInMonths = convertTermIntoMonths(termInYears);
        double monthlyInterestRate = interestRateInFraction /  MONTHS_IN_YEAR;

        return interestRateInFraction == 0 ? getMonthlyPaymentWithoutInterest(principalAmount, termInMonths)
                : getMonthlyPaymentWithInterest(principalAmount, monthlyInterestRate, termInMonths);
    }

    private static double convertIntoDecimal(double interestRate) {
        return interestRate /= HUNDRED_PERCENT;
    }

    private static double convertTermIntoMonths(double termInYears) {
        return termInYears *  MONTHS_IN_YEAR;
    }

    private static double getMonthlyPaymentWithoutInterest(int principalAmount, double termInMonths) {
        return principalAmount / termInMonths;
    }

    private static double getMonthlyPaymentWithInterest(int principalAmount, double monthlyInterestRate, double termInMonths) {
        return (principalAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -termInMonths));
    }
}
