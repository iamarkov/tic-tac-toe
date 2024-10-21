package com.scentbird.server.game;


import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.responses.ChooseSymbolResponse;
import com.scentbird.server.game.command.ChooseSymbolCommand;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class ChooseSymbolState extends TicTacToeState {

    ChooseSymbolState(TicTacToeRoom gameRoom) {
        super(gameRoom);
        //we send the option to choose a symbol to both players and accept the request of the faster one to respond to it
        for (Player player : gameRoom.getPlayers().values()) {
            ChooseSymbolResponse response = ChooseSymbolResponse.builder().roomId(gameRoom.getRoomId()).build();
            gameRoom.getStompMessageSender().sendToUser(player.getSessionId(), response);
        }
    }

    @Override
    public boolean chooseSymbol(ChooseSymbolCommand chooseSymbolCommand) {
        String username = chooseSymbolCommand.getUsername();
        if (!gameRoom.getPlayers().containsKey(username)) {
            log.warn("Room {} doesn't have player {}!", gameRoom.getRoomId(), username);
            return false;
        }

        TicTacToeSymbol chosenSymbol = chooseSymbolCommand.getSymbol();
        TicTacToeSymbol leftSymbol = chosenSymbol.equals(TicTacToeSymbol.X) ? TicTacToeSymbol.O : TicTacToeSymbol.X;
        for (Player player : gameRoom.getPlayers().values()) {
            if (player.getUsername().equals(username)) {
                log.info("User {} chose to play with symbol {}", player.getUsername(), chosenSymbol);
                player.setSymbol(chosenSymbol);
            } else {
                log.info("For user {} the symbol {} is left", player.getUsername(), leftSymbol);
                player.setSymbol(leftSymbol);
            }
        }

        gameRoom.transit(new PlayingState(gameRoom));
        return true;
    }
}
