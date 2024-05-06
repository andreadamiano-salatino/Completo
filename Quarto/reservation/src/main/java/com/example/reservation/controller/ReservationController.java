package com.example.reservation.controller;

import com.example.reservation.models.Booking;
import com.example.reservation.models.Customer;
import com.example.reservation.repositories.DatabaseComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.sound.sampled.FloatControl;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {
    Customer customer = new Customer();
    Booking booking = new Booking();
    @Autowired
    DatabaseComponent db;

    @GetMapping("/")
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView("prenotazione");
        //modelAndView.setViewName("prenotazione");
        return  modelAndView;
    }

    @PostMapping("/booking")
    public ModelAndView CreateReservation(@RequestBody MultiValueMap<String, String> userFormData) throws SQLException {

        //System.out.println(userFormData.toString());

        String fname = userFormData.get("fname").get(0);
        String lname = userFormData.get("lname").get(0);
        String email = userFormData.get("email").get(0);
        String cellphone = userFormData.get("phone").get(0);
        String note = userFormData.get("note").get(0);
        int seats = Integer.parseInt(userFormData.get("seats").get(0));
        String restaurant = userFormData.get("restaurant").get(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDate = LocalDate.parse(userFormData.get("start-date").get(0),formatter).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(userFormData.get("end-date").get(0), formatter).atStartOfDay();

        customer.setFname(fname);
        customer.setLname(lname);
        customer.setEmail(email);
        customer.setCellphone(cellphone);

        booking.setNote(note);
        booking.setSeats(seats);

        //ArrayList<LocalDateTime> availableDates = new ArrayList<LocalDateTime>();
        //LocalDateTime date1 = startDate.plusHours(13);
        //LocalDateTime date2 = startDate.plusHours(15);
        //LocalDateTime date3 = startDate.plusHours(17);

        //availableDates.add(date1);
        //availableDates.add(date2);
        //availableDates.add(date3);

        int restaurantId = db.GetRestaurantId(restaurant);
        booking.setRestaurantId(restaurantId);

        List<LocalDateTime> dates = db.GetAvailableDates(restaurant, startDate, endDate);

        ModelAndView modelAndView = new ModelAndView("date");
        modelAndView.addObject("date", dates);
        return modelAndView;
    }

    @PostMapping("/confirmation")
    public ModelAndView ConfirmReservation(@RequestBody MultiValueMap<String, String> userFormData) throws SQLException {
        LocalDateTime date = LocalDateTime.parse(userFormData.get("date-time").get(0));
        booking.setDate(date);

        int customerId = db.insertCustomerAndGetId(customer);
        booking.setCustomerId(customerId);

        int bookingId = db.insertBookingAndGetId(booking);
        LocalDateTime data = booking.getDate();

        ModelAndView modelAndView = new ModelAndView("conferma");
        modelAndView.addObject("codice", bookingId);
        modelAndView.addObject("data", data);
        return modelAndView;
    }
}
