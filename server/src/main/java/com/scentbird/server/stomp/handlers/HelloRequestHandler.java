package com.scentbird.server.stomp.handlers;

import com.scentbird.common.payload.requests.HelloRequest;
import com.scentbird.common.stomp.StompDestinations;
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
