package com.scentbird.server.game.command;

import com.scentbird.server.game.Player;
import com.scentbird.server.lobby.LobbyService;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@ToString(callSuper = true)
public class JoinGameCommand extends GameCommand {

    private final LobbyService lobbyService;
    private final Player player;

    @Override
    public void execute() {
        lobbyService.accept(this);
    }

}
