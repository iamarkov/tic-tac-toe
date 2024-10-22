package com.scentbird.server.game.command;

import com.scentbird.common.payload.requests.ChooseSymbolRequest;
import com.scentbird.server.game.TicTacToeRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class ChooseSymbolCommandTest {

    @Test
    public void executeTest() {
        //arrange
        TicTacToeRoom gameRoomMock = Mockito.mock(TicTacToeRoom.class);
        ChooseSymbolCommand command = ChooseSymbolCommand.builder()
                .gameRoom(gameRoomMock)
                .build();
        ArgumentCaptor<ChooseSymbolCommand> commandArgumentCaptor = ArgumentCaptor.forClass(ChooseSymbolCommand.class);

        //act
        command.execute();

        //assert
        Mockito.verify(gameRoomMock, Mockito.times(1)).chooseSymbol(commandArgumentCaptor.capture());
        Assertions.assertEquals(command, commandArgumentCaptor.getValue());
    }

}
