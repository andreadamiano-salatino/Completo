package com.example.reservation.models;

import java.time.LocalDateTime;

public class Booking {
    private String note;
    private LocalDateTime date;
    private int seats;
    private int RestaurantId;
    private int CustomerId;

    public Booking() {
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        RestaurantId = restaurantId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }
}
