package com.scentbird.common.payload.requests;

import com.scentbird.common.stomp.StompDestinations;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class HelloRequest extends StompRequest {

    private String name;

    @Override
    public String getDestination() {
        return StompDestinations.HELLO;
    }

}
