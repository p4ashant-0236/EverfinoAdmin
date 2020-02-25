package com.everfino.everfinoadmin.Model;

public class RestList {
    public  int restid;
    public String mobileno;
    public String email;
    public String restname;
    public String restdesc;
    public  String address;
    public  String city;
    public String status;

    public int getRestid() {
        return restid;
    }

    public void setRestid(int restid) {
        this.restid = restid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobilenumber) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRestname() {
        return restname;
    }

    public void setRestname(String restname) {
        this.restname = restname;
    }

    public String getRestdesc() {
        return restdesc;
    }

    public void setRestdesc(String restdesc) {
        this.restdesc = restdesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RestList(int restid, String mobileno, String email, String restname, String restdesc, String address, String city, String status) {
        this.restid = restid;
        this.mobileno = mobileno;
        this.email = email;
        this.restname = restname;
        this.restdesc = restdesc;
        this.address = address;
        this.city = city;
        this.status = status;
    }
}
