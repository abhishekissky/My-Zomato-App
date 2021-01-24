package com.example.myzomatoapp;

public class Restaurants {

    Restaurant restaurant;

    public Restaurants(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    @Override
    public String toString() {
        return "Restaurants{" +
                "restaurant=" + restaurant +
                '}';
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}


