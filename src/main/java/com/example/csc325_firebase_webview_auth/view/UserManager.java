package com.example.csc325_firebase_webview_auth.view;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users = new ArrayList<>();


    public boolean registerUser(String name, String email, String password) {
        if (getUserByEmail(email) == null) {
            users.add(new User(name, email, password));
            return true;
        }
        return false;
    }


    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }


    public boolean validateUser(String email, String password) {
        User user = getUserByEmail(email);
        return user != null && user.getPassword().equals(password);
    }


    public boolean authenticateUser(String email, String password) {
        return validateUser(email, password);
    }
}
