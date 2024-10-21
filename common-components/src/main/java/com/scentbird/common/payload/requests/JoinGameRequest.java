package com.scentbird.common.payload.requests;

import com.scentbird.common.stomp.StompDestinations;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class JoinGameRequest extends StompRequest {

    @Override
    public String getDestination() {
        return StompDestinations.JOIN_GAME;
    }

}
