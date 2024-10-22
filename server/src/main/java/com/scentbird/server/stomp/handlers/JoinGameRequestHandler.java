package com.scentbird.server.stomp.handlers;

import com.scentbird.common.payload.requests.JoinGameRequest;
import com.scentbird.common.stomp.StompDestinations;
import com.scentbird.server.game.Player;
import com.scentbird.server.game.command.GameCommand;
import com.scentbird.server.game.command.JoinGameCommand;
import com.scentbird.server.lobby.LobbyService;
import com.scentbird.server.lobby.UserRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinGameRequestHandler implements StompRequestHandler<JoinGameRequest> {

    private final LobbyService lobbyService;
    private final UserRegistry userRegistry;

    @Override
    public String getSupportedDestination() {
        return StompDestinations.JOIN_GAME;
    }

    @Override
    public GameCommand convert(JoinGameRequest request, String sessionId) {
        Player player = userRegistry.getOnlinePlayer(request.getUsername());
        if (player == null) {
            player = new Player(request.getUsername());
        }
        player.setSessionId(sessionId);
        userRegistry.markPlayerOnline(player);
        return JoinGameCommand.builder()
                .username(request.getUsername())
                .sessionId(sessionId)
                .lobbyService(lobbyService)
                .player(player)
                .build();
    }
}