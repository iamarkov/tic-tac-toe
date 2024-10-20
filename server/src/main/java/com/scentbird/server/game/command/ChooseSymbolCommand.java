package com.scentbird.server.game.command;

import com.scentbird.common.payload.TicTacToeSymbol;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;


@SuperBuilder
@Getter
@ToString(callSuper = true)
@Slf4j
public class ChooseSymbolCommand extends GameCommand {

    private final TicTacToeSymbol symbol;

    @Override
    public void execute() {
        if (gameRoom == null) {
            log.error(ROOM_NULL_ERROR_MESSAGE);
            return;
        }
        gameRoom.chooseSymbol(this);
    }

}
