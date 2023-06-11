package com.anubhavps.satrank.models;

import androidx.annotation.NonNull;

public class Result {
    private String name;
    private String address;
    private String city;
    private String country;
    private String pinCode;
    private int score;
    private boolean passed;

    public Result() {
    }


    public Result(String name, String address, String city, String country, String pinCode, int score, boolean passed) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.pinCode = pinCode;
        this.score = score;
        this.passed = passed;
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

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    @NonNull
    @Override
    public String toString() {
        return "Result{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", score=" + score +
                ", passed=" + passed +
                '}';
    }
}
