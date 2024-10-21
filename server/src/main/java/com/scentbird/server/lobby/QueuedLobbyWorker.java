package com.scentbird.server.lobby;

import com.scentbird.server.game.TicTacToeRoom;
import com.scentbird.server.game.command.JoinGameCommand;
import com.scentbird.server.stomp.StompMessageSender;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class QueuedLobbyWorker implements LobbyWorker {

    private final QueuedLobbyService lobbyService;
    private final StompMessageSender stompMessageSender;
    private TicTacToeRoom gameRoom;

    public QueuedLobbyWorker(QueuedLobbyService lobbyService, StompMessageSender stompMessageSender) {
        this.lobbyService = lobbyService;
        this.stompMessageSender = stompMessageSender;
        this.gameRoom = new TicTacToeRoom(stompMessageSender);
        lobbyService.getGameRooms().put(gameRoom.getRoomId(), gameRoom);
    }

    @Override
    public void work() {
        while (true) {
            try {
                JoinGameCommand command = lobbyService.getJoinGameCommands().take();
                synchronized (this) {
                    registerPlayer(command);
                }
            } catch (InterruptedException e) {
                log.error("Error occurred while waiting for new commands: {}", e.getMessage());
            }
        }
    }

    private void registerPlayer(JoinGameCommand command) {
        boolean isPlayerRegistered = gameRoom.registerPlayer(command);
        if (!isPlayerRegistered) {
            this.gameRoom = new TicTacToeRoom(stompMessageSender);
            lobbyService.getGameRooms().put(gameRoom.getRoomId(), gameRoom);
            gameRoom.registerPlayer(command);
        }
    }

}
