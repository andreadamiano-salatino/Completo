package com.example.reservation.controller;

import com.example.reservation.models.Chef;
import com.example.reservation.models.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class InitController {
    @GetMapping("/init")
    public ArrayList init(){
        List<Chef> chefs = new ArrayList<Chef>();
        Chef chef1 = new Chef("Mario", "Rossi", "mario@rossi.it", "1234567890", "primi");
        Chef chef2 = new Chef("Paolo", "Rossi", "paolo@rossi.it", "1234567891", "secondi");
        Chef chef3 = new Chef("Antonio", "Rossi", "antonio@rossi.it", "1234567892", "dessert");
        Chef chef4 = new Chef("Maria", "Rossi", "maria@rossi.it", "1234567893", "primi");
        Chef chef5 = new Chef("Giulia", "Rossi", "giulia@rossi.it", "1234567894", "secondi");

        chefs.add(chef1);
        chefs.add(chef2);
        chefs.add(chef3);
        chefs.add(chef4);
        chefs.add(chef5);

        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        Restaurant restaurant1 = new Restaurant("MangiareBene", "Via Vecchia 1");
        Restaurant restaurant2 = new Restaurant("MangiareSano", "Via Nuova 1");

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        ArrayList list = new ArrayList<>();
        list.add(chef1);
        list.add(chef2);
        list.add(chef3);
        list.add(chef4);
        list.add(chef5);

        list.add(restaurant1);
        list.add(restaurant2);

        return list;
    }
}
