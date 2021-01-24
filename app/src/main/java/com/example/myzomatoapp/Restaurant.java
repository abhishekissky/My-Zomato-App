package com.example.myzomatoapp;

public class Restaurant {

    Location location;
    String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "location=" + location +
                '}';
    }

    public Location getLocation() {
        return location;
    }

    public Restaurant(Location location) {
        this.location = location;
    }
}
