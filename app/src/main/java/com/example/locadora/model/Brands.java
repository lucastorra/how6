package com.example.locadora.model;

import java.io.Serializable;

public class Brands implements Serializable {
    private Long id;
    private String description;

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
}
