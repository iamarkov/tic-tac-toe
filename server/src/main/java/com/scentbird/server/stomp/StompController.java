package com.scentbird.server.stomp;

import com.scentbird.common.payload.requests.ChooseSymbolRequest;
import com.scentbird.common.payload.requests.JoinGameRequest;
import com.scentbird.common.payload.requests.PlayRequest;
import com.scentbird.common.payload.requests.StompRequest;
import com.scentbird.common.stomp.StompDestinations;
import com.scentbird.server.game.command.GameCommand;
import com.scentbird.server.stomp.handlers.StompRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
public class StompController {

    private final Map<String, StompRequestHandler<?>> requestHandlerMap;

    public StompController(List<StompRequestHandler<?>> requestHandlers) {
        requestHandlerMap = new HashMap<>();
        for (StompRequestHandler<?> requestHandler : requestHandlers) {
            requestHandlerMap.put(requestHandler.getSupportedDestination(), requestHandler);
        }
    }

    @MessageMapping(StompDestinations.JOIN_GAME)
    public void receiveJoinGameRequest(JoinGameRequest joinGameRequest, @Header("simpSessionId") String sessionId) {
        receive(joinGameRequest, sessionId);
    }

    @MessageMapping(StompDestinations.CHOOSE_SYMBOL)
    public void receiveChooseSymbolRequest(ChooseSymbolRequest chooseSymbolRequest, @Header("simpSessionId") String sessionId) {
        receive(chooseSymbolRequest, sessionId);
    }

    @MessageMapping(StompDestinations.PLAY)
    public void receivePlayRequest(PlayRequest playRequest, @Header("simpSessionId") String sessionId) {
        receive(playRequest, sessionId);
    }

    private <R extends StompRequest> void receive(R request, String sessionId) {
        log.info("Received STOMP message from {} with sessionId: {}: {}", request.getUsername(), sessionId, request);
        Optional<StompRequestHandler> optionalHandler = Optional.ofNullable(requestHandlerMap.get(request.getDestination()));
        if (optionalHandler.isPresent()) {
            GameCommand gameCommand = optionalHandler.get().convert(request, sessionId);
            gameCommand.execute();
        } else {
            log.error("Unrecognized STOMP destination {}, ignoring message: {}", request.getDestination(), request);
        }
    }

}