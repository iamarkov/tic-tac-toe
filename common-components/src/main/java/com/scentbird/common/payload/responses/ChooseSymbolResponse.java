package com.scentbird.common.payload.responses;

import com.scentbird.common.stomp.StompDestinations;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChooseSymbolResponse extends StompResponse {

    public String roomId;

    @Override
    public String getDestination() {
        return StompDestinations.CHOOSE_SYMBOL;
    }

}
