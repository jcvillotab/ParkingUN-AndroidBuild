package com.example.parkingun.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Parking implements Serializable {
    public int id;
    public String nameP;
    public BigDecimal latitude;
    public BigDecimal longitude;
    public int capacity;
    public int actual;
    public String state;
    public String nearbyPlaces;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNearbyPlaces() {
        return nearbyPlaces;
    }

    public void setNearbyPlaces(String nearbyPlaces) {
        this.nearbyPlaces = nearbyPlaces;
    }
}
