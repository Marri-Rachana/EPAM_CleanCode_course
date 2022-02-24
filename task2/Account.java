package com.epam.engx.cleancode.functions.task2;


import com.epam.engx.cleancode.functions.task2.thirdpartyjar.Level;
import com.epam.engx.cleancode.functions.task2.thirdpartyjar.NotActivUserException;
import com.epam.engx.cleancode.functions.task2.thirdpartyjar.Review;
import com.epam.engx.cleancode.functions.task2.thirdpartyjar.User;

import java.util.TreeMap;

public abstract class Account implements User {

    private static final int MIN_VISIT_NUMBER = 1;
    private TreeMap<Integer, Level> thresholdsToLevel = new TreeMap<>();

    public Level getActivityLevel() {
        validateAccountForLevel();
        int reviewAnswersCount = getReviewAnswersCount();
        return getActivityLevelByReviews(reviewAnswersCount);
    }

    private void validateAccountForLevel() {
        if (isNotActiveUser())
            throw new NotActivUserException();
    }

    private boolean isNotActiveUser() {
        return isNotRegisteredAccount() || getVisitNumber() < MIN_VISIT_NUMBER;
    }

    private boolean isNotRegisteredAccount() {
        return !isRegistered();
    }

    private int getReviewAnswersCount() {
        int reviewAnswersCount = 0;
        for (Review r : getAllReviews()) {
            reviewAnswersCount += r.getAnswers().size();
        }
        return reviewAnswersCount;
    }

    private Level getActivityLevelByReviews(int reviewAnswersCount) {
        for (Integer threshold : thresholdsToLevel.keySet()) {
            if (reviewAnswersCount >= threshold) {
                return thresholdsToLevel.get(threshold);
            }
        }

        return getDefaultActivityLevel();
    }

    private Level getDefaultActivityLevel() {
        return Level.defaultLevel();
    }

    public void setThresholdsToLevel(TreeMap<Integer, Level> thresholdsToLevel) {
        this.thresholdsToLevel = thresholdsToLevel;
    }
}
