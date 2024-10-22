package com.scentbird.server.game;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.responses.PlayResponse;
import com.scentbird.server.game.command.PlayCommand;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class PlayingState extends TicTacToeState {

    private static final int MAX_TRIES = 9;
    private int numberOfTries;

    PlayingState(TicTacToeRoom gameRoom) {
        super(gameRoom);
    }

    @Override
    public boolean play(PlayCommand playCommand) {
        if (!validateCommand(playCommand)) {
            return false;
        }

        int rowIndex = playCommand.getRowIndex();
        int columnIndex = playCommand.getColumnIndex();
        Player currentPlayer = gameRoom.getCurrentPlayer();
        TicTacToeSymbol symbol = currentPlayer.getSymbol();
        gameRoom.markOnField(symbol, rowIndex, columnIndex);
        log.info("Player {} marked {} on cell with indexes [{}][{}]",
                currentPlayer.getUsername(), symbol, rowIndex, columnIndex);
        gameRoom.printGameBoard();

        if (gameRoom.hasWinner()) {
            gameRoom.markWinner(currentPlayer);
            gameRoom.transit(EndState::new);
            return true;
        }

        if (++numberOfTries == MAX_TRIES) {
            gameRoom.transit(EndState::new);
            return true;
        }

        Player nextPlayer = currentPlayer;
        currentPlayer = gameRoom.getNextPlayer();
        gameRoom.assignPlayerOrder(currentPlayer, nextPlayer);

        sendPlayResponseToCurrentPlayer();

        return true;
    }

    @Override
    void markStateReady() {
        //first to play is the user with X symbol
        Player currentPlayer = null;
        Player nextPlayer = null;
        for (Player player : gameRoom.getPlayers().values()) {
            if (player.getSymbol().equals(TicTacToeSymbol.X)) {
                currentPlayer = player;
            } else {
                nextPlayer = player;
            }
        }
        gameRoom.assignPlayerOrder(currentPlayer, nextPlayer);
        sendPlayResponseToCurrentPlayer();
    }

    private void sendPlayResponseToCurrentPlayer() {
        PlayResponse playResponse = PlayResponse.builder()
                .roomId(gameRoom.getRoomId())
                .gameField(gameRoom.getGameField())
                .build();
        gameRoom.getStompMessageSender().sendToUser(gameRoom.getCurrentPlayer().getUsername(), playResponse);
    }

    private boolean validateCommand(PlayCommand playCommand) {
        String username = playCommand.getUsername();
        if (username == null || !username.equals(gameRoom.getCurrentPlayer().getUsername())) {
            log.error("Can't execute play command: wrong username!");
            return false;
        }

        TicTacToeSymbol[][] gameField = gameRoom.getGameField();
        int rowIndex = playCommand.getRowIndex();
        int columnIndex = playCommand.getColumnIndex();
        if (gameField[rowIndex][columnIndex] != null) {
            log.error("Can't play on cell with indexes [{}][{}] is it is already occupied!", rowIndex, columnIndex);
            return false;
        }

        return true;
    }
}