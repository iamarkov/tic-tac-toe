package com.scentbird.player.command;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.requests.ChooseSymbolRequest;
import com.scentbird.common.payload.requests.PlayRequest;
import com.scentbird.player.stomp.StompClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class PlayCommandTest {

    @Test
    public void executeTest() {
        //arrange
        TicTacToeSymbol[][] gameField = {
                {TicTacToeSymbol.O, TicTacToeSymbol.X, TicTacToeSymbol.O},
                {TicTacToeSymbol.O, TicTacToeSymbol.X, TicTacToeSymbol.O},
                {TicTacToeSymbol.O, TicTacToeSymbol.X, null},
        };

        StompClientService stompClientService = Mockito.mock(StompClientService.class);
        PlayCommand command = PlayCommand.builder()
                .username("sample-username")
                .roomId("sample-room-id")
                .gameField(gameField)
                .playerDelaySeconds(0)
                .build();
        command.setStompClientService(stompClientService);
        ArgumentCaptor<PlayRequest> requestCaptor = ArgumentCaptor.forClass(PlayRequest.class);
        PlayRequest expectedRequest = PlayRequest.builder()
                .username("sample-username")
                .roomId("sample-room-id")
                .rowIndex(2)
                .columnIndex(2)
                .build();

        //act
        command.execute();

        //assert
        Mockito.verify(stompClientService, Mockito.times(1)).send(requestCaptor.capture());
        Assertions.assertEquals(expectedRequest, requestCaptor.getValue());
    }

}
