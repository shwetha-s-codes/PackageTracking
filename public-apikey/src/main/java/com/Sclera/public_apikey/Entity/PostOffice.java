package com.Sclera.public_apikey.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostOffice {



    @JsonProperty("Name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // getter and setter
}
