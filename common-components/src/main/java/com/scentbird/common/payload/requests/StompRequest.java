package com.scentbird.common.payload.requests;

import lombok.Data;

@Data
public abstract class StompRequest {

    private final String destination;
    private String username;

    public StompRequest(String destination) {
        this.destination = destination;
    }

    public final String getDestination() {
        return destination;
    }

    public final String getUsername() {
        return username;
    }

}
