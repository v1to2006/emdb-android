package com.example.emdb.classes;

public class InputValidator {
    public boolean validInput(String input) {
        if (input.trim().isEmpty())
            return false;

        return true;
    }
}
