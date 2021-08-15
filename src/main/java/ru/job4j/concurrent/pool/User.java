package ru.job4j.concurrent.pool;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return
                Objects.equals(userName, user.userName)
                && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email);
    }
}
