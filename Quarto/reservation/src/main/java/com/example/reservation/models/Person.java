package com.example.reservation.models;

public class Person {
    private String fname;
    private String lname;
    private String email;
    private String cellphone;

    public Person(String fname, String lname, String email, String cellphone) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.cellphone = cellphone;
    }

    public Person() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
