package com.example.eomtaeyoon.eightchat.models;

/**
 * Created by eomtaeyoon on 2017. 11. 18..
 */

public class TextMessage extends Message{

    private String messageText;

    public void setMessageText(String m) {
        this.messageText = m;
    }
    public String getMessageText() {
        return messageText;
    }

}
