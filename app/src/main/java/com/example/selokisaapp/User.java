package com.example.selokisaapp;

public class User {

    public String email,name,password,mobilePhone; // Fields that the app should get from the user.

    public User() {
        //Default constructor for empty object
        //// Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String name, String password, String mobilePhone) {
        //Default c
        this.email = email;
        this.name = name;
        this.password = password;
        this.mobilePhone = mobilePhone;
    }

   public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
