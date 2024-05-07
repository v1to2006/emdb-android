package com.example.emdb.classes;

public class InputValidator {
    public boolean validInput(String input) {
        if (input.trim().isEmpty())
            return false;

        return true;
    }

    public boolean validUsername(String username) {
        if (!validInput(username))
            return false;

        return true;
    }

    public boolean validEmail(String email) {
        if (!validInput(email))
            return false;

        if (!email.contains("@") || !email.contains("."))
            return false;

        return true;
    }

    public boolean validPassword(String password) {
        return true;
    }
}
