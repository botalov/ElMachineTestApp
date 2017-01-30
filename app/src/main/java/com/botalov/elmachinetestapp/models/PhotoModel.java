package com.botalov.elmachinetestapp.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PhotoModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("is_album")
    private boolean is_album;

    @JsonProperty("cover")
    private String cover;

    @JsonProperty("title")
    private String title;

    public PhotoModel(){

    }


    public String getId(){return this.id;}
    public boolean getIsAlbum(){return this.is_album;}
    public String getCover(){return this.cover;}
    public String getTitle(){return this.title;}


}
