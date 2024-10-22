package com.scentbird.server.lobby;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.responses.ServerStateResponse;
import com.scentbird.server.stomp.StompMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ServerStateHeartbeat {

    private final LobbyService lobbyService;
    private final StompMessageSender stompMessageSender;
    private final UserRegistry userRegistry;

    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.SECONDS)
    private void stateHeartbeat() {
        List<ServerStateResponse.RoomInfo> rooms = new ArrayList<>();
        lobbyService.getGameRooms().values().forEach(room -> {
            List<ServerStateResponse.PlayerInfo> players = new ArrayList<>();
            room.getPlayers().values().forEach(player -> {
                players.add(ServerStateResponse.PlayerInfo.builder()
                        .username(player.getUsername())
                        .symbol(player.getSymbol())
                        .isOnline(userRegistry.getOnlinePlayer(player.getUsername()) != null)
                        .build());
            });
            rooms.add(ServerStateResponse.RoomInfo.builder()
                    .id(room.getRoomId())
                    .gameField(flattenField(room.getGameField()))
                    .state(room.getStateName())
                    .winner(room.getWinner() != null ? room.getWinner().getUsername() : "")
                    .players(players)
                    .build());
        });


        ServerStateResponse serverStateResponse = ServerStateResponse.builder()
                .rooms(rooms)
                .build();
        stompMessageSender.sendToAll(serverStateResponse);
    }

    private List<TicTacToeSymbol> flattenField(TicTacToeSymbol[][] gameField) {
        List<TicTacToeSymbol> result = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < gameField.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gameField[0].length; columnIndex++) {
                result.add(gameField[rowIndex][columnIndex]);
            }
        }
        return result;
    }

}
