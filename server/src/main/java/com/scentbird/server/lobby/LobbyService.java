package com.scentbird.server.lobby;

import com.scentbird.server.game.TicTacToeRoom;
import com.scentbird.server.game.command.JoinGameCommand;

import java.util.Map;
import java.util.Set;

public interface LobbyService {

    void accept(JoinGameCommand joinGameCommand);

    Map<String, TicTacToeRoom> getGameRooms();

    Set<TicTacToeRoom> getActiveRooms(String username);

    void addActiveRoom(String username, String roomId);

    void removeActiveRoom(String username, String roomId);
}
