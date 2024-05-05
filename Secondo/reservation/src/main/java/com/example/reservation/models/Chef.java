package com.example.reservation.models;

public class Chef extends Person{
    private String specialization;

    public Chef(String fname, String lname, String email, String cellphone, String specialization) {
        super(fname, lname, email, cellphone);
        this.specialization = specialization;
    }

    public Chef() {
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
