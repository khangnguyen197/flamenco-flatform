package com.example.finalproject;

public class Users {
    String name, email, password, phone, isAdmin;

    public Users() {
    }

    public Users(String name, String email, String password, String phone, String isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getIsAdmin() {
        return isAdmin;
    }
}