package com.scentbird.server.game.command;

import com.scentbird.server.game.TicTacToeRoom;
import com.scentbird.server.lobby.LobbyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class JoinGameCommandTest {

    @Test
    public void executeTest() {
        //arrange
        LobbyService lobbyServiceMock = Mockito.mock(LobbyService.class);
        JoinGameCommand command = JoinGameCommand.builder()
                .lobbyService(lobbyServiceMock)
                .build();
        ArgumentCaptor<JoinGameCommand> commandArgumentCaptor = ArgumentCaptor.forClass(JoinGameCommand.class);

        //act
        command.execute();

        //assert
        Mockito.verify(lobbyServiceMock, Mockito.times(1)).accept(commandArgumentCaptor.capture());
        Assertions.assertEquals(command, commandArgumentCaptor.getValue());
    }

}
