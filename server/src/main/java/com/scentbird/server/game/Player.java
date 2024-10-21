package com.scentbird.server.game;

import com.scentbird.common.payload.TicTacToeSymbol;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class Player {
    private final String username;
    private final String sessionId;
    @Setter
    private TicTacToeSymbol symbol;
}
