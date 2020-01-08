package com.example.eomtaeyoon.eightchat.models;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * Created by eomtaeyoon on 2017. 11. 18..
 */

public class Chat {

    private String chatId;
    private String title;
    private Date createDate;
    private TextMessage lastMessage;
    private boolean disabled;
    private int totalUnreadCount;

    public void setChatId(String id) {
        this.chatId = id;
    }
    public void setCreateDate(Date d) {
        this.createDate = d;
    }
    public void setLastMessage(TextMessage lm) {
        this.lastMessage = lm;
    }
    public void setTitle(String t) {
        this.title = t;
    }
    public String getChatId() {
        return chatId;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public TextMessage getLastMessage() {
        return lastMessage;
    }
    public String getTitle() {
        return title;
    }


}
