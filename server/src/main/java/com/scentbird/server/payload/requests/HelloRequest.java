package com.scentbird.server.payload.requests;

import com.scentbird.server.stomp.StompDestinations;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class HelloRequest extends StompRequest {

    private String name;

    public HelloRequest() {
        super(StompDestinations.HELLO);
    }

}
