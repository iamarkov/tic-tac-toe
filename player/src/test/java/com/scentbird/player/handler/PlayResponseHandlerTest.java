package com.scentbird.player.handler;

import com.scentbird.common.payload.TicTacToeSymbol;
import com.scentbird.common.payload.responses.PlayResponse;
import com.scentbird.player.command.ChooseSymbolCommand;
import com.scentbird.player.command.PlayCommand;
import com.scentbird.player.stomp.handlers.PlayResponseHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayResponseHandlerTest {

    @Test
    public void convertTest() {
        //arrange
        TicTacToeSymbol[][] gameField = new TicTacToeSymbol[3][3];
        PlayResponse response = PlayResponse.builder()
                .roomId("sample-room-id")
                .gameField(gameField)
                .build();
        PlayResponseHandler responseHandler = new PlayResponseHandler();

        //act
        PlayCommand command = responseHandler.convert(response);

        //assert
        Assertions.assertEquals(command.getRoomId(), "sample-room-id");
        Assertions.assertEquals(command.getGameField(), gameField);
    }

}
