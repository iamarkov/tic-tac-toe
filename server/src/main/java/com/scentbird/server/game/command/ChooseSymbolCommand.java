package com.scentbird.server.game.command;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.server.game.TicTacToeRoom;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;


@SuperBuilder
@Getter
@ToString(callSuper = true)
@Slf4j
public class ChooseSymbolCommand extends GameCommand {

    private final TicTacToeRoom gameRoom;
    private final TicTacToeSymbol symbol;

    @Override
    public void execute() {
        if (gameRoom == null) {
            log.error("Invalid room to execute command on: null!");
            return;
        }
        gameRoom.chooseSymbol(this);
    }

}
