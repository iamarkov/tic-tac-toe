package com.scentbird.server.game;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class EndState extends TicTacToeState {

    public EndState(TicTacToeRoom gameRoom) {
        super(gameRoom);
    }

    @Override
    void markStateReady() {
        gameRoom.getPlayers().values().forEach(player -> {
            gameRoom.getLobbyService().removeActiveRoom(player.getUsername(), gameRoom.getRoomId());
        });
        if (gameRoom.hasWinner()) {
            log.info("Congratulations to player {}!", gameRoom.getWinner().getUsername());
        } else {
            log.info("Sadly there are no winners in the game!");
        }
    }
}