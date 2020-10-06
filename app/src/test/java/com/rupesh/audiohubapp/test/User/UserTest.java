package com.rupesh.audiohubapp.test.User;

import com.rupesh.audiohubapp.model.User;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {

    @Test
    public void isValidPassword() {
        String name = "Person A";
        String email = "person@in.com";
        String passwordWithText = "Password123***";
        User user = new User(name, email, passwordWithText);

        // Using mock class
        UserHelperMock mock = new UserHelperMock();
        mock.setEmpty(false);
        mock.setPatternMatched(true);
        user.setHelper(mock);

        boolean validate = user.isValidRegisteredData();
        assertTrue(validate);
    }

    @Test
    public void isValidPasswordNoUpperCase() {
        String name = "Person A";
        String email = "person@in.com";
        String passwordWithText = "password123***";
        User user = new User(name, email, passwordWithText);

        // Using mock class
        UserHelperMock mock = new UserHelperMock();
        mock.setEmpty(false);
        mock.setPatternMatched(true);
        user.setHelper(mock);

        boolean validate = user.isValidRegisteredData();
        assertFalse(validate);
    }

    @Test
    public void isValidPasswordNoLowerCase() {
        String name = "Person A";
        String email = "person@in.com";
        String passwordWithText = "PASSWORD123***";
        User user = new User(name, email, passwordWithText);

        // Using mock class
        UserHelperMock mock = new UserHelperMock();
        mock.setEmpty(false);
        mock.setPatternMatched(true);
        user.setHelper(mock);

        boolean validate = user.isValidRegisteredData();
        assertFalse(validate);
    }

    @Test
    public void isValidPasswordNoNumber() {
        String name = "Person A";
        String email = "person@in.com";
        String passwordWithText = "Password***";
        User user = new User(name, email, passwordWithText);

        // Using mock class
        UserHelperMock mock = new UserHelperMock();
        mock.setEmpty(false);
        mock.setPatternMatched(true);
        user.setHelper(mock);

        boolean validate = user.isValidRegisteredData();
        assertFalse(validate);
    }

    @Test
    public void isValidPasswordNoCharacter() {
        String name = "Person A";
        String email = "person@in.com";
        String passwordWithText = "Password123";
        User user = new User(name, email, passwordWithText);

        // Using mock class
        UserHelperMock mock = new UserHelperMock();
        mock.setEmpty(false);
        mock.setPatternMatched(true);
        user.setHelper(mock);

        boolean validate = user.isValidRegisteredData();
        assertFalse(validate);
    }

    @Test
    public void isValidLoginData() {
        String email = "person@in.com";
        String passwordWithText = "Password123*";
        User user = new User(email, passwordWithText);

        UserHelperMock mock = new UserHelperMock();
        mock.setEmpty(false);
        mock.setPatternMatched(true);
        user.setHelper(mock);

        Boolean validate = user.isValidLoginData();

        assertTrue(validate);

    }

    @Test
    public void isValidLoginDataWrongPasswordFormat() {
        String email = "person@in.com";
        String passwordWithText = "password123";
        User user = new User(email, passwordWithText);

        UserHelperMock mock = new UserHelperMock();
        mock.setEmpty(false);
        mock.setPatternMatched(true);
        user.setHelper(mock);

        Boolean validate = user.isValidLoginData();

        assertFalse(validate);
    }
}