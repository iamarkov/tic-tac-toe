package com.scentbird.server.game;

import com.scentbird.common.payload.responses.ChooseSymbolResponse;
import com.scentbird.server.stomp.StompMessageSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThatList;

public class ChooseSymbolStateTest {

    @Test
    public void sendChooseSymbolCommandsToBothPlayersTest() {
        //arrange
        Player player1 = new Player("player1");
        Player player2 = new Player("player22");
        Map<String, Player> players = new HashMap<>();
        players.put(player1.getUsername(), player1);
        players.put(player2.getUsername(), player2);

        StompMessageSender stompMessageSender = Mockito.mock(StompMessageSender.class);
        TicTacToeRoom gameRoom = Mockito.mock(TicTacToeRoom.class);
        Mockito.doReturn(players).when(gameRoom).getPlayers();
        Mockito.doReturn(stompMessageSender).when(gameRoom).getStompMessageSender();

        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ChooseSymbolResponse> responseCaptor = ArgumentCaptor.forClass(ChooseSymbolResponse.class);

        ChooseSymbolState state = new ChooseSymbolState(gameRoom);

        ChooseSymbolResponse expectedResponse = ChooseSymbolResponse.builder().roomId(gameRoom.getRoomId()).build();

        //act
        state.markStateReady();

        //assert
        Mockito.verify(stompMessageSender, Mockito.times(2)).sendToUser(usernameCaptor.capture(), responseCaptor.capture());
        assertThatList(usernameCaptor.getAllValues()).containsExactlyInAnyOrderElementsOf(players.keySet());
        Assertions.assertEquals(2, responseCaptor.getAllValues().size());
        Assertions.assertEquals(expectedResponse, responseCaptor.getValue());

    }

}
