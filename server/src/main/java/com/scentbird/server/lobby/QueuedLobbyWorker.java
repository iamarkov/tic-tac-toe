package com.scentbird.server.lobby;

import com.scentbird.common.payload.responses.PlayResponse;
import com.scentbird.server.game.Player;
import com.scentbird.server.game.TicTacToeRoom;
import com.scentbird.server.game.command.JoinGameCommand;
import com.scentbird.server.stomp.StompMessageSender;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;


@Slf4j
public class QueuedLobbyWorker implements LobbyWorker {

    private final QueuedLobbyService lobbyService;
    private final StompMessageSender stompMessageSender;
    private TicTacToeRoom gameRoom;

    public QueuedLobbyWorker(QueuedLobbyService lobbyService, StompMessageSender stompMessageSender) {
        this.lobbyService = lobbyService;
        this.stompMessageSender = stompMessageSender;
        this.gameRoom = new TicTacToeRoom(stompMessageSender, lobbyService);
        lobbyService.getGameRooms().put(gameRoom.getRoomId(), gameRoom);
    }

    @Override
    public void work() {
        while (true) {
            try {
                JoinGameCommand command = lobbyService.getJoinGameCommands().take();
                synchronized (this) {
                    registerPlayerForNewGame(command);
                    checkForActiveGames(command.getUsername());
                }
            } catch (InterruptedException e) {
                log.error("Error occurred while waiting for new commands: {}", e.getMessage());
            }
        }
    }

    private void registerPlayerForNewGame(JoinGameCommand command) {
        boolean isPlayerRegistered = gameRoom.registerPlayer(command);
        String username = command.getPlayer().getUsername();
        if (gameRoom.getPlayers().containsKey(username)) {
            log.info("User {} re-entered room {}", username, gameRoom.getRoomId());
        }
        if (!isPlayerRegistered) {
            this.gameRoom = new TicTacToeRoom(stompMessageSender, lobbyService);
            lobbyService.getGameRooms().put(gameRoom.getRoomId(), gameRoom);
            gameRoom.registerPlayer(command);
        }
    }

    private void checkForActiveGames(String username) {
        Set<TicTacToeRoom> activeRooms = lobbyService.getActiveRooms(username);
        activeRooms.forEach(activeRoom -> {
            Player currentPlayer = activeRoom.getCurrentPlayer();
            if (currentPlayer != null && currentPlayer.getUsername().equals(username)) {
                PlayResponse playResponse = PlayResponse.builder()
                        .roomId(activeRoom.getRoomId())
                        .gameField(activeRoom.getGameField())
                        .build();
                activeRoom.getStompMessageSender().sendToUser(username, playResponse);
            }
        });
    }

}
