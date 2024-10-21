package com.scentbird.server.game.command;

import com.scentbird.server.game.TicTacToeRoom;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public class PlayCommand extends GameCommand {

    private final TicTacToeRoom gameRoom;
    private final String roomId;
    private final int xCoordinate;
    private final int yCoordinate;

    @Override
    public void execute() {
        //TODO implement
    }

}