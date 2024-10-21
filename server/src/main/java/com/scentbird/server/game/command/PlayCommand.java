package com.scentbird.server.game.command;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@SuperBuilder
@Getter
@ToString(callSuper = true)
@Slf4j
public class PlayCommand extends GameCommand {

    private final int rowIndex;
    private final int columnIndex;

    @Override
    public void execute() {
        if (gameRoom == null) {
            log.error(ROOM_NULL_ERROR_MESSAGE);
            return;
        }
        gameRoom.play(this);
    }

}