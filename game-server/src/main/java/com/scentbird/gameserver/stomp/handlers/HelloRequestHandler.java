package com.scentbird.gameserver.stomp.handlers;

import com.scentbird.gameserver.payload.requests.HelloRequest;
import com.scentbird.gameserver.stomp.StompDestinations;
import org.springframework.stereotype.Component;

@Component
public class HelloRequestHandler implements StompRequestHandler<HelloRequest> {

    @Override
    public String getSupportedDestination() {
        return StompDestinations.HELLO;
    }

    @Override
    public void handle(HelloRequest request) {
        System.out.println(request);
    }

}
