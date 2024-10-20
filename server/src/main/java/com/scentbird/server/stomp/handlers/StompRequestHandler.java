package com.scentbird.server.stomp.handlers;


import com.scentbird.common.payload.requests.StompRequest;

public interface StompRequestHandler <R extends StompRequest> {

    String getSupportedDestination();

    void handle(R request);

}
