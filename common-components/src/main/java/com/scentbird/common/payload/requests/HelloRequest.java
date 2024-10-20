package com.scentbird.common.payload.requests;

import com.scentbird.common.stomp.StompDestinations;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class HelloRequest extends StompRequest {

    private String name;

    public HelloRequest() {
        super(StompDestinations.HELLO);
    }

    public HelloRequest(String name) {
        this();
        this.name = name;
    }

}
