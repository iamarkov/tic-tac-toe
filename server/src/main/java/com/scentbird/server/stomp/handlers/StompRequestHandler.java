package com.scentbird.server.stomp.handlers;


import com.scentbird.common.payload.requests.StompRequest;
import com.scentbird.server.game.command.GameCommand;

public interface StompRequestHandler <R extends StompRequest> {

    String getSupportedDestination();

    GameCommand convert(R request, String sessionId);

}