package com.scentbird.gameserver.stomp.handlers;

import com.scentbird.gameserver.payload.requests.StompRequest;

public interface StompRequestHandler <R extends StompRequest> {

    String getSupportedDestination();

    void handle(R request);

}
