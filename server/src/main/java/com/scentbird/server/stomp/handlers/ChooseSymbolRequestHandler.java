package com.scentbird.server.stomp.handlers;

import com.scentbird.common.payload.requests.ChooseSymbolRequest;
import com.scentbird.common.stomp.StompDestinations;
import com.scentbird.server.game.command.ChooseSymbolCommand;
import com.scentbird.server.game.command.GameCommand;
import com.scentbird.server.lobby.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChooseSymbolRequestHandler implements StompRequestHandler<ChooseSymbolRequest> {

    private final LobbyService lobbyService;

    @Override
    public String getSupportedDestination() {
        return StompDestinations.CHOOSE_SYMBOL;
    }

    @Override
    public GameCommand convert(ChooseSymbolRequest request, String sessionId) {
        return ChooseSymbolCommand.builder()
                .username(request.getUsername())
                .sessionId(sessionId)
                .gameRoom(lobbyService.getRoom(request.getRoomId()))
                .symbol(request.getSymbol())
                .build();
    }
}