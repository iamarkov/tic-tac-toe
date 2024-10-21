package com.scentbird.player.stomp;

import com.scentbird.common.payload.requests.StompRequest;

public interface StompClientService {
    void send(StompRequest request);
}
