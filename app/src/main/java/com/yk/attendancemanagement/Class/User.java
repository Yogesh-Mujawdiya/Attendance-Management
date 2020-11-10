package com.yk.attendancemanagement.Class;

import java.util.Date;

public class User {
    String UserId, Name, Email, Sex, Mobile, UserType, Password;
    Date date;

    public User(String userId,String name,String mobile, String email, String userType) {
        UserId = userId;
        Email = email;
        UserType = userType;
        Name = name;
        Mobile = mobile;
    }

    public User(String userId, String name,String mobile, String email, String password, String userType) {
        UserId = userId;
        Name = name;
        Email = email;
        Mobile = mobile;
        UserType = userType;
        Password = password;
    }

    public User(String userId, String name, String email, String sex, String mobile, String userType, String password, Date date) {
        UserId = userId;
        Name = name;
        Email = email;
        Sex = sex;
        Mobile = mobile;
        UserType = userType;
        this.date = date;
    }

    public User(){

    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserType() {
        return UserType;
    }


}
