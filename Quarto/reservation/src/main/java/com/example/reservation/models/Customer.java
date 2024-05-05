package com.example.reservation.models;

public class Customer extends Person{
    private String intollerance;

    public Customer(String fname, String lname, String email, String cellphone, String intollerance) {
        super(fname, lname, email, cellphone);
        this.intollerance = intollerance;
    }

    public Customer() {
    }

    public String getIntollerance() {
        return intollerance;
    }

    public void setIntollerance(String intollerance) {
        this.intollerance = intollerance;
    }
}
