package com.example.locadora.model;

import com.example.locadora.Brand;
import com.example.locadora.Color;

import java.io.Serializable;

public class Cars implements Serializable {
    private Long id;
    private String description;
    private Brands brand;
    private Colors color;

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

    public Brands getBrand() { return brand; }

    public void setBrand(Brands brand) { this.brand = brand; }

    public Colors getColor() { return color; }

    public void setColor(Colors color) { this.color = color; }


}
