package com.example.emdb.classes;

import java.time.Year;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    public boolean validInput(String input) {
        return !input.trim().isEmpty();
    }

    public boolean validUsername(String username) {
        return validInput(username);
    }

    public boolean validEmail(String email) {
        if (!validInput(email))
            return false;

        return email.contains("@") && email.contains(".");
    }

    public boolean passwordMatching(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public boolean validPassword(String password) {
        return password.length() >= 6 && containsUppercase(password) && containsSpecialSymbol(password);
    }

    public boolean containsUppercase(String password) {
        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsSpecialSymbol(String password) {
        Pattern pattern = Pattern.compile("[!#?&%$€£@]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public boolean validLength(String lengthInput) {
        int length;

        int minLength = 0;
        int maxLength = 6000;

        try {
            length = Integer.parseInt(lengthInput);
        } catch (NumberFormatException e) {
            return false;
        }

        return length >= minLength && length <= maxLength;
    }

    public boolean validReleaseYear(String releaseYearInput) {
        int year;

        int minYear = 1888;
        int maxYear = Year.now().getValue() + 10;

        try {
            year = Integer.parseInt(releaseYearInput);
        } catch (NumberFormatException e) {
            return false;
        }

        return year >= minYear && year <= maxYear;
    }

    public boolean validRating(String ratingInput) {
        float rating;

        float maxRating = 10.0f;
        float minRating = 0.0f;

        try {
            rating = Float.parseFloat(ratingInput);
        } catch (NumberFormatException e) {
            return false;
        }

        return rating >= minRating && rating <= maxRating;
    }
}