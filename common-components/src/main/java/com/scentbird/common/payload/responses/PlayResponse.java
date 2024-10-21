package com.scentbird.common.payload.responses;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.stomp.StompDestinations;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlayResponse extends StompResponse {

    private String roomId;
    private TicTacToeSymbol[][] gameField;

    @Override
    public String getDestination() {
        return StompDestinations.PLAY;
    }

}