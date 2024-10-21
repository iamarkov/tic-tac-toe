package com.scentbird.server.game.command;

import com.scentbird.common.command.Command;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public abstract class GameCommand implements Command {
    private final String username;
    private final String sessionId;
}
