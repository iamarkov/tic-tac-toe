package com.scentbird.server.stomp.handlers;

import com.scentbird.common.payload.requests.PlayRequest;
import com.scentbird.common.stomp.StompDestinations;
import com.scentbird.server.game.command.GameCommand;
import com.scentbird.server.game.command.PlayCommand;
import com.scentbird.server.lobby.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayRequestHandler implements StompRequestHandler<PlayRequest> {

    private final LobbyService lobbyService;

    @Override
    public String getSupportedDestination() {
        return StompDestinations.PLAY;
    }

    @Override
    public GameCommand convert(PlayRequest request, String sessionId) {
        return PlayCommand.builder()
                .username(request.getUsername())
                .sessionId(sessionId)
                .gameRoom(lobbyService.getGameRooms().get(request.getRoomId()))
                .rowIndex(request.getRowIndex())
                .columnIndex(request.getColumnIndex())
                .build();
    }
}