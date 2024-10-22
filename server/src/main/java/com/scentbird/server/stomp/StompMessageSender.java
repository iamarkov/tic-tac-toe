package com.scentbird.server.stomp;


import com.scentbird.common.payload.responses.StompResponse;

public interface StompMessageSender {

    void sendToUser(String username, StompResponse response);

    void sendToAll(StompResponse response);

}
