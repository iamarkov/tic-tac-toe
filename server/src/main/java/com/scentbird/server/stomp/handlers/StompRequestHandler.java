package com.scentbird.server.stomp.handlers;


import com.scentbird.common.payload.requests.StompRequest;
import com.scentbird.server.game.command.GameCommand;

public interface StompRequestHandler <R extends StompRequest> {

    String getSupportedDestination();

    //this way we provide loose coupling between requests from players and commands to be used internally
    GameCommand convert(R request, String sessionId);
}