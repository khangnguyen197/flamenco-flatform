package com.example.finalproject;

public class Users {
    String name, email, password, phoneNo, isAdmin;

    public Users() {
    }

    public Users(String name, String email, String password, String phoneNo, String isAdmin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getIsAdmin() {
        return isAdmin;
    }
}