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
        if (!validateCommand(joinGameCommand)) {
            return false;
        }

        String username = joinGameCommand.getUsername();
        gameRoom.getPlayers().put(username, joinGameCommand.getPlayer());
        joinGameCommand.getLobbyService().addActiveRoom(username, gameRoom.getRoomId());

        if (gameRoom.getPlayers().size() == TIC_TAC_TOE_PLAYERS_COUNT) {
            gameRoom.transit(ChooseSymbolState::new);
        }

        return true;
    }

    private boolean validateCommand(JoinGameCommand joinGameCommand) {
        if (gameRoom.getPlayers().size() == TIC_TAC_TOE_PLAYERS_COUNT) {
            log.warn("Room {} already full!", gameRoom.getRoomId());
            return false;
        }
        String username = joinGameCommand.getUsername();
        if (gameRoom.getPlayers().containsKey(username)) {
            log.warn("Player {} already entered room {}!", username, gameRoom.getRoomId());
            return false;
        }

        return true;
    }

}
