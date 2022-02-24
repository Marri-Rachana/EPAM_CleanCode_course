package com.epam.engx.cleancode.errorhandling.task1;

import com.epam.engx.cleancode.errorhandling.task1.thirdpartyjar.Order;
import com.epam.engx.cleancode.errorhandling.task1.thirdpartyjar.User;
import com.epam.engx.cleancode.errorhandling.task1.thirdpartyjar.UserDao;

import java.util.List;

public class UserReportBuilder {


    private UserDao userDao;

    public Double getUserTotalOrderAmount(String userId) throws InvalidUserException, InvalidOrderException,
            InvalidTotalAmountException, DatabaseConnectionException {

        User user = getValidUserById(userId);

        List<Order> orders = getValidOrders(user);

        return calculateTotalOrderAmount(orders);
    }
    private List<Order> getValidOrders(User user) throws InvalidOrderException {
        List<Order> orders = user.getAllOrders();
        validateOrder(orders);
        return orders;
    }

    private void validateDatabaseConnection() throws DatabaseConnectionException {
        if (userDao == null) {
            throw new DatabaseConnectionException("technicalError");
        }
    }

    private User getValidUserById(String userId) throws InvalidUserException,DatabaseConnectionException{
        validateDatabaseConnection();
        User user = userDao.getUser(userId);
        checkIfUserExsist(user);
        return user;
    }

    private void checkIfUserExsist(User user) throws InvalidUserException{
        if (user == null) {
            throw new InvalidUserException("WARNING: User ID doesn't exist.");
        }
    }

    private void validateOrder(List<Order> orders) throws InvalidOrderException{
        if (orders.isEmpty()) {
            throw new InvalidOrderException("WARNING: User have no submitted orders.");
        }
    }

    private Double calculateTotalOrderAmount(List<Order> orders) throws InvalidTotalAmountException{
        Double sum = 0.0;
        for (Order order : orders) {
            if (order.isSubmitted()) {
                sum += getTotalAmountOfOrder(order);
            }
        }
        return sum;
    }

    private Double getTotalAmountOfOrder(Order order) throws InvalidTotalAmountException{
        Double total = order.total();
        validateTotalAmount(total);
        return total;
    }

    private void validateTotalAmount(Double total) throws InvalidTotalAmountException{
        if(total < 0){
            throw new InvalidTotalAmountException("ERROR: Wrong order amount.");
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
