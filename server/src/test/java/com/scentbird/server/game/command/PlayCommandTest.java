package com.scentbird.server.game.command;

import com.scentbird.server.game.TicTacToeRoom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class PlayCommandTest {

    @Test
    public void executeTest() {
        //arrange
        TicTacToeRoom gameRoomMock = Mockito.mock(TicTacToeRoom.class);
        PlayCommand command = PlayCommand.builder()
                .gameRoom(gameRoomMock)
                .build();
        ArgumentCaptor<PlayCommand> commandArgumentCaptor = ArgumentCaptor.forClass(PlayCommand.class);

        //act
        command.execute();

        //assert
        Mockito.verify(gameRoomMock, Mockito.times(1)).play(commandArgumentCaptor.capture());
        Assertions.assertEquals(command, commandArgumentCaptor.getValue());
    }

}
