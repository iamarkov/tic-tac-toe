package com.scentbird.server.stomp;

import com.scentbird.common.payload.requests.HelloRequest;
import com.scentbird.common.payload.requests.StompRequest;
import com.scentbird.common.stomp.StompDestinations;
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

    @MessageMapping(StompDestinations.HELLO)
    public void receiveHelloRequest(HelloRequest helloRequest, @Header("simpSessionId") String sessionId) {
        receive(helloRequest, sessionId);
    }

    private <R extends StompRequest> void receive(R request, String sessionId) {
        log.info("Received STOMP message from {} with sessionId: {}: {}", request.getUsername(), sessionId, request);
        Optional<StompRequestHandler> optionalHandler = Optional.ofNullable(requestHandlerMap.get(request.getDestination()));
        if (optionalHandler.isPresent()) {
            optionalHandler.get().handle(request);
        } else {
            log.error("Unrecognized STOMP destination {}, ignoring message: {}", request.getDestination(), request);
        }
    }

}
