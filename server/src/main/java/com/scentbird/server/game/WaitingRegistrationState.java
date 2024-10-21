package com.scentbird.server.game;

import com.scentbird.server.game.command.JoinGameCommand;
import lombok.extern.slf4j.Slf4j;


@Slf4j
class WaitingRegistrationState extends TicTacToeState {

    private static final int TIC_TAC_TOE_PLAYERS_COUNT = 2;

    WaitingRegistrationState(TicTacToeRoom gameRoom) {
        super(gameRoom);
    }

    @Override
    public boolean registerPlayer(JoinGameCommand joinGameCommand) {
        if (gameRoom.getPlayers().size() == TIC_TAC_TOE_PLAYERS_COUNT) {
            log.warn("Room {} already full!", gameRoom.getRoomId());
            return false;
        }
        String username = joinGameCommand.getUsername();
        if (gameRoom.getPlayers().containsKey(username)) {
            log.warn("Player {} already entered room {}!", username, gameRoom.getRoomId());
            return false;
        }

        gameRoom.getPlayers().put(username, new Player(username, joinGameCommand.getSessionId()));

        if (gameRoom.getPlayers().size() == TIC_TAC_TOE_PLAYERS_COUNT) {
            gameRoom.transit(new ChooseSymbolState(gameRoom));
        }

        return true;
    }

}
