package ru.job4j.concurrent.pool;

public class User {

    private String userName;
    private String email;

    public User(String aName, String aEmail) {
        userName = aName;
        email = aEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
