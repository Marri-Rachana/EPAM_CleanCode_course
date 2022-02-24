package com.epam.engx.cleancode.errorhandling.task1;

import com.epam.engx.cleancode.errorhandling.task1.thirdpartyjar.Model;

public class UserReportController {

    private static final String USER_TOTAL_AMOUNT_MESSAGE = "userTotalMessage";
    private static final String USER_ORDER_TOTAL = "userTotal";

    private UserReportBuilder userReportBuilder;

    public String getUserTotalOrderAmountView(String userId, Model model){
        try {
            String totalMessage = getUserTotalMessage(userId);
            model.addAttribute(USER_TOTAL_AMOUNT_MESSAGE, totalMessage);
            return USER_ORDER_TOTAL;
        } catch(DatabaseConnectionException exception){
            return exception.getMessage();
        }
    }

    private String getUserTotalMessage(String userId) throws DatabaseConnectionException {
        try {
            Double amount = userReportBuilder.getUserTotalOrderAmount(userId);
            return "User Total: " + amount + "$";
        } catch(UserReportException exception){
            return exception.getMessage();
        }
    }

    public UserReportBuilder getUserReportBuilder() {
        return userReportBuilder;
    }

    public void setUserReportBuilder(UserReportBuilder userReportBuilder) {
        this.userReportBuilder = userReportBuilder;
    }
}
