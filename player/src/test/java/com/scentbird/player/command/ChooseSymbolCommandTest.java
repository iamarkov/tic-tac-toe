package com.scentbird.player.command;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.requests.ChooseSymbolRequest;
import com.scentbird.player.stomp.StompClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class ChooseSymbolCommandTest {

    @Test
    public void executeTest() {
        //arrange
        StompClientService stompClientService = Mockito.mock(StompClientService.class);
        ChooseSymbolCommand command = ChooseSymbolCommand.builder()
                .username("sample-username")
                .symbol(TicTacToeSymbol.X)
                .roomId("sample-room-id")
                .build();
        command.setStompClientService(stompClientService);
        ArgumentCaptor<ChooseSymbolRequest> requestCaptor = ArgumentCaptor.forClass(ChooseSymbolRequest.class);
        ChooseSymbolRequest expected = ChooseSymbolRequest.builder()
                .username("sample-username")
                .symbol(TicTacToeSymbol.X)
                .roomId("sample-room-id")
                .build();

        //act
        command.execute();

        //assert
        Mockito.verify(stompClientService, Mockito.times(1)).send(requestCaptor.capture());
        Assertions.assertEquals(expected, requestCaptor.getValue());
    }

}
