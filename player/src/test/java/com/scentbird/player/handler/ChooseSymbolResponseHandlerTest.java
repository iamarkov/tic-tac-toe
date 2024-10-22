package com.scentbird.player.handler;

import com.scentbird.common.payload.responses.ChooseSymbolResponse;
import com.scentbird.player.command.ChooseSymbolCommand;
import com.scentbird.player.command.PlayerCommand;
import com.scentbird.player.stomp.handlers.ChooseSymbolResponseHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChooseSymbolResponseHandlerTest {

    @Test
    public void convertTest() {
        //arrange
        ChooseSymbolResponse response = ChooseSymbolResponse.builder()
                .roomId("sample-room-id")
                .build();
        ChooseSymbolResponseHandler responseHandler = new ChooseSymbolResponseHandler();

        //act
        ChooseSymbolCommand command = responseHandler.convert(response);

        //assert
        Assertions.assertEquals(command.getRoomId(), "sample-room-id");
    }

}
