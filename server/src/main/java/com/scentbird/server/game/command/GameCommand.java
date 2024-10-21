package com.scentbird.server.game.command;

import com.scentbird.common.command.Command;
import com.scentbird.server.game.TicTacToeRoom;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public abstract class GameCommand implements Command {

    public static final String ROOM_NULL_ERROR_MESSAGE = "Invalid room to execute command on: null!";

    protected final String username;
    protected final String sessionId;
    protected final TicTacToeRoom gameRoom;
}
