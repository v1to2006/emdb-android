package com.example.emdb.models;

public class User {
    public User(int id, String username, String email, String password) {
        Id = id;
        Username = username;
        Email = email;
        Password = password;
    }

    public User(int id, String username, String email) {
        Id = id;
        Username = username;
        Email = email;
    }

    public int Id;
    public String Username;
    public String Email;
    public String Password;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
