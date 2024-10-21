package com.scentbird.player.stomp.handlers;


import com.scentbird.common.payload.responses.StompResponse;
import com.scentbird.player.command.PlayerCommand;

import java.lang.reflect.Type;

public interface StompResponseHandler<R extends StompResponse> {
    String getSupportedDestination();

    Type getSupportedType();

    PlayerCommand convert(R response);
}
