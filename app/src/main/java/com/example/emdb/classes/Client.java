package com.example.emdb.classes;

import com.example.emdb.models.User;

public class Client {
    private static final Client instance = new Client();

    private Client() {}

    public static Client getInstance() {
        return instance;
    }

    public User loggedUser = null;

    public void logIn(User user) {
        loggedUser = user;
    }

    public void logOut() {
        loggedUser = null;
    }

    public boolean userLoggedIn() {
        return loggedUser != null;
    }
}
