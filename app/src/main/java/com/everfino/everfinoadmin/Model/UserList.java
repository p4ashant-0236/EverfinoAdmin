package com.everfino.everfinoadmin.Model;

import java.util.Date;

public class UserList {
public int userid;

    public String name;
    public String password;
    public String mobileno;
    public String email;
    public Date dob;
    public String gender;
    public String status;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserList(int userid, String name, String password, String mobileno, String email, Date dob, String gender, String status) {
        this.userid = userid;
        this.name = name;
        this.password = password;
        this.mobileno = mobileno;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.status = status;
    }


}
