package com.example.servicenovigrad;


import java.util.ArrayList;

public class UserList {
    private final ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public boolean userValidated(User user) {
        for (User value : users) {
            if (value.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean userExist(User user) {
        for (User value : users) {
            if (value.exists(user)) {
                return true;
            }
        }
        return false;
    }

    public User getUserInfo(User user) {
        for (User value : users) {
            if (value.exists(user)) {
                return value;
            }
        }
        return user;
    }

    public ArrayList<User> getList() {
        return users;
    }

    public User get(int i) {
        return users.get(i);
    }

    public boolean emailExists(String email) {
        for (User value : users) {
            if (value.emailEquals(email)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByName(String name) {
        for (User value : users) {
            if (value.getUsername().equals(name)) {
                return value;
            }
        }
        return null;
    }

    public void clear() {
        users.clear();
    }

    public ArrayList<String> getUsersName() {
        ArrayList<String> list = new ArrayList<>();
        for (User user : users) {
            list.add(user.getUsername());
        }
        return list;
    }
}
