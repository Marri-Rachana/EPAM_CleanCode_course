package com.epam.engx.cleancode.dry.task1;

import com.epam.engx.cleancode.dry.task1.thirdpartyjar.Profitable;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class InterestCalculator implements Profitable {

    private static final int SENIOR_ELIGIBLE_AGE = 60;
    private static final double INTEREST_PERCENT = 4.5d;
    private static final double SENIOR_INTEREST_PERCENT = 5.5d;
    private static final int BONUS_AGE = 13;
    private static final int LEAP_YEAR_SHIFT = 1;
    private static final int HUNDRED_PERCENT = 100;


    public BigDecimal calculateInterest(AccountDetails accountDetails) {
        return isAccountStartedAfterBonusAge(accountDetails) ? getInterestAfterBonusAge(accountDetails) : BigDecimal.ZERO;
    }

    private boolean isAccountStartedAfterBonusAge(AccountDetails accountDetails) {
        int ageDuringAccountOpen = getDurationBetweenDatesInYears(accountDetails.getBirth(), accountDetails.getStartDate());
        return  ageDuringAccountOpen > BONUS_AGE;
    }

    private int getDurationBetweenDatesInYears(Date from, Date to) {
        Calendar startCalendar = getGregorianTime(from);
        Calendar endCalendar = getGregorianTime(to);

        int difference = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        return isLeapYear(endCalendar, startCalendar) ? difference-1 : difference;
    }

    private Calendar getGregorianTime(Date time){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        return calendar;
    }

    private boolean isLeapYear(Calendar endCalendar, Calendar startCalendar){
        return endCalendar.get(Calendar.DAY_OF_YEAR) + LEAP_YEAR_SHIFT < startCalendar.get(Calendar.DAY_OF_YEAR);
    }

    private BigDecimal getInterestAfterBonusAge(AccountDetails accountDetails) {
        double interest = getInterestBasedOnAge(accountDetails);
        return BigDecimal.valueOf(interest);
    }

    private double getInterestBasedOnAge(AccountDetails accountDetails) {
        double principalAmount = accountDetails.getBalance().doubleValue();
        int accountDuration = getDurationSinceStartDateInYears(accountDetails.getStartDate());

        int age = getDurationSinceStartDateInYears(accountDetails.getBirth());

        return (SENIOR_ELIGIBLE_AGE <= age)
                ? principalAmount * accountDuration * SENIOR_INTEREST_PERCENT / HUNDRED_PERCENT
                : principalAmount * accountDuration * INTEREST_PERCENT / HUNDRED_PERCENT;
    }

    private int getDurationSinceStartDateInYears(Date startDate) {
        return getDurationBetweenDatesInYears(startDate, new Date());
    }
}
