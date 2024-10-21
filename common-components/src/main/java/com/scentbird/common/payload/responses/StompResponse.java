package com.scentbird.common.payload.responses;

import lombok.Data;

@Data
public abstract class StompResponse {

    public abstract String getDestination();

}
