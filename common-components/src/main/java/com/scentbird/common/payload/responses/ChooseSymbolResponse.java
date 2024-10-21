package com.scentbird.common.payload.responses;

import com.scentbird.common.stomp.StompDestinations;
import lombok.*;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChooseSymbolResponse extends StompResponse {

    public String roomId;

    @Override
    public String getDestination() {
        return StompDestinations.CHOOSE_SYMBOL;
    }

}
