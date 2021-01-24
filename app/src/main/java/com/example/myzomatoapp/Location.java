package com.example.myzomatoapp;

public class Location {

    String address;
    String locality;
    String city;
    String zipcode;

    public String getLocality() {
        return locality;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", locality='" + locality + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public Location(String address, String locality, String city, String zipcode) {
        this.address = address;
        this.locality = locality;
        this.city = city;
        this.zipcode = zipcode;
    }
}
