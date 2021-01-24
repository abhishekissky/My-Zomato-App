package com.example.myzomatoapp;

public class Zomato {

    long results_found;
    Restaurants [] restaurants;

    public long getResults_found() {
        return results_found;
    }

    public Restaurants[] getRestaurants() {
        return restaurants;
    }

    public Zomato(long results_found, Restaurants[] restaurants) {
        this.results_found = results_found;
        // this.restaurants = restaurants;
    }
}


