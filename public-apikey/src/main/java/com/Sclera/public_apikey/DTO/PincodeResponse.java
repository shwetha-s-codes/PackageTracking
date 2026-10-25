package com.Sclera.public_apikey.DTO;

import com.Sclera.public_apikey.Entity.PostOffice;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PincodeResponse {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Status")
    private String status;


    @JsonProperty("PostOffice")
    private List<PostOffice> postOffice;

    public List<PostOffice> getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(List<PostOffice> postOffice) {
        this.postOffice = postOffice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
