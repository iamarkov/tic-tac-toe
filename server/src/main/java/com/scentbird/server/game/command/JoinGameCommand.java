package com.scentbird.server.game.command;

import com.scentbird.server.lobby.LobbyService;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@ToString
public class JoinGameCommand extends GameCommand {

    private final LobbyService lobbyService;

    @Override
    public void execute() {
        lobbyService.accept(this);
    }

}
