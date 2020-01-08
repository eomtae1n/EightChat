package com.example.eomtaeyoon.eightchat.models;

import java.util.Date;
import java.util.List;

/**
 * Created by eomtaeyoon on 2017. 11. 18..
 */

public class Message {

    private String messageId;
    private User messageUser;
    private String chatId;
    private int unreadCount;
    private Date messageDate;
    private MessageType messageType;
    private List<String> readUserList;

    public enum MessageType {
        TEXT, PHOTO, EMOTICON, EXIT
    }
    public void setMessageDate(Date d) {
        this.messageDate = d;
    }
    public void setChatId(String c) {
        this.chatId = c;
    }
    public void setMessageId(String id) {
        this.messageId = id;
    }
    public void setMessageType(MessageType type) {
        this.messageType = type;
    }
    public void setMessageUser(User u) {
        this.messageUser = u;
    }
    public Date getMessageDate() {
        return messageDate;
    }
    public String getChatId() {
        return chatId;
    }
    public String getMessageId() {
        return messageId;
    }
    public MessageType getMessageType() {
        return messageType;
    }
    public User getMessageUser() {
        return messageUser;
    }

}
