package com.scentbird.server.game;

import com.scentbird.common.payload.TicTacToeSymbol;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Player {

    private final String username;

    @EqualsAndHashCode.Exclude
    @Setter
    private String sessionId;

    @Setter
    @EqualsAndHashCode.Exclude
    private TicTacToeSymbol symbol;

}
