package com.scentbird.common.payload.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class StompRequest {

    private String username;

    public abstract String getDestination();

    public final String getUsername() {
        return username;
    }

}
