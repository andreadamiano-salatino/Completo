package com.example.reservation.controller;

import com.example.reservation.models.Booking;
import com.example.reservation.models.Customer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.FloatControl;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
public class ReservationController {
    Customer customer = new Customer();
    Booking booking = new Booking();
    @PostMapping("/booking")
    public String CreateReservation(@RequestBody MultiValueMap<String, String> userFormData){

        System.out.println(userFormData.toString());

        String fname = userFormData.get("fname").get(0);
        String lname = userFormData.get("lname").get(0);
        String email = userFormData.get("email").get(0);
        String cellphone = userFormData.get("phone").get(0);
        String note = userFormData.get("note").get(0);
        int seats = Integer.parseInt(userFormData.get("seats").get(0));
        String restaurant = userFormData.get("restaurant").get(0);
        LocalDateTime startDate = LocalDateTime.parse(userFormData.get("start-date").get(0));
        LocalDateTime endDate = LocalDateTime.parse(userFormData.get("end-date").get(0));

        customer.setFname(fname);
        customer.setLname(lname);
        customer.setEmail(email);
        customer.setCellphone(cellphone);

        booking.setNote(note);
        booking.setSeats(seats);

        ArrayList<LocalDateTime> availableDates = new ArrayList<LocalDateTime>();
        LocalDateTime date1 = startDate.plusHours(13);
        LocalDateTime date2 = startDate.plusHours(15);
        LocalDateTime date3 = startDate.plusHours(17);

        availableDates.add(date1);
        availableDates.add(date2);
        availableDates.add(date3);

        return availableDates.toString();
    }

    @PostMapping("/confirmation")
    public String ConfirmReservation(@RequestBody MultiValueMap<String, String> userFormData){
        LocalDateTime date = LocalDateTime.parse(userFormData.get("date").get(0));
        booking.setDate(date);

        return "Codice di prenotazione 123456789C0 "+date.toString();
    }
}
