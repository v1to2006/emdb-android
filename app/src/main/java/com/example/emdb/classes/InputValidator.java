package com.example.emdb.classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public boolean passwordMatching(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public boolean validPassword(String password) {
        if (password.length() < 6) {
            return false;
        }

        if (!containsUppercase(password)) {
            return false;
        }

        if (!containsSpecialSymbol(password)) {
            return false;
        }

        return true;
    }

    private boolean containsUppercase(String password) {
        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSpecialSymbol(String password) {
        Pattern pattern = Pattern.compile("[!#?&%$€£@]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
