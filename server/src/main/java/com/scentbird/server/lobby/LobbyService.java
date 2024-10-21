package com.scentbird.server.lobby;

import com.scentbird.server.game.TicTacToeRoom;
import com.scentbird.server.game.command.JoinGameCommand;

public interface LobbyService {

    void accept(JoinGameCommand joinGameCommand);

    TicTacToeRoom getRoom(String roomId);

}
