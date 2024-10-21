package com.scentbird.common.payload.requests;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.stomp.StompDestinations;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ChooseSymbolRequest extends StompRequest {

    private TicTacToeSymbol symbol;
    private String roomId;

    @Override
    public String getDestination() {
        return StompDestinations.CHOOSE_SYMBOL;
    }

}
