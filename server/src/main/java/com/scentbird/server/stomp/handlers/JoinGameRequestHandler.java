package com.scentbird.server.stomp.handlers;

import com.scentbird.common.payload.requests.JoinGameRequest;
import com.scentbird.common.stomp.StompDestinations;
import com.scentbird.server.game.command.GameCommand;
import com.scentbird.server.game.command.JoinGameCommand;
import com.scentbird.server.lobby.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinGameRequestHandler implements StompRequestHandler<JoinGameRequest> {

    private final LobbyService lobbyService;

    @Override
    public String getSupportedDestination() {
        return StompDestinations.JOIN_GAME;
    }

    @Override
    public GameCommand convert(JoinGameRequest request, String sessionId) {
        return JoinGameCommand.builder()
                .username(request.getUsername())
                .sessionId(sessionId)
                .lobbyService(lobbyService)
                .build();
    }
}