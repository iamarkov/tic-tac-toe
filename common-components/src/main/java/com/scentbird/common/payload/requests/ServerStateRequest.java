package com.scentbird.common.payload.requests;

import com.scentbird.common.stomp.StompDestinations;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ServerStateRequest extends StompRequest {

    @Override
    public String getDestination() {
        return StompDestinations.STATE;
    }

}