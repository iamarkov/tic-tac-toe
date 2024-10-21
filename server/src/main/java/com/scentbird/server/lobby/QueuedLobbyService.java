package com.scentbird.server.lobby;

import com.scentbird.server.game.TicTacToeRoom;
import com.scentbird.server.game.command.JoinGameCommand;
import com.scentbird.server.stomp.StompMessageSender;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
//we apply producer-consumer pattern here to solve the problem of distributing players to rooms
public class QueuedLobbyService implements LobbyService {

    @Getter(AccessLevel.PROTECTED)
    private final BlockingQueue<JoinGameCommand> joinGameCommands;
    @Getter(AccessLevel.PROTECTED)
    private final Map<String, TicTacToeRoom> gameRooms;

    public QueuedLobbyService(@Value("${game.server.lobby.workers.count}") int lobbyWorkersCount,
                              StompMessageSender stompMessageSender) {
        this.joinGameCommands = new LinkedBlockingQueue<>();
        this.gameRooms = new ConcurrentHashMap<>();
        for (int count = 0; count < lobbyWorkersCount; count++) {
            QueuedLobbyWorker lobbyWorker = new QueuedLobbyWorker(this, stompMessageSender);
            Thread lobbyWorkerExecutor = new Thread(lobbyWorker::work);
            lobbyWorkerExecutor.start();
        }
    }

    @Override
    public void accept(JoinGameCommand joinGameCommand) {
        joinGameCommands.add(joinGameCommand);
    }

    @Override
    public TicTacToeRoom getRoom(String roomId) {
        return gameRooms.get(roomId);
    }
}
