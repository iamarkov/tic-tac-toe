package com.scentbird.player.command;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.requests.ChooseSymbolRequest;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ChooseSymbolCommand extends PlayerCommand {

    private final String username;
    private final TicTacToeSymbol symbol;
    private final String roomId;

    @Override
    public void execute() {
        ChooseSymbolRequest request = ChooseSymbolRequest.builder()
                .username(username)
                .symbol(symbol)
                .roomId(roomId)
                .build();
        stompClientService.send(request);
    }

}
