package com.example.locadora.model;

import java.io.Serializable;
import java.util.Date;

public class Rents implements Serializable {
    private Long id;
    private String description;
    private Cars car;
    private String startdate;
    private String returndate;

    @Override
    public String toString() {
        return description.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cars getCar() { return car; }

    public void setCar(Cars car) { this.car = car; }

    public String getStartDate() {
        return startdate;
    }

    public void setStartDate(String startDate) {
        this.startdate = startDate;
    }

    public String getReturnDate() {
        return startdate;
    }

    public void setReturnDate(String returnDate) {
        this.returndate = returnDate;
    }

}
