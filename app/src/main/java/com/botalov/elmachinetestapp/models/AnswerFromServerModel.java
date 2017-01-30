package com.botalov.elmachinetestapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AnswerFromServerModel {

    @JsonProperty("data")
    private List<PhotoModel> photos;

    public AnswerFromServerModel(){

    }

    public List<PhotoModel> getPhotos(){return this.photos;}

}
