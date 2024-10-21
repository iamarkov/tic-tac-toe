package com.scentbird.player.command;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.requests.ChooseSymbolRequest;
import lombok.Builder;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Builder
@ToString
public class ChooseSymbolCommand extends PlayerCommand {

    @Value("${player.username}")
    private String playerUsername;
    private final TicTacToeSymbol symbol;
    private final String roomId;

    @Override
    public void execute() {
        ChooseSymbolRequest request = ChooseSymbolRequest.builder()
                .username(playerUsername)
                .symbol(symbol)
                .roomId(roomId)
                .build();
        stompClientService.send(request);
    }

}
