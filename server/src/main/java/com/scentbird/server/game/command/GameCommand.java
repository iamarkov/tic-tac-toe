package com.scentbird.server.game.command;

import com.scentbird.common.command.Command;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public abstract class GameCommand implements Command {
    private final String username;
    private final String sessionId;
}
