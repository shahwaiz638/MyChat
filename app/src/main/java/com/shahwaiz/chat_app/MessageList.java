package com.shahwaiz.chat_app;

public class MessageList {
    private String name,email,mobile,dp,lastMsg,chatKey;
    private int unreadMessages;

    public MessageList(String name, String email, String mobile,String dp,String lastMsg, int unreadMessages,String chatKey) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.dp=dp;
        this.lastMsg=lastMsg;
        this.chatKey=chatKey;
        this.unreadMessages = unreadMessages;
    }

    public String getChatKey() {
        return chatKey;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public String getName() {
        return name;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }
}
