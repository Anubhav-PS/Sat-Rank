package com.anubhavps.satrank.models;

public class Marks {

    //unique identifier
    private String name;
    private String address;
    private String city;
    private String country;
    private String pinCode;
    private int score;


    public Marks() {
    }

    public Marks(String name, String address, String city, String country, String pinCode, int score) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.pinCode = pinCode;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
