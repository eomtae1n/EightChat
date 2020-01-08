package com.example.eomtaeyoon.eightchat.models;

/**
 * Created by eomtaeyoon on 2017. 11. 18..
 */

public class PhotoMessage extends Message{

    private String photoUrl;

    public void setPhotoUrl(String url) {
        this.photoUrl = url;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }
}
