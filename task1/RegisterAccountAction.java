package com.epam.engx.cleancode.functions.task1;

import com.epam.engx.cleancode.functions.task1.thirdpartyjar.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.epam.engx.cleancode.functions.task1.thirdpartyjar.CheckStatus.OK;

public class RegisterAccountAction {


    private static final int USER_NAME_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 9;

    private PasswordChecker passwordChecker;
    private AccountManager accountManager;

    public void register(Account account) {
        validateCredentials(account);
        storeAccountDetails(account);
        createNewAccount(account);
    }

    private void validateCredentials(Account account) {
        validateUserNameLength(account.getName());
        validatePassword(account);
    }

    private void validatePassword(Account account) {
        validatePasswordLength(account.getPassword());
        validatePasswordContext(account.getPassword());
    }

    private void validateUserNameLength(String accountName) {
        int userNameLength = accountName.length();
        if (userNameLength < USER_NAME_MIN_LENGTH){
            throw new WrongAccountNameException();
        }
    }

    private void validatePasswordLength(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new TooShortPasswordException();
        }
    }

    private void validatePasswordContext(String password) {
        if (passwordChecker.validate(password) != OK) {
            throw new WrongPasswordException();
        }
    }

    private void storeAccountDetails(Account account) {
        storeAccountCreationDate(account);
        storeAddresses(account);
    }

    private void storeAccountCreationDate(Account account) {
        account.setCreatedDate(new Date());
    }
    private void storeAddresses(Account account) {
        List<Address> addresses = new ArrayList<Address>();
        addresses.add(account.getHomeAddress());
        addresses.add(account.getWorkAddress());
        addresses.add(account.getAdditionalAddress());
        account.setAddresses(addresses);
    }

    private void createNewAccount(Account account) {
        accountManager.createNewAccount(account);
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setPasswordChecker(PasswordChecker passwordChecker) {

        this.passwordChecker = passwordChecker;
    }

}
