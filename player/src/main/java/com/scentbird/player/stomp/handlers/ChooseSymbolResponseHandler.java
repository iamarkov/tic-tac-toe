package com.scentbird.player.stomp.handlers;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.responses.ChooseSymbolResponse;
import com.scentbird.player.command.ChooseSymbolCommand;
import com.scentbird.player.command.PlayerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Random;

import static com.scentbird.common.stomp.StompDestinations.USER_PREFIX;
import static com.scentbird.common.stomp.StompDestinations.QUEUE_PREFIX;
import static com.scentbird.common.stomp.StompDestinations.CHOOSE_SYMBOL;

@Component
@RequiredArgsConstructor
public class ChooseSymbolResponseHandler implements StompResponseHandler<ChooseSymbolResponse> {

    @Override
    public String getSupportedDestination() {
        return USER_PREFIX + QUEUE_PREFIX + CHOOSE_SYMBOL;
    }

    @Override
    public Type getSupportedType() {
        return ChooseSymbolResponse.class;
    }

    @Override
    public PlayerCommand convert(ChooseSymbolResponse response) {
        TicTacToeSymbol randomSymbol = TicTacToeSymbol.values()[new Random().nextInt(TicTacToeSymbol.values().length)];
        return ChooseSymbolCommand.builder()
                .symbol(randomSymbol)
                .roomId(response.getRoomId())
                .build();
    }
}
