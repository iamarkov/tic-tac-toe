package com.scentbird.server.game;

import com.scentbird.server.game.command.ChooseSymbolCommand;
import com.scentbird.server.game.command.JoinGameCommand;
import com.scentbird.server.game.command.PlayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
abstract class TicTacToeState implements TicTacToeGame {

    private static final String ERROR_MESSAGE_TEMPLATE = "Operation not accepted right now: {}";

    protected final TicTacToeRoom gameRoom;

    @Override
    public boolean registerPlayer(JoinGameCommand joinGameCommand) {
        log.error(ERROR_MESSAGE_TEMPLATE, joinGameCommand);
        return false;
    }

    @Override
    public boolean chooseSymbol(ChooseSymbolCommand chooseSymbolCommand) {
        log.error(ERROR_MESSAGE_TEMPLATE, chooseSymbolCommand);
        return false;
    }

    @Override
    public boolean play(PlayCommand playCommand) {
        log.error(ERROR_MESSAGE_TEMPLATE, playCommand);
        return false;
    }

}
